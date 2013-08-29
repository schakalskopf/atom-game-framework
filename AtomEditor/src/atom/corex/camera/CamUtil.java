/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atom.corex.camera;

import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.FastMath;
import com.jme3.renderer.Camera;

/**
 *
 * @author hungcuong
 */
public class CamUtil implements ActionListener {

    private Camera cam;
    private FlyByCamera flyCam;
    private InputManager inputManager;

    public CamUtil(Camera cam, FlyByCamera flyCam, InputManager inputManager) {
        this.cam = cam;
        this.flyCam = flyCam;
        this.inputManager = inputManager;

    }

    public void onAction(String name, boolean value, float tpf) {
        if (!value) {
            return;
        }

        if (name.equals("CamDrag")) {
            flyCam.setDragToRotate(!flyCam.isDragToRotate());
            if (flyCam.isDragToRotate()) {
                flyCam.setRotationSpeed(0);
            } else {
                flyCam.setRotationSpeed(1f);
            }
            System.out.print("CamDrag" + flyCam.isDragToRotate());

        } else if (name.equals("CamParallel")) {
            cam.setParallelProjection(!cam.isParallelProjection());
            System.out.print("CamParallel" + cam.isParallelProjection());
        } else if (name.equals("CamFOV+")) {
            setCamFOV(getCamFOV() + 5);
            System.out.print("CamFOV : " + getCamFOV());
        } else if (name.equals("CamFOV-")) {
            setCamFOV(getCamFOV() - 5);
            System.out.print("CamFOV : " + getCamFOV());
        }
    }

    public Camera getCam() {
        return cam;
    }

    public void setCam(Camera cam) {
        this.cam = cam;
    }

    public void initMyCustomKeyboard() {
        inputManager.addMapping("CamDrag", new KeyTrigger(KeyInput.KEY_X));
        inputManager.addMapping("CamParallel", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("CamFOV+", new KeyTrigger(KeyInput.KEY_ADD));
        inputManager.addMapping("CamFOV-", new KeyTrigger(KeyInput.KEY_SUBTRACT));

        inputManager.addListener(this, "CamDrag", "CamParallel", "CamFOV+", "CamFOV-");


    }

    public void setCamFOV(float angle) {
        // derive fovY value
        float h = cam.getFrustumTop();


        float w = cam.getFrustumRight();


        float aspect = w / h;



        float near = cam.getFrustumNear();


        float fovY = angle; //* FastMath.DEG_TO_RAD;
        /*
        float fovY = FastMath.atan(h / near)
        / (FastMath.DEG_TO_RAD * .5f);
         */
        //fovY += value * 0.1f;

        h = FastMath.tan(fovY * FastMath.DEG_TO_RAD * .5f) * near;
        w = h * aspect;

        cam.setFrustumTop(h);
        cam.setFrustumBottom(-h);
        cam.setFrustumLeft(-w);
        cam.setFrustumRight(w);


    }

    public float getCamFOV() {
        float h = cam.getFrustumTop();
        float w = cam.getFrustumRight();
        float near = cam.getFrustumNear();
        float fovY = FastMath.atan(h / near)
                / (FastMath.DEG_TO_RAD * .5f);
        //return fovY * FastMath.RAD_TO_DEG;

        return fovY;
    }

    public void setFlyByCam(boolean value) {
        flyCam.setDragToRotate(value);
    }
}
