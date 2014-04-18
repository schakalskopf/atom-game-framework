package sg.atom.utils.repository.exceptions;

/**
 * A persistence exception can be thrown when an error occurs in underlying
 * persistence code. By encapsulating errors, one retains the ability to make
 * changes to the implementation structure without affecting the interface to
 * persistence services presented to the application.
 */
public class PersistenceException extends Exception {

    /**
     * Constructs a persistence exception with the specified error message.
     */
    public PersistenceException(String message) {
        super(message);
    }

    /**
     * Constructs a persistence exception with the specified error message and
     * the chained causing event.
     */
    public PersistenceException(String message, Exception cause) {
        super(message);
        initCause(cause);
    }

    /**
     * Constructs a persistence exception with the specified chained causing
     * event.
     */
    public PersistenceException(Exception cause) {
        initCause(cause);
    }
}
