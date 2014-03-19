/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.execution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.concurrent.Executor;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class DelayedRunnableExecutor implements Executor {

    protected ArrayList<DelayedRunnableAction> actionQueue = new ArrayList<DelayedRunnableAction>(5);
    public static int DEFAULT_QUEUE_ENTRIES = 4;

    public DelayedRunnableExecutor() {
        actionQueue = new ArrayList<DelayedRunnableAction>(DEFAULT_QUEUE_ENTRIES);
    }

    public DelayedRunnableExecutor(int entries) {
        this.actionQueue = new ArrayList<DelayedRunnableAction>(entries);
    }

    @Override
    public void execute(Runnable command) {
        command.run();
    }

    public void callAction(DelayedRunnableAction action) {
        if (action.start != null) {
            action.start.run();
        }
        if (action.end != null) {
            action.end.run();
        }

    }

    public void delayAction(float delayTime, Runnable start, Runnable end) {
        queueAction(delayTime, start, end);
    }

    public void queueAction(float delayTime, Runnable start, Runnable end) {
        actionQueue.add(new DelayedRunnableAction(delayTime, start, end));
    }

    public void queueTask(float delayTime, TimerTask task) {
    }

    public void updateActionQueue(float tpf) {
        Iterator<DelayedRunnableAction> iterator = actionQueue.iterator();
        while (iterator.hasNext()) {
            DelayedRunnableAction act = iterator.next();
            act.delayTime -= tpf;
            if (act.delayTime <= 0) {
                callAction(act);
                iterator.remove();
            }
        }
    }
}
