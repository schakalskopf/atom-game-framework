package sg.atom.utils.repository.pool;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Category resource pooling.<br>
 *
 * <p>FIXME: Use common Pool instead.
 *
 * @param <K>
 * @param <V>
 */
@Deprecated
public abstract class AbstractPool<K, V> {

    private final CategoryPool<K, V> pool;
    private final HashMap<K, Integer> creationCount;
    private final HashMap<K, Integer> deletionCount;

    public AbstractPool() {
        this.pool = new CategoryPool<K, V>();
        this.creationCount = new HashMap<K, Integer>();
        this.deletionCount = new HashMap<K, Integer>();
    }

    /**
     * instance를 얻어올 때 instance가 저장된 위치에 대한 key를 얻는다.
     *
     * @param keys
     * @return
     */
    protected abstract K getKey(Object... keys);

    /**
     * pool에 반환할 때 instance로부터 저장할 위치에 대한 key를 얻는다.
     *
     * @param instance
     * @return
     */
    protected abstract K getKey(V instance);

    /**
     * pool에 instance가 없을 때 새로 생성한다.
     *
     * @param keys
     * @return
     */
    protected abstract V create(Object... keys);

    /**
     * 모든 instance를 cleanup할 때 호출된다
     *
     * @param key
     * @param value
     */
    protected abstract void dispose(K key, V value);

    public V getInstance(Object... keys) {
        K key = getKey(keys);
        V value = this.pool.getInstance(key);
        if (value == null) {
            Integer count = creationCount.get(key);
            if (count == null) {
                creationCount.put(key, 1);
            } else {
                creationCount.put(key, count + 1);
            }
            value = create(keys);
        }
        initialize(key, value);
        return value;
    }

    /**
     * 사용할 개체를 초기화한다.
     *
     * @param key
     * @param value 새로 생성되거나, Pool에 있던 개체.
     * @author mulova
     */
    protected abstract void initialize(K key, V instance);

    /**
     * instance를 반환하면서 처리할 사항을 기술한다.
     *
     * @param instance
     * @author mulova
     */
    protected abstract void postRelease(K key, V instance);

    public V peek(Object... keys) {
        K key = getKey(keys);
        V value = this.pool.peek(key);
        if (value == null) {
            value = create(keys);
            release(key, value);
        }
        return value;
    }

    /**
     * instance를 pool에 반환한다.<br> instance안에 저장위치에 대한 key가 저장되어 있을때 사용한다.<br>
     *
     * @param instance
     */
    public void release(final V instance) {
        if (instance == null) {
            return;
        }
        release(getKey(instance), instance);
    }

    /**
     * instance를 pool에 반환한다.<br> instance안에 저장위치에 대한 key가 저장되어 있지 않을때 사용한다.<br>
     *
     * @param key
     * @param instance
     */
    public void release(final K key, final V instance) {
        postRelease(key, instance);
        this.pool.release(key, instance);
    }

    /**
     * 현재 pool에 남아있는 instance 개수를 반환한다.
     *
     * @param keys
     * @return
     */
    public int getInstanceCount(Object... keys) {
        return this.pool.getSize(getKey(keys));
    }

    /**
     * @return 모든 category를 통털어 현재까지 생성된 instance count
     */
    private int getTotalCount(HashMap<K, Integer> map) {
        int total = 0;
        for (K key : map.keySet()) {
            Integer i = map.get(key);
            if (i != null) {
                total += i;
            }
        }
        return total;
    }

    private int getCount(HashMap<K, Integer> map, Object... keys) {
        K key = getKey(keys);
        Integer i = map.get(key);
        if (i == null) {
            return 0;
        }
        return i;
    }

    /**
     * @return 모든 category를 통털어 현재까지 생성된 instance count
     */
    public int getCreationCount() {
        return getTotalCount(creationCount);
    }

    public int getDeletionCount() {
        return getTotalCount(deletionCount);
    }

    public int getCreationCount(Object... keys) {
        return getCount(creationCount, keys);
    }

    public int getDeletionCount(Object... keys) {
        return getCount(deletionCount, keys);
    }

    /**
     * @return 모든 category를 통털어 현재 pool에 들어있는 instance count
     */
    public int getTotalInstanceCount() {
        return this.pool.getTotalSize();
    }

    public Set<K> getCategory() {
        return this.pool.getCategory();
    }

    public void cleanup() {
        for (K category : getCategory()) {
            int size = pool.getSize(category);
            for (int i = 0; i < size; i++) {
                V instance = pool.getInstance(category);
                dispose(category, instance);
            }
            assert pool.getSize(category) == 0;
            Integer count = deletionCount.get(category);
            if (count == null) {
                count = size;
            } else {
                count = count.intValue() + size;
            }
            deletionCount.put(category, count);
        }
        this.pool.cleanup(); // cleanup keys
    }

    class Creater implements Callable<V> {

        private Object[] keys;

        Creater(Object... keys) {
            this.keys = keys;
        }

        @Override
        public V call() throws Exception {
            return create(keys);
        }
    }
}
