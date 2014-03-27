package sg.atom.utils.repository.pool;

import java.nio.*;

/**
 * 동일한 크기가 아니라도 capacity가 큰 bytebuffer를 반환할 수 있다. 
 * @author mulova
 *
 */
public class ByteBufferPool extends NioBufferPool<ByteBuffer> {
	private static ByteBufferPool singleton;

	public ByteBufferPool(int modulus) {
		super(modulus);
	}
	
	public ByteBuffer getByteBuffer(int size) {
		return getInstance(size, getCapacity(size));
	}
	
	public static ByteBufferPool getInstance() {
		if (singleton == null) {
			synchronized (ByteBufferPool.class) {
				if (singleton == null) {
					singleton = new ByteBufferPool(256);
				}
			}
		}
		return singleton;
	}


	@Override
	protected void dispose(ArrayPoolKey key, ByteBuffer value) {
	}

	@Override
	protected void initialize(ArrayPoolKey key, ByteBuffer instance) {
		instance.limit(key.limit);
		instance.rewind();
	}

	@Override
	protected void postRelease(ArrayPoolKey key, ByteBuffer instance) {
	}

	@Override
	protected ByteBuffer allocate(int capacity) {
		return ByteBuffer.allocate(capacity);
	}

	@Override
	protected ByteBuffer allocateDirect(int capacity) {
		ByteBuffer buf = ByteBuffer.allocateDirect(capacity);
		buf.order(ByteOrder.nativeOrder());
		return buf;
	}
}
