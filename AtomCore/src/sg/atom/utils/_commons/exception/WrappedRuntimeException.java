package sg.atom.utils._commons.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * A run-time exception that wraps another exception. The printed stack
 * trace will be that of the wrapped exception.
 *
 * @author <a href="mailto:boisvert@intalio.com">Alex Boisvert</a>
 * @version $Id: WrappedRuntimeException.java,v 1.1 2002/05/31 06:33:20 boisvert Exp $
 */
public class WrappedRuntimeException
    extends RuntimeException
{


    /**
     * The underlying exception.
     */
    private final Exception _except;


    /**
     * Constructs a new runtime exception based on a checked exception.
     *
     * @param message The error message
     * @param except The checked exception
     */
    public WrappedRuntimeException( String message, Exception except )
    {
        super( message == null ? "No message available" : message );

        if ( except instanceof WrappedRuntimeException &&
             ( (WrappedRuntimeException) except )._except != null )
        {
            _except = ( (WrappedRuntimeException) except )._except;
        } else {
            _except = except;
        }
    }


    /**
     * Constructs a new runtime exception based on a checked exception.
     *
     * @param except The checked exception
     */
    public WrappedRuntimeException( Exception except )
    {
        super( except == null || except.getMessage() == null ? "No message available" : except.getMessage() );

        if ( except instanceof WrappedRuntimeException &&
             ( (WrappedRuntimeException) except )._except != null )
        {
            _except = ( (WrappedRuntimeException) except )._except;
        } else {
            _except = except;
        }
    }


    /**
     * Returns the exception wrapped by this runtime exception.
     *
     * @return The exception wrapped by this runtime exception
     */
    public Exception getException()
    {
        return _except;
    }


    public void printStackTrace()
    {
        if ( _except == null ) {
            super.printStackTrace();
        } else {
            _except.printStackTrace();
        }
    }


    public void printStackTrace( PrintStream stream )
    {
        if ( _except == null ) {
            super.printStackTrace( stream );
        } else {
            _except.printStackTrace( stream );
        }
    }


    public void printStackTrace( PrintWriter writer )
    {
        if ( _except == null ) {
            super.printStackTrace( writer );
        } else {
            _except.printStackTrace( writer );
        }
    }

}


