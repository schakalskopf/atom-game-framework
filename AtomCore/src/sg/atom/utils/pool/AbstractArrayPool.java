package sg.atom.utils.pool;


/**
 * Array를 재사용하는 데 사용되는 base class.
 * 동일한 크기의 개체가 없더라도 capacity가 넉넉한 개체를 반환한다. 
 * @author mulova
 *
 */
public abstract class AbstractArrayPool<T> extends AbstractPool<ArrayPoolKey, T> {
	private int modulus;

	public AbstractArrayPool(int modulus) {
		super();
		this.modulus = modulus;
	}
	
	@Override
	protected final T create(Object... keys) {
		int limit = (Integer)keys[0];
		int capacity = getCapacity(limit);
		
		return create(limit, capacity);
	}
	
	protected abstract T create(int limit, int capacity);

	public int getCapacity(int capacity) {
		if (modulus != 0) {
			int remainder = capacity % modulus;
			if (remainder > 0) {
				capacity = capacity - remainder+ modulus;
			}
		}
		return capacity;
	}

	@Override
	protected final ArrayPoolKey getKey(Object... keys) {
		int limit = (Integer) keys[0];
		return new ArrayPoolKey(limit, getCapacity(limit));
	}
}
