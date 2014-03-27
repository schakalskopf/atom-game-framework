/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.execution;

import java.util.Collection;
import java.util.concurrent.Executor;

/**
 * Managed executor.
 *
 * @author atomix
 */
@Deprecated
public interface IManagedExecutor extends Executor {

    public abstract void add(Runnable action);

    public abstract void remove(Runnable action);

    public abstract Collection<Runnable> getActions();
}
