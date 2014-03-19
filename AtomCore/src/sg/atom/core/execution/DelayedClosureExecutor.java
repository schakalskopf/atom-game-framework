/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.execution;

import groovy.lang.Closure;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executor;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class DelayedClosureExecutor extends DelayedRunnableExecutor {

    public DelayedClosureExecutor() {
        super();
    }

    public DelayedClosureExecutor(int entries) {
        super(entries);
    }

    @Override
    public void execute(Runnable command) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void callAction(DelayedRunnableAction action) {
        if (action.callee != null) {
            if (action.start != null) {
                ((Closure) action.start).call(action.callee);
            }
            if (action.end != null) {
                ((Closure) action.end).call(action.callee);
            }
        } else {
            if (action.start != null) {
                ((Closure) action.start).call();
            }
            if (action.end != null) {
                ((Closure) action.end).call();
            }
        }
    }

    public void delayAction(float delayTime, Closure start, Closure end) {
        queueAction(delayTime, start, end, null);
    }

    public void delayAction(float delayTime, Closure start, Closure end, Object callee) {
        queueAction(delayTime, start, end, this);

    }

    public void queueAction(float delayTime, Closure start, Closure end, Object callee) {
        actionQueue.add(new DelayedRunnableAction(delayTime, start, end, callee));
    }
}
