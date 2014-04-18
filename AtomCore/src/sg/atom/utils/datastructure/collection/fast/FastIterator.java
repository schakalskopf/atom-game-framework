package sg.atom.utils.datastructure.collection.fast;

import sg.atom.utils.datastructure.collection.IterationException;

/**
 * Fast and simple iterator.
 *
 * @version $Revision: 1.2 $
 * @author <a href="boisvert@intalio.com">Alex Boisvert</a>
 */
public abstract class FastIterator {

    /**
     * Returns the next element in the interation.
     *
     * @return the next element in the iteration, or null if no more element.
     */
    public abstract Object next()
            throws IterationException;
}
