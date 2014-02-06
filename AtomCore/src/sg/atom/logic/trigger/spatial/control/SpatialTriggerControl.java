package sg.atom.logic.trigger.spatial.control;

import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import sg.atom.logic.trigger.Trigger;
import sg.atom.logic.trigger.spatial.SpatialTrigger;
import sg.atom.stage.WorldManager;

/**
 * SpatialTriggerControl is the default implementation of trigger which related
 * to Spatial.
 *
 * Add this to a Spatial-> A SpatialTrigger will be add to that specific Spatial
 *
 *
 * @author atomix
 */
public abstract class SpatialTriggerControl<T extends Trigger> extends AbstractControl {

    protected WorldManager worldManager;
    SpatialTrigger trigger;

    public SpatialTriggerControl(WorldManager worldManager) {
        this.worldManager = worldManager;
    }

    public SpatialTriggerControl(WorldManager worldManager, SpatialTrigger trigger) {
        this.worldManager = worldManager;
        this.trigger = trigger;
    }

    public SpatialTrigger getTrigger() {
        return trigger;
    }
    
}
