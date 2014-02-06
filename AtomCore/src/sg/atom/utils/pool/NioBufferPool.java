package sg.atom.utils.pool;

import java.nio.*;

/**
 * 동일한 크기가 아니라도 capacity가 큰 bytebuffer를 반환할 수 있다. 
 * @author mulova
 *
 */
public abstract class NioBufferPool<T extends Buffer> extends AbstractArrayPool<T> {
	private boolean nativeBuffer = true;

	public NioBufferPool(int modulus) {
		super(modulus);
	}
	
	public void setNativeBuffer(boolean nativeBuffer) {
		this.nativeBuffer = nativeBuffer;
	}

	@Override
	protected ArrayPoolKey getKey(T instance) {
		return new ArrayPoolKey(instance.limit(), instance.capacity());
	}

	public T getT(int size) {
		return getInstance(size, getCapacity(size));
	}
	
	protected abstract T allocateDirect(int capacity);
	
	protected abstract T allocate(int capacity);
	
	@Override
	protected T create(int limit, int capacity) {
		T buf = null;
		if (nativeBuffer) {
			buf = allocateDirect(capacity);
		} else {
			buf = allocate(capacity);
		}
		return buf;
	}

	@Override
	protected void initialize(ArrayPoolKey key, T instance) {
		instance.limit(key.limit);
		instance.rewind();
	}
}
