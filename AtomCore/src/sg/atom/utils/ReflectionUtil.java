package sg.atom.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sg.atom.utils.pool.StringBuilderPool;

public class ReflectionUtil {

    private static final Logger log = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * Instance의 field 값을 반환한다. 상속받은 field도 찾아준다.
     *
     * @param instance
     * @param fieldName
     * @return 실패하면 null
     * @author mulova
     */
    public static Object getField(Object instance, String fieldName) {
        return getField(instance, fieldName, instance.getClass());
    }

    private static Object getField(Object instance, String fieldName, Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                try {
                    field.setAccessible(true);
                    return field.get(instance);
                } catch (Exception e) {
                    log.error(fieldName, e);
                    return null;
                }
            }
        }
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != Object.class) {
            return getField(superClazz, fieldName, superClazz);
        }
        return null;
    }

    /**
     * instance의 field에 값을 설정한다. 상속받은 field에도 값을 설정할 수 있다.
     *
     * @param instance
     * @param fieldName
     * @param value
     * @return 실패하면 false
     * @author mulova
     */
    public static boolean setField(Object instance, String fieldName, Object value) {
        return setField(instance, fieldName, value, instance.getClass());
    }

    public static boolean setField(Class<?> cls, String fieldName, Object value) {
        return setField(null, fieldName, value, cls);
    }

    private static boolean setField(Object instance, String fieldName, Object value, Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                try {
                    field.setAccessible(true);
                    field.set(instance, value);
                } catch (Exception e) {
                    log.error(fieldName, e);
                    return false;
                }
                return true;
            }
        }
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != Object.class) {
            return setField(instance, fieldName, value, superClazz);
        }
        return false;
    }

    /**
     * Class parameter와 동일한 class type의 object만으로 이루어졌을 경우 사용할 수 있다.
     *
     * @param <T>
     * @param clazz
     * @param parameters Class parameter와 동일한 class type의 parameters
     * @return
     * @author mulova
     */
    public static <T> T createInstance(Class<T> clazz, Object... parameters) {
        Class<?>[] paramClazz = new Class<?>[parameters.length];
        for (int i = 0; i < paramClazz.length; i++) {
            paramClazz[i] = parameters[i].getClass();
        }
        return createInstance(clazz, paramClazz, parameters);
    }

    public static <T> T createInstance(Class<T> clazz, Class<?>[] paramClazz, Object... parameters) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(paramClazz);
            constructor.setAccessible(true);
            return constructor.newInstance(parameters);
        } catch (Exception e) {
            log.error("Can't instantiate " + clazz.getCanonicalName(), e);
        }
        return null;
    }

    public static boolean deepEquals(Object o1, Object o2) {
        if (o1.getClass() != o2.getClass()) {
            return false;
        }
        return deepEquals(o1.getClass(), o1, o2);
    }

    private static boolean deepEquals(Class clazz, Object o1, Object o2) {
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            try {
                Object v1 = field.get(o1);
                Object v2 = field.get(o2);
                if (v1 == null ^ v2 == null) {
                    return false;
                }
                if (v1 == null && v2 == null) {
                    return true;
                }
                return v1.equals(v2);
            } catch (Exception e) {
                log.error("Can't compare", e);
                return false;
            }
        }
        Class<?> superClazz = o1.getClass().getSuperclass();
        if (superClazz != Object.class) {
            return deepEquals(superClazz, o1, o2);
        }

        return false;
    }

    public static String toString(Object instance) {
        StringBuilder str = StringBuilderPool.fetchInstance();
        toString(instance, str, instance.getClass());
        String result = str.toString();
        StringBuilderPool.releaseInstance(str);
        return result;
    }

    public static String toString(Object instance, StringBuilder str, Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                str.append(field.getName()).append("= ");
                str.append(field.get(instance)).append('\n');
            } catch (Exception e) {
                log.error(field.getName(), e);
            }
        }
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != Object.class) {
            return toString(instance, str, superClazz);
        }
        return null;
    }
}
