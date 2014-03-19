/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.logic.command;

import com.jme3.scene.Spatial;
import sg.atom.stage.WorldManager;

/**
 * Basic interface for Commands pattern as in Gof Chain of Resposibility.
 *
 * @author atomix
 */
public interface Command {

    public static enum CommandProcessingState {

        Finished,
        Continuing,
        Blocking,}
    /*
     public static enum TargetResult {

     Deny,
     Accept,
     DenyFriendly,
     DenyEnemy,
     AcceptEnemy,
     AcceptFriendly
     }
     */

    public String getName();

    public CommandProcessingState doCommand(float tpf);

    public Command initialize(WorldManager world, long playerId, long entityId, Spatial spat);

    public int getPriority();

    public void setPriority(int priority);

    public boolean isRunning();

    public void setRunning(boolean running);

    public void cancel();
}
