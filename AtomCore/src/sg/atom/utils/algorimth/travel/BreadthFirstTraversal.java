package sg.atom.utils.algorimth.travel;

import java.util.Collection;
import java.util.LinkedList;

public abstract class BreadthFirstTraversal<N> {

    public abstract Collection<N> getChildren(N node);

    /**
     * @param node
     * @return false if there is no need to process any further
     * @author mulova
     */
    protected abstract boolean process(N node);

    public void traverse(N root) {
        LinkedList<N> queue = new LinkedList<N>();
        queue.addLast(root);
        while (queue.isEmpty() == false) {
            N current = queue.removeFirst();
            if (process(current) == false) {
                return;
            }
            for (N n : getChildren(current)) {
                queue.addLast(n);
            }
        }
    }
}
