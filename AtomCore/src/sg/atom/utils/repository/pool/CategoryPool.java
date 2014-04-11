package sg.atom.utils.repository.pool;

import java.util.*;

/**
 * <code>
 * get() {
 * 	i = pool.instance(key)
 *
 * }
 *
 * reuse() {
 * 	pool.release(i, key);
 * }
 * </code>
 *
 * @author mulova
 * @param <V>
 */
public class CategoryPool<K, V> {

    private final Map<K, LinkedList<V>> pool = new HashMap<K, LinkedList<V>>(100);
    private boolean checkDuplicate;

    private LinkedList<V> getSlot(final K key) {
        LinkedList<V> slot = this.pool.get(key);
        if (slot == null) {
            slot = new LinkedList<V>();
            this.pool.put(key, slot);
        }
        return slot;
    }

    public int getSize(final K key) {
        final LinkedList<V> slot = getSlot(key);
        return slot.size();
    }

    public int getTotalSize() {
        int total = 0;
        for (K key : this.pool.keySet()) {
            total += this.pool.get(key).size();
        }
        return total;
    }

    /**
     * @return null을 반환할 수 있다.
     */
    public V getInstance(final K key) {
        final LinkedList<V> slot = getSlot(key);
        try {
            synchronized (slot) {
                final V instance = slot.poll();
                return instance;
            }
        } catch (Exception e) {
            assert false; // 호출 중간에 누군
        }
        return null;
    }

    /**
     * pool에서 제거하지 않는다.
     *
     * @param key
     * @return null을 반환할 수 있다.
     */
    public V peek(final K key) {
        final LinkedList<V> slot = getSlot(key);
        final V instance = slot.peek();
        return instance;
    }

    public void release(final K key, final V instance) {
        if (instance == null) {
            return;
        }
        final LinkedList<V> slot = getSlot(key);
        synchronized (slot) {
            if (this.checkDuplicate) {
                for (V v : slot) {
                    if (v == instance) {
                        return;
                    }
                }
            }
            slot.addFirst(instance);
        }
    }

    /**
     * 중복된 개체 추가를 허용할 것인가?
     *
     * @param duplicate 참이면 중복을 허용하지 않는다.
     */
    public void checkDuplicate(final boolean duplicate) {
        this.checkDuplicate = duplicate;
    }

    public Set<K> getCategory() {
        return this.pool.keySet();
    }

    public void cleanup() {
        this.pool.clear();
    }
}
