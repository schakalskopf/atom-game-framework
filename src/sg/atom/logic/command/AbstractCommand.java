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
 * @author cuong.nguyenmanh2
 */
/**
 *
 * @author normenhansen
 */
public abstract class AbstractCommand implements Command {

    protected int priority = -1;
    protected long playerId = -1;
    protected long entityId = -1;
    protected Spatial entity = null;
    protected long targetPlayerId = -1;
    protected long targetEntityId = -1;
    protected Spatial targetEntity = null;
    protected Vector3f targetLocation = new Vector3f();
    private boolean running = false;
    protected WorldManager world;

    public abstract State doCommand(float tpf);

    public Command initialize(WorldManager world, long playerId, long entityId, Spatial spat) {
        this.world = world;
        this.playerId = playerId;
        this.entityId = entityId;
        this.entity = spat;
        return this;
    }

    public TargetResult setTargetEntity(Spatial spat) {
        this.targetPlayerId = (Long) spat.getUserData("player_id");
        this.targetEntityId = (Long) spat.getUserData("entity_id");
        this.targetEntity = spat;
        targetLocation.set(spat.getWorldTranslation());
        return TargetResult.Accept;
    }

    public TargetResult setTargetLocation(Vector3f location) {
        targetLocation.set(location);
        return TargetResult.Accept;
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
