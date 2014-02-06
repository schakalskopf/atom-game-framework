/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.execution;

import java.util.Collection;
import java.util.concurrent.Executor;

/**
 *
 * @author atomix
 */
public interface IManagedExecutor extends Executor {

    public abstract void add(Runnable action);

    public abstract void remove(Runnable action);
    
    public abstract Collection<Runnable> getActions();
}
