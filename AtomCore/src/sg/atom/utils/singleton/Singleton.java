package sg.atom.utils.singleton;

/**
 * fast and avoid double-checked locking algorithm Implementation based on
 * 
 *
 * @author mulova
 *
 * @param <T>
 */
public class Singleton<T> {

    private volatile T singleton;
    private Class<T> clazz;

    public Singleton(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T getInstance() {
        T result = singleton;
        if (result == null) {
            synchronized (this) {
                result = singleton;
                if (result == null) {
                    try {
                        singleton = result = clazz.getConstructor().newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                        assert false;
                    }
                }
            }
        }
        return result;
    }

    public synchronized void setInstance(T instance) {
        if (singleton != null) {
            throw new RuntimeException("Already set");
        }
        singleton = instance;
    }
}
