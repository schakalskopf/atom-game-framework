/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.logic.trigger.spatial;

import sg.atom.logic.trigger.spatial.control.PhysicTriggerControl;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import sg.atom.stage.StageManager;
import sg.atom.entity.EntityManager;
import sg.atom.stage.WorldManager;

/**
 *
 * @author atomix
 */
public class SphereTrigger extends PhysicTriggerControl {

    protected EntityManager entityManager;
    StageManager stageManager;
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
        getTrigger().setShape(new SphereCollisionShape(radius));
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
