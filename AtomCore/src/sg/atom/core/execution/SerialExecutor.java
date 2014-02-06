/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.execution;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.Executor;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class SerialExecutor implements IManagedExecutor {
    
    final Queue<Runnable> tasks = new ArrayDeque<Runnable>();
    final Executor executor;
    Runnable active;
    
    public SerialExecutor(Executor executor) {
        this.executor = executor;
    }
    
    public synchronized void execute(final Runnable r) {
        tasks.offer(new Runnable() {
            public void run() {
                try {
                    r.run();
                } finally {
                    scheduleNext();
                }
            }
        });
        if (active == null) {
            scheduleNext();
        }
    }
    
    protected synchronized void scheduleNext() {
        if ((active = tasks.poll()) != null) {
            executor.execute(active);
        }
    }
    
    @Override
    public void add(Runnable action) {
        tasks.add(action);
    }
    
    @Override
    public void remove(Runnable action) {
        tasks.remove(action);
    }

    @Override
    public Collection<Runnable> getActions() {
        return tasks;
    }
}
