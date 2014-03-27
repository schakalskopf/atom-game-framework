package sg.atom.utils.repository.pool;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class StringBuilderPool {

    private static ConcurrentLinkedQueue<StringBuilder> builder = new ConcurrentLinkedQueue<StringBuilder>();

    public static StringBuilder fetchInstance() {
        StringBuilder instance = builder.poll();
        if (instance != null) {
            instance.setLength(0);
            return instance;
        }
        return new StringBuilder(32);
    }

    public static void releaseInstance(StringBuilder instance) {
        builder.add(instance);
    }
}
