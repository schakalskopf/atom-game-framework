package sg.atom.logic.trigger;

import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import sg.atom.stage.WorldManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class PhysicTriggerControl implements TriggerControl {

    protected Spatial spatial;
    protected boolean enabled = true;
    protected GhostControl ghostControl;
    protected CollisionShape shape;
    protected float checkTimer = 0;
    protected float checkTime = 1;
    protected WorldManager worldManager;
    ArrayList<PhysicTriggerListener> listeners = new ArrayList<PhysicTriggerListener>();

    public PhysicTriggerControl(WorldManager worldManager) {
        this.worldManager = worldManager;

    }

    public void update(float tpf) {
        if (!enabled) {
            return;
        }
        checkTimer += tpf;
        if (checkTimer >= checkTime) {
            checkTimer = 0;
            if (ghostControl.getOverlappingCount() > 0) {
                List<PhysicsCollisionObject> objects = ghostControl.getOverlappingObjects();
                for (Iterator<PhysicsCollisionObject> it = objects.iterator(); it.hasNext();) {
                    PhysicsCollisionObject physicsCollisionObject = it.next();

                    collided(physicsCollisionObject);
                    Spatial targetEntity = worldManager.getSpatial(physicsCollisionObject);
                    if (conditionCheck(targetEntity)) {
                        collided(targetEntity);
                    }
                }
            }
        }
    }

    boolean conditionCheck(Spatial targetEntity) {
        //entityManager.getEntity(targetEntity);
        return targetEntity != null;
    }

    public void collided(PhysicsCollisionObject sp) {
        for (PhysicTriggerListener tl : listeners) {
            tl.enter(sp);
        }
    }

    public void collided(Spatial sp) {
        for (PhysicTriggerListener tl : listeners) {
            tl.enter(sp);
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

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setShape(CollisionShape shape) {
        this.shape = shape;
    }

    public void createShape() {
        shape = new SphereCollisionShape(10);
    }

    public void createGhostControl() {
        if (ghostControl == null) {
            ghostControl = new GhostControl(shape);
        } else {
            ghostControl.setCollisionShape(shape);
        }
    }

    public GhostControl getGhost() {
        return ghostControl;
    }

    public void setCheckTime(float checkTime) {
        this.checkTime = checkTime;
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        return null;
    }

    @Override
    public void render(RenderManager rm, ViewPort vp) {
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
    }

    @Override
    public void read(JmeImporter im) throws IOException {
    }

    public void addListener(PhysicTriggerListener aListener) {
        listeners.add(aListener);
    }
}
