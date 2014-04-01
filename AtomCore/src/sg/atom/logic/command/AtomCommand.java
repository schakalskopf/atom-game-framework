/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.logic.command;

import com.jme3.scene.Spatial;

/**
 * Basic interface for Commands pattern as in Gof Chain of Resposibility.
 *
 * <p>Different to Command in Apache Chain.
 *
 * @author atomix
 */
public interface AtomCommand {

    public static enum CommandProcessingState {

        Finished,
        Continuing,
        Blocking,}

    public String getName();

    public CommandProcessingState doCommand(float tpf);

    public AtomCommand initialize(Object context, long playerId, long entityId, Spatial spat);

    public int getPriority();

    public void setPriority(int priority);

    public boolean isRunning();

    public void setRunning(boolean running);

    public void cancel();
}
