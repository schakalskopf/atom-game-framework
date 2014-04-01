/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.logic.command;

import com.jme3.scene.Spatial;
import sg.atom.stage.WorldManager;

/**
 *
 * @author normenhansen, atomix
 */
public abstract class AbstractActorCommand implements AtomCommand {

    protected int priority = -1;
    protected long actorId = -1;
    protected long entityId = -1;
    protected Spatial entity = null;
    private boolean running = false;
    protected WorldManager world;

    public abstract CommandProcessingState doCommand(float tpf);

    public AtomCommand initialize(WorldManager world, long playerId, long entityId, Spatial spat) {
        this.world = world;
        this.actorId = playerId;
        this.entityId = entityId;
        this.entity = spat;
        return this;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void cancel() {
        entity.getControl(CommandControl.class).removeCommand(this);
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
