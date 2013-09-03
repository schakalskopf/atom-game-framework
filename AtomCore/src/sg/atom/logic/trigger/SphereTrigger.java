/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.logic.trigger;

import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import sg.atom.stage.StageManager;
import sg.atom.entity.EntityManager;
import sg.atom.stage.WorldManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class SphereTrigger extends PhysicTriggerControl {

    protected EntityManager entityManager;
    StageManager stageManager;
    WorldManager worldManager;
    private float radius;

    public SphereTrigger(StageManager stageManager, WorldManager worldManager, float radius) {
        super(worldManager);
        this.stageManager = stageManager;
        this.entityManager = stageManager.getEntityManager();
        this.radius = radius;
    }

    public void setGhostRadius(float radius) {
        this.radius = radius;
        createShape();
        createGhostControl();
    }

    @Override
    public void createShape() {
        shape = new SphereCollisionShape(radius);
    }
}
