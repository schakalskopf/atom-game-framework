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
import sg.atom.stage.actor.AtomActor;

/**
 * GameScene (common) is a unit of Stage activity. A scene defined with a group
 * of selected actors, camera positions, and a level.
 *
 * <p>In addition, a GameScene has trigger to hook into its routines. For ex
 * background music start to play in the start of the screen.
 *
 * @author atomix
 */
public class GameScene {

    protected List<Camera> cameraList = new LinkedList<Camera>();
    public Camera currentCamera;
    public GameLevel parentLevel;
    private List<AtomActor> actors;

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

    public List<AtomActor> getActors() {
        return actors;
    }

    public void sceneStart() {
    }

    public void sceneEnd() {
    }
}
