/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import sg.atom.stage.WorldManager;

/**
 *
 * @author hungcuong
 */
public class WorldTestHelper {

    WorldManager worldManager;
    Node rootNode;
    AssetManager assetManager;
    private Material matBullet;

    public WorldTestHelper(WorldManager worldManager) {

        this.rootNode = worldManager.getRootNode();
        this.assetManager = worldManager.getAssetManager();
    }

    private void createWall(float bLength, float bHeight, float bWidth) {
        float xOff = -144;
        float zOff = -40;
        float startpt = bLength / 4 - xOff;
        float height = 6.1f;
        Box brick = new Box(Vector3f.ZERO, bLength, bHeight, bWidth);
        brick.scaleTextureCoordinates(new Vector2f(1f, .5f));
        for (int j = 0; j < 15; j++) {
            for (int i = 0; i < 4; i++) {
                Vector3f vt = new Vector3f(i * bLength * 2 + startpt, bHeight + height, zOff);
                addBrick(vt, brick);
            }
            startpt = -startpt;
            height += 1.01f * bHeight;
        }
    }

    private void addBrick(Vector3f ori, Box brick) {
        Geometry reBoxg = new Geometry("brick", brick);
        reBoxg.setMaterial(matBullet);
        reBoxg.setLocalTranslation(ori);
        reBoxg.addControl(new RigidBodyControl(1.5f));
        reBoxg.setShadowMode(ShadowMode.CastAndReceive);
        this.rootNode.attachChild(reBoxg);
        worldManager.getPhysicsSpace().add(reBoxg);
    }

    public Geometry createRedBox() {
        /** create a red box straight above the blue one at (1,3,1) */
        Box box2 = new Box(new Vector3f(1, 3, 1), 1, 1, 1);
        Geometry red = new Geometry("Box", box2);
        Material mat2 = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Red);
        red.setMaterial(mat2);
        return red;
    }
}
