/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.controls;

import com.jme3.input.controls.ActionListener;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 * AtomCharacterControl hook to JME3 Spatial to control the Character. This is a
 * Common approach! This is in a higher level than SpatialEntityControl!
 *
 * <h4>Features:</h4>
 *
 * <ul> <li>Integrated to Atom framework: With StageManager and SteeringManager.
 * With AtomCharacter / Entity/ Spatial. And the chains for : Animation, AI,
 * Movement, Grouping, Configure, Details!
 *
 * <li>EventBus intergerate for normal event dispatching. It's also reactive by
 * default since Atom1.1
 *
 * <li>Clone in AtomCharacterControl is a deep clone by mapping and with levels
 * of detail (LOD) concerning!
 *
 * </ul>
 *
 * @author atomix
 */
public abstract class AtomCharacterControl extends AbstractControl implements ActionListener {

    @Override
    protected void controlUpdate(float tpf) {
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        return this;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
    }

    public abstract void setLocation(Vector3f location);

    public abstract void setMoveSpeed(float speed);

    public abstract Vector3f getLocation();
}
