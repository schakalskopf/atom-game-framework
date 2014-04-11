/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.repository.defaultimpl;

import java.util.concurrent.atomic.AtomicBoolean;
import sg.atom.core.timing.TimeStamp;
import sg.atom.utils.repository.transaction.Transaction;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class DefaultTransaction implements Transaction {

    @Override
    public TimeStamp getTimeStamp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TimeStamp.Duration getDuration() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AtomicBoolean completed() throws Transaction.TransactionTimeoutException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AtomicBoolean stopTransaction() throws Transaction.TransactionAbortedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AtomicBoolean kill() throws Transaction.TransactionAbortedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AtomicBoolean commit() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AtomicBoolean rollBack() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AtomicBoolean isActive() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
