package sg.atom.utils.algorimth.travel;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

public abstract class DepthFirstTraversal<N> {

    public abstract Collection<N> getChildren(N node);

    /**
     * @param node
     * @return false if there is no need to process any further
     * @author mulova
     */
    protected abstract boolean process(N node);

    public void traverse(N root) {
        HashSet<N> traversed = new HashSet<N>();
        LinkedList<N> queue = new LinkedList<N>();
        queue.addFirst(root);
        while (queue.isEmpty() == false) {
            N current = queue.peekFirst();
            if (traversed.contains(current)) {
                queue.removeFirst();
                if (process(current) == false) {
                    return;
                }
            } else {
                Collection<N> children = getChildren(current);
                int i = 0;
                for (N n : children) {
                    queue.add(i, n);
                    i++;
                }
            }
            traversed.add(current);
        }
    }
}
