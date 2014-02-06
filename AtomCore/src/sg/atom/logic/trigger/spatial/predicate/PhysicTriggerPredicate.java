/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.logic.trigger.spatial.predicate;

import com.google.common.base.Predicate;
import com.jme3.bullet.control.GhostControl;
import com.jme3.scene.Spatial;
import sg.atom.logic.trigger.Trigger;
import sg.atom.logic.trigger.spatial.PhysicTrigger;
import sg.atom.stage.WorldManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class PhysicTriggerPredicate implements Predicate<Trigger> {

    public PhysicTriggerPredicate() {
    }

    @Override
    public boolean apply(Trigger input) {
        PhysicTrigger trigger = (PhysicTrigger) input;
        Spatial spatial = trigger.getSpatial();
        GhostControl ghostControl = spatial.getControl(GhostControl.class);
        WorldManager worldManager = trigger.getWorldManager();

        if (ghostControl == null) {
            return false;
        }
        boolean bresult = false;
        if (ghostControl.getOverlappingCount() > 0) {
            return true;
        }
        return false;
    }
}
