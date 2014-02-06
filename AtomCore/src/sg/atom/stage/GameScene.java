/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package sg.atom.stage;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import java.util.LinkedList;
import java.util.List;
import sg.atom.gameplay.GameLevel;

/**
 *
 * @author atomix
 */
public class GameScene {

    List<Camera> CameraList = new LinkedList<Camera>();
    public Camera currentCamera;
    public GameLevel parentLevel;

    public GameScene(GameLevel parentLevel, Camera cam) {
        this.parentLevel = parentLevel;
        if (cam != null) {
            currentCamera = cam;
        } else {
            currentCamera = new Camera();
            currentCamera.setParallelProjection(false);
            currentCamera.setLocation(Vector3f.ZERO);
        }
    }
}
