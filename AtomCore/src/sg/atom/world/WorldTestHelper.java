/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

/**
 * A Helper class go along with WorldManager to help create simple stuff for
 * testing. Mainly extract from examples of JME3 Testsuite!
 *
 * FIXME: Going to change to Utils to get name convention persist.
 * @author atomix
 */
public class WorldTestHelper {

    protected WorldManager worldManager;
    protected AssetManager assetManager;
    //Nodes
    public Node rootNode;
    public Node gizmo = new Node("gizmo");
    public Geometry ground;
    public Geometry gridGeo;
    //
    //Lights
    //Materials
    public Material matBullet;
    public Material unshadedMat;
    public Material lightMat;
    public Material matGround;

    public WorldTestHelper(WorldManager worldManager) {
        this.rootNode = worldManager.getWorldNode();
        this.assetManager = worldManager.getAssetManager();
    }

    public WorldTestHelper(Node rootNode, AssetManager assetManager) {
        this.rootNode = rootNode;
        this.assetManager = assetManager;
    }

    public Material getColoredMat(ColorRGBA color) {
        return MaterialManager.getDefaultInstance(assetManager).getColoredMat(color);
    }

    public void createTerrain() {
        //FIXME: Use generic terrain.
    }

    public void createFlatGround(int size) {
        Box b = new Box(new Vector3f(0, -0.1f, size / 2), size, 0.01f, size);
        b.scaleTextureCoordinates(new Vector2f(40, 40));
        ground = new Geometry("Ground", b);
        matGround = getMatGround();

        ground.setMaterial(matGround);
        ground.setShadowMode(ShadowMode.Receive);
        rootNode.attachChild(ground);
    }

    public void createWall(float bLength, float bHeight, float bWidth) {
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

    public void addBrick(Vector3f ori, Box brick) {
        Geometry reBoxg = new Geometry("brick", brick);
        reBoxg.setMaterial(matBullet);
        reBoxg.setLocalTranslation(ori);
        reBoxg.addControl(new RigidBodyControl(1.5f));
        reBoxg.setShadowMode(ShadowMode.CastAndReceive);
        this.rootNode.attachChild(reBoxg);
        worldManager.getPhysicsSpace().add(reBoxg);
    }

    public Geometry createRedBox() {
        Box box2 = new Box(new Vector3f(1, 3, 1), 1, 1, 1);
        Geometry red = new Geometry("Box", box2);
        Material mat2 = getUnshadedMat();
        mat2.setColor("Color", ColorRGBA.Red);
        red.setMaterial(mat2);
        return red;
    }

    public Geometry putShape(Node node, Mesh shape, ColorRGBA color) {
        Geometry g = new Geometry("shape", shape);
        Material mat = getColoredMat(color).clone();
        mat.getAdditionalRenderState().setWireframe(true);
        g.setMaterial(mat);
        node.attachChild(g);
        return g;
    }

    public void putArrow(Vector3f pos, Vector3f dir, ColorRGBA color) {
        Arrow arrow = new Arrow(dir);
        arrow.setLineWidth(4); // make arrow thicker

        putShape(gizmo, arrow, color).setLocalTranslation(pos);
        rootNode.attachChild(gizmo);
        gizmo.scale(1);
    }

    public void createGizmo() {
        putArrow(Vector3f.ZERO, Vector3f.UNIT_X, ColorRGBA.Red);
        putArrow(Vector3f.ZERO, Vector3f.UNIT_Y, ColorRGBA.Green);
        putArrow(Vector3f.ZERO, Vector3f.UNIT_Z, ColorRGBA.Blue);
    }

    public void createGrid(int gw, int gh) {
        Material mat = getColoredMat(ColorRGBA.DarkGray).clone();

        Grid grid = new Grid(gw - 1, gh - 1, 1);
        gridGeo = new Geometry("Grid", grid);
        gridGeo.setMaterial(mat);
        gridGeo.setLocalTranslation(-gw / 2, 0.1f, -gh / 2);
        rootNode.attachChild(gridGeo);
    }

    public void createLight() {
        setupLight(ColorRGBA.White);
    }

    public void setupLight() {
        setupLight(ColorRGBA.White);
    }

    public void setupLight(ColorRGBA color) {
        // light . sun
        /**
         * A white, spot light source.
         */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(color);
        rootNode.addLight(ambient);

        /**
         * A white, directional light source
         */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(color);
        rootNode.addLight(sun);
    }

    public void createSkyBox() {
        Spatial sky = SkyFactory.createSky(assetManager, "Scenes/Beach/FullskiesSunset0068.dds", false);
        sky.setLocalScale(350);

        rootNode.attachChild(sky);
    }

    public void createTestWorld() {
        createGizmo();
        createGrid(10, 10);
        createLight();
    }

    public Material getMatGround() {
        if (matGround == null) {
            matGround = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
            Texture grass = assetManager.loadTexture("Textures/Terrain/splat/grass.jpg");
            grass.setWrap(Texture.WrapMode.Repeat);
            matGround.setTexture("DiffuseMap", grass);
        }
        return matGround;
    }

    public Material getLightMat() {
        return lightMat;
    }

    public Material getUnshadedMat() {
        if (unshadedMat == null) {
            unshadedMat = new Material(assetManager,
                    "Common/MatDefs/Misc/Unshaded.j3md");
        }
        return unshadedMat;
    }
}
