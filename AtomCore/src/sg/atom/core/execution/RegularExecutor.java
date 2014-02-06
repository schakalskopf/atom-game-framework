/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.execution;

import com.google.common.collect.BiMap;
import java.util.Collection;
import java.util.Iterator;
import sg.atom.core.timing.Regulator;

/**
 *
 * @author cuong.nguyenmanh2
 */
public abstract class RegularExecutor implements IManagedExecutor, Iterable<Runnable> {

    public static float DEFAULT_INTERVAL = 1f;
    protected BiMap<Regulator, Runnable> executionQueue;

    @Override
    public void execute(Runnable action) {
        action.run();
    }

    @Override
    public void add(Runnable action) {
        add(new Regulator(DEFAULT_INTERVAL), action);
    }

    public void add(Regulator regulator, Runnable runnable) {
        this.executionQueue.put(regulator, runnable);
    }

    @Override
    public void remove(Runnable task) {
        this.executionQueue.values().remove(task);
    }

    public abstract void updateRegulators(float fpf);

    @Override
    public Iterator<Runnable> iterator() {
        return executionQueue.values().iterator();
    }

    @Override
    public Collection<Runnable> getActions() {
        return executionQueue.values();
    }
    
    
}
