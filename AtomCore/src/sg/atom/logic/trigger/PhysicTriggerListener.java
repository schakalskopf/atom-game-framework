/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.logic.trigger;

import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.control.GhostControl;
import com.jme3.scene.Spatial;

/**
 *
 * @author hungcuong
 */
public abstract class PhysicTriggerListener implements TriggerListener{
    
    public abstract void enter(Spatial sp);

    public abstract void enter(GhostControl gc);

    public abstract void enter(PhysicsCollisionObject gc);
}
