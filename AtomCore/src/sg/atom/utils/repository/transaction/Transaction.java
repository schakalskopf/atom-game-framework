/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.repository.transaction;

import java.util.concurrent.atomic.AtomicBoolean;
import sg.atom.core.timing.TimeStamp;

/**
 * A transaction is a set of Datastore operations on one or more entities. Each
 * transaction is guaranteed to be atomic, which means that transactions are
 * never partially applied. Either all of the operations in the transaction are
 * applied, or none of them are applied
 *
 * @author cuong.nguyenmanh2
 */
public interface Transaction {

    public class TransactionException extends RuntimeException {
    }

    public class TransactionAbortedException extends TransactionException {
    }

    public class TransactionConflictException extends TransactionException {
    }

    public class TransactionTimeoutException extends TransactionException {
    }

    public class TransactionInteruptedException extends TransactionException {
    }

    TimeStamp getTimeStamp();

    TimeStamp.Duration getDuration();

    AtomicBoolean completed() throws TransactionTimeoutException;

    AtomicBoolean stopTransaction() throws TransactionAbortedException;

    AtomicBoolean kill() throws TransactionAbortedException;

    AtomicBoolean commit();

    AtomicBoolean rollBack();

    AtomicBoolean isActive();
}
