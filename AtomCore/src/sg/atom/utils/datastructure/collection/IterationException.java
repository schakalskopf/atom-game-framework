package sg.atom.utils.datastructure.collection;

import sg.atom.utils._commons.exception.WrappedRuntimeException;

/**
 * Iteration exception.
 *
 * @author <a href="boisvert@intalio.com">Alex Boisvert</a>
 * @version $Revision: 1.2 $
 */
public class IterationException
        extends WrappedRuntimeException {

    /**
     * Construct a new iteration exception wrapping an underlying exception and
     * providing a message.
     *
     * @param message The exception message
     * @param except The underlying exception
     */
    public IterationException(String message, Exception except) {
        super(message, except);
    }

    /**
     * Construct a new iteration exception with a message.
     *
     * @param message The exception message
     */
    public IterationException(String message) {
        super(message, null);
    }

    /**
     * Construct a new iteration exception wrapping an underlying exception.
     *
     * @param except The underlying exception
     */
    public IterationException(Exception except) {
        super(except);
    }
}
