package sg.atom.logic.trigger.spatial.control;

import com.jme3.bullet.control.GhostControl;
import com.jme3.scene.Spatial;
import sg.atom.logic.trigger.spatial.PhysicTrigger;
import sg.atom.world.WorldManager;

/**
 *
 * @author atomix
 */
public abstract class PhysicTriggerControl extends SpatialTriggerControl {
    
    protected GhostControl ghostControl;
    /* FIXME: Should be replaced with Regulator */
    protected float checkTimeWaited = 0;
    protected float checkTimeInterval = 1;
    
    public PhysicTriggerControl(WorldManager worldManager) {
        super(worldManager);
    }
    
    public PhysicTriggerControl(WorldManager worldManager, PhysicTrigger trigger) {
        super(worldManager, trigger);
    }
    
    @Override
    public void controlUpdate(float tpf) {
        
        checkTimeWaited += tpf;
        if (checkTimeWaited >= checkTimeInterval) {
            checkTimeWaited = 0;
            trigger.apply(null);
        }
    }
    
    public void setSpatial(Spatial spatial) {
        if (spatial == null) {
            detachControl();
            this.spatial = spatial;
            return;
        }
        this.spatial = spatial;
        if (ghostControl == null) {
            createShape();
            createGhostControl();
        }
        attachControl();
        
    }
    
    public void attachControl() {
        spatial.addControl(ghostControl);
        worldManager.getPhysicsSpace().add(ghostControl);
    }
    
    public void detachControl() {
        if (this.spatial != null) {
            this.spatial.removeControl(ghostControl);
            worldManager.getPhysicsSpace().remove(ghostControl);
        }
    }
    
    public abstract void createShape();
    
    public void createGhostControl() {
        if (ghostControl == null) {
            ghostControl = new GhostControl(getTrigger().getShape());
        } else {
            ghostControl.setCollisionShape(getTrigger().getShape());
        }
    }
    
    public GhostControl getGhost() {
        return ghostControl;
    }
    
    public PhysicTrigger getTrigger() {
        return (PhysicTrigger) trigger;
    }
    
    public void setCheckTimeInterval(float checkTimeInterval) {
        this.checkTimeInterval = checkTimeInterval;
    }
    
    public float getCheckTimeInterval() {
        return checkTimeInterval;
    }
}
