package sg.atom.logic.trigger.spatial.predicate;

import com.google.common.base.Predicate;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.control.GhostControl;
import com.jme3.scene.Spatial;
import java.util.Iterator;
import java.util.List;
import sg.atom.entity.EntityManager;
import sg.atom.logic.trigger.Trigger;
import sg.atom.logic.trigger.spatial.PhysicTrigger;
import sg.atom.world.WorldManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class PhysicTriggerEntityPredicate implements Predicate<Trigger> {
    boolean checkForEntity;
    boolean checkFirstOnly;

    public PhysicTriggerEntityPredicate(boolean checkForEntity, boolean checkFirstOnly) {
        this.checkForEntity = checkForEntity;
        this.checkFirstOnly = checkFirstOnly;
    }

    @Override
    public boolean apply(Trigger input) {
        PhysicTrigger trigger=(PhysicTrigger) input;
        Spatial spatial=trigger.getSpatial();
        GhostControl ghostControl = spatial.getControl(GhostControl.class);
        WorldManager worldManager=trigger.getWorldManager();
        if (ghostControl == null) {
            return false;
        }
        boolean bresult = false;
        if (ghostControl.getOverlappingCount() > 0) {
            List<PhysicsCollisionObject> objects = ghostControl.getOverlappingObjects();
            for (Iterator<PhysicsCollisionObject> it = objects.iterator(); it.hasNext();) {
                PhysicsCollisionObject physicsCollisionObject = it.next();
                trigger.saveResult(physicsCollisionObject);
                boolean isEntity;
                Spatial collidedSpatial = worldManager.getSpatial(physicsCollisionObject);
                if (checkForEntity) {
                    EntityManager entityManager = worldManager.getStageManager().getEntityManager();
                    isEntity = entityManager.isEntitySpatial(collidedSpatial);
                } else {
                    isEntity = true;
                }
                bresult = isEntity;
                if (checkFirstOnly && bresult) {
                    return true;
                }
            }
            return bresult;
        }
        return false;
    }
    
}
