package sg.atom.core.monitor;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

public class MemoryTracer {

    private static final Logger log = LoggerFactory.getLogger(MemoryTracer.class);
    private static Queue<WeakReference<Object>> refQueue = new LinkedList<WeakReference<Object>>();
    private static boolean enabled;

    public static boolean isEnabled() {
        return MemoryTracer.enabled;
    }

    public static void setEnabled(final boolean enabled0) {
        MemoryTracer.enabled = enabled0;
    }

    public static void clear() {
        refQueue.clear();
    }

    public static void printLiveResources() {
        if (!MemoryTracer.enabled) {
            return;
        }
        System.gc();
        System.gc();
        WeakReference<Object> ref = null;
        final Queue<WeakReference<Object>> queue = new LinkedList<WeakReference<Object>>();
        while ((ref = MemoryTracer.refQueue.poll()) != null) {
            final Object obj = ref.get();
            if (obj != null) {
                int hash = obj.hashCode();
                //log.warn("Live resource: hash [{}] {}", hash , StringUtil.toString(obj));
                queue.add(ref);
            }
        }
        MemoryTracer.refQueue = queue;
        log.warn("Live Resource Count: {}", refQueue.size());
    }

    public static Object trace(final Object obj) {
        if (!MemoryTracer.enabled) {
            return null;
        }
        /*		if (obj instanceof Node) {
         Node node = (Node)obj;
         List<Spatial> children = node.getChildren();
         for (Spatial child : children)
         addToQueue(child);
         }*/
        final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        final WeakReference<Object> ref = new WeakReference<Object>(obj);
        //MemoryTracer.log("MemoryTracer> adding {} [hash] {}", obj, ref.get().hashCode());
        //MemoryTracer.log("              ", stack[2]);
        MemoryTracer.refQueue.add(ref);

        return obj;
    }
}
