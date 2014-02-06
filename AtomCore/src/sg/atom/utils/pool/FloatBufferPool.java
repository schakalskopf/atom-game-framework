package sg.atom.utils.pool;

import java.nio.*;

/**
 * 동일한 크기가 아니라도 capacity가 큰 bytebuffer를 반환할 수 있다. 
 * @author mulova
 *
 */
public class FloatBufferPool extends NioBufferPool<FloatBuffer> {
	private static FloatBufferPool singleton;

	public FloatBufferPool(int modulus) {
		super(modulus);
	}
	
	public FloatBuffer getFloatBuffer(int size) {
		return getInstance(size, getCapacity(size));
	}
	
	public static FloatBufferPool getInstance() {
		if (singleton == null) {
			synchronized (FloatBufferPool.class) {
				if (singleton == null) {
					singleton = new FloatBufferPool(256);
				}
			}
		}
		return singleton;
	}


	@Override
	protected void dispose(ArrayPoolKey key, FloatBuffer value) {
	}

	@Override
	protected void initialize(ArrayPoolKey key, FloatBuffer instance) {
		instance.limit(key.limit);
		instance.rewind();
	}

	@Override
	protected void postRelease(ArrayPoolKey key, FloatBuffer instance) {
	}

	@Override
	protected FloatBuffer allocate(int capacity) {
		return FloatBuffer.allocate(capacity);
	}

	@Override
	protected FloatBuffer allocateDirect(int capacity) {
		ByteBuffer buf = ByteBuffer.allocateDirect(capacity*4);
		buf.order(ByteOrder.nativeOrder());
		return buf.asFloatBuffer();
	}
}
