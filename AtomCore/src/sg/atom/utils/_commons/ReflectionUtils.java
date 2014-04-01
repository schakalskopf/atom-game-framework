package sg.atom.utils._commons;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sg.atom.utils.repository.pool.StringBuilderPool;

/**
 * Replaced with Guava facilities. or BeanUtils.
 *
 * @author cuong.nguyenmanh2
 */
@Deprecated
public class ReflectionUtils {

    private static final Logger log = LoggerFactory.getLogger(ReflectionUtils.class);

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

    public static Method getSetter(Object object, String propertyName) {
        /*
         * Find the setter method for the property.
         */
        final String firstChar = propertyName.substring(0, 1);
        final String remainder = propertyName.substring(1);
        final String propertySetterName = "set" + firstChar.toUpperCase(Locale.ENGLISH) + remainder;
        Method propertySetter = null;
        try {
            for (Method m : object.getClass().getMethods()) {
                if (m.getName().equals(propertySetterName)) {
                    if (m.getParameterTypes().length == 1) {
                        propertySetter = m;
                        break;
                    }
                }
            }
            if (propertySetter == null) {
                throw new IllegalArgumentException("");
            }
        } catch (SecurityException e) {
            throw new IllegalArgumentException("");
        }
        return propertySetter;
    }

    public static Method getGetter(Object object, String propertyName) {
        final String firstChar = propertyName.substring(0, 1);
        final String remainder = propertyName.substring(1);
        final String propertyGetterName = "get" + firstChar.toUpperCase(Locale.ENGLISH) + remainder;
        Method propertyGetter = null;
        try {
            for (Method m : object.getClass().getMethods()) {
                if (m.getName().equals(propertyGetterName)) {
                    if (m.getParameterTypes().length == 0) {
                        propertyGetter = m;
                        break;
                    }
                }
            }
            if (propertyGetter == null) {
                throw new IllegalArgumentException("");
            }
        } catch (SecurityException e) {
            throw new IllegalArgumentException("");
        }
        return propertyGetter;
    }
}
