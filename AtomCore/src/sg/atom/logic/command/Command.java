/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.logic.command;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import sg.atom.stage.WorldManager;

/**
 *
@author atomix
 */
/**
 * Basic interface for AI commands
 * @author normenhansen
 */
public interface Command {
    enum State{
        Finished,
        Continuing,
        Blocking,
    }

    enum TargetResult{
        Deny,
        Accept,
        DenyFriendly,
        DenyEnemy,
        AcceptEnemy,
        AcceptFriendly
    }

    public String getName();

    public State doCommand(float tpf);

    public Command initialize(WorldManager world, long playerId, long entityId, Spatial spat);

    public TargetResult setTargetEntity(Spatial spat);

    public TargetResult setTargetLocation(Vector3f location);

    public int getPriority();

    public void setPriority(int priority);

    public boolean isRunning();

    public void setRunning(boolean running);

    public void cancel();
}

