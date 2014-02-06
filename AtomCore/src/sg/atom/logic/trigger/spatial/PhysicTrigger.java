package sg.atom.logic.trigger.spatial;

import sg.atom.logic.trigger.spatial.predicate.PhysicTriggerPredicate;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.CollisionShape;
import java.util.ArrayList;

/**
 *
 * @author atomix
 */
public class PhysicTrigger extends SpatialTrigger {
    
    protected CollisionShape shape;
    ArrayList<PhysicsCollisionObject> results;
    
    public CollisionShape getShape() {
        return shape;
    }
    
    public PhysicTrigger(CollisionShape shape) {
        this.shape = shape;
        this.activeConditionPredicate = new PhysicTriggerPredicate();
        //this.enableConditionPredicate = new;
    }
    
    public void setShape(CollisionShape shape) {
        this.shape = shape;
    }
    
    public void saveResult(PhysicsCollisionObject physicsCollisionObject) {
        results.add(physicsCollisionObject);
    }
}
