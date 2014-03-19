package sg.atom.core.monitor;

public interface IGarbageHandler<T, N> {
	/**
	 * When the object is garbage collected native resource returns to be processed.
	 * @param obj
	 * @return
	 * @author mulova
	 */
	public N getNativeId(T obj);
	
	/**
	 * native resource removed.
	 * @param nativeId
	 * @author mulova
	 */
	public void dispose(N nativeId);
}
