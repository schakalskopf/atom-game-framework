/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.script.engine.groovy.execution;

import groovy.lang.Closure;
import sg.atom.utils.execution.DelayedRunnableAction;
import sg.atom.utils.execution.DelayedRunnableExecutor;

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
        if (action.getCallee() != null) {
            if (action.getStart() != null) {
                ((Closure) action.getStart()).call(action.getCallee());
            }
        } else {
            if (action.getStart() != null) {
                ((Closure) action.getStart()).call();
            }
        }
    }

    public void delayAction(float delayTime, Closure start) {
        queueAction(delayTime, start, null);
    }

    public void delayAction(float delayTime, Closure start, Object callee) {
        queueAction(delayTime, start, this);

    }

    public void queueAction(float delayTime, Closure start, Object callee) {
        actionQueue.add(new DelayedRunnableAction(delayTime, start, callee));
    }
}
