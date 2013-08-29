package sg.atom.fx.cine;

import com.jme3.cinematic.MotionPath;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.util.ArrayList;

/*
 * CameraDirector helps handling Camera in Cinematographic Styles.
 */
public class CameraDirector extends AbstractControl {

    ArrayList<Mesh> targets;
    ArrayList<Mesh> space;
    ArrayList<Mesh> boundary;
    Camera currentCamera;
    ArrayList<MotionPath> paths;

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    enum ShotMotionType {

        Fixed, Dolly, Crane, Steady, Folow, Rotate
    }

    enum ShotCharacters {

        Both, OverShoulder
    }

    enum RangeType {

        ExCloseUp, MedCloseUp, FullClose, WideClose, Close, MedClose, MedShot, MedFullShot, FullShot, LongShot
    }

    enum AdditionCameraBehavior {

        Shake, Waving
    }

    enum TransitionStyle {

        Flash, Slow, EaseIn, EaseOut
    }

    public void controlCamera() {
    }

    @Override
    protected void controlUpdate(float tpf) {
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
