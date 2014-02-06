package sg.atom.utils.monitor;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author mulova
 *
 * @param <T> referent type
 * @param <N> native resource type
 */
public class GarbageCollector<T, N> {
	private static final Logger log = LoggerFactory.getLogger(GarbageCollector.class);
	private HashMap<PhantomReference<T>, N> phantomMap = new HashMap<PhantomReference<T>, N>();
	private ReferenceQueue<T> refQueue = new ReferenceQueue<T>();
	private IGarbageHandler<T, N> handler;
	private long gcCount;
	
	public GarbageCollector(IGarbageHandler<T, N> handler) {	
		this.handler = handler;
	}
	
	public ReferenceQueue<T> getQueue() {
		return refQueue;
	}
	
	/**
	 * 추가될 때는 이미 native id 값을 알고 있어야 한다.
	 * @param referent
	 */
	public void register(T referent) {
		phantomMap.put(new PhantomReference<T>(referent, refQueue), handler.getNativeId(referent));
	}
	
	@SuppressWarnings("unchecked")
	public int gc() {
		PhantomReference<T> ref;
		int count = 0;
		while ((ref = (PhantomReference<T>) refQueue.poll()) != null) {
			N nativeId = phantomMap.remove(ref);
			ref.clear();
			handler.dispose(nativeId);
			count++;
		}
		gcCount += count;
		return count;
	}
	
	public long getTotalCount() {
		return gcCount;
	}
}
