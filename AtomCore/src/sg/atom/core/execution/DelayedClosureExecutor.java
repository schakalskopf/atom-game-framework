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
public class DelayedClosureExecutor implements Executor {

    private ArrayList<DelayedAction> actionQueue;
    public static int DEFAULT_QUEUE_ENTRIES = 4;

    public DelayedClosureExecutor() {
        actionQueue = new ArrayList<DelayedAction>(DEFAULT_QUEUE_ENTRIES);
    }

    public DelayedClosureExecutor(int entries) {
        this.actionQueue = new ArrayList<DelayedAction>(entries);
    }

    @Override
    public void execute(Runnable command) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    class DelayedAction {

        float delayTime;
        Closure start;
        Closure end;
        Object callee;

        public DelayedAction(float delayTime, Closure start, Closure end, Object callee) {
            this.delayTime = delayTime;
            this.start = start;
            this.end = end;
            this.callee = callee;
        }
    }

    public void callAction(DelayedAction action) {
        if (action.callee != null) {
            if (action.start != null) {
                action.start.call(action.callee);
            }
            if (action.end != null) {
                action.end.call(action.callee);
            }
        } else {
            if (action.start != null) {
                action.start.call();
            }
            if (action.end != null) {
                action.end.call();
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
        actionQueue.add(new DelayedAction(delayTime, start, end, callee));
    }

    public void updateActionQueue(float tpf) {
        Iterator<DelayedAction> iterator = actionQueue.iterator();
        while (iterator.hasNext()) {
            DelayedAction act = iterator.next();
            act.delayTime -= tpf;
            if (act.delayTime <= 0) {
                callAction(act);
                iterator.remove();
            }
        }
    }
}
