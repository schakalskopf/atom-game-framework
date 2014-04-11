/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.repository.transaction;

import java.util.concurrent.atomic.AtomicBoolean;
import sg.atom.core.timing.TimeStamp;

/**
 * Simple implementation of JRS907.
 *
 * http://en.wikipedia.org/wiki/Java_Transaction_API
 *
 * <p>A transaction defines a logical unit of work that either completely
 * succeeds or produces no result at all. If the attempted action failed (raise
 * Error or throw ugly Exception), the system will automaticly roll back to its
 * "valid" state.
 *
 * <p>This resemble a database but not intend to be that big: single user, no
 * sql or procedures... but triggers! Some functions are provided like
 * commitChanges(), rollBackChanges() and all depend and require TimeStamp and a
 * MVCC Tree structure.
 *
 * <p><b>This will go to a complete implementation of multi-user, no SQL in the
 * future!</b>
 *
 * @author cuong.nguyenmanh2
 */
public interface TransactionSystem {

    public void commitChanges();

    public void rollBackChanges();

    public Transaction beginTransaction();
    
    public AtomicBoolean endTransaction();
}
