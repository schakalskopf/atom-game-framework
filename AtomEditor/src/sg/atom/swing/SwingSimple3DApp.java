package sg.atom.swing;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.effect.ParticleEmitter;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import com.jme3.texture.Texture;
import java.awt.Canvas;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.atom.fx.particle.ParticleFactory;
import sg.atom.world.WorldTestHelper;

/**
 * A simple swing application in which is simplier than AtomMain.
 *
 * <p>It's have less features than AtomMain use for testing and editing purpose
 * only. It well-crafted to be embeded in Swing enviroment, comparable to
 * SceneApplication embed in JME3' SDK under Netbean platform. It just depend in
 * generic helpers of Atom framework excepts GameState, StageManager and
 * GameGUIManager. To enable those 3, for ex: to embed your run-time game in
 * Swing enviroment but not use AtomMain directly, you should use an proxy
 * AtomMain, return via static method getAtomMain(). The mechanism is similar to
 * how SceneApplication and FakeApplication interact. Compare to
 * SceneApplication, this class is kind of the inbetween, it's a
 * SimpleApplication which use predefined render tasks and appstates, but not
 * related to Netbean.For numberous of different purposes and goals, I've
 * decided to made this one instead of using SceneApplication directly!</p>
 *
 * <p>It also support Guava, Guice and all other extensions that AtomMain
 * support.</p>
 *
 * <p></p>
 *
 * @author cuong.nguyenmanh2
 */
public class SwingSimple3DApp extends SimpleApplication {

    private ParticleEmitter currentParticle;

    public static void main(String[] args) {
        SwingSimple3DApp app = new SwingSimple3DApp();
        app.setShowSettings(false);
        app.start();

        Logger.getLogger("com.jme3").setLevel(Level.WARNING);
    }

    public Canvas createAndStartCanvas(int width, int height) {
        AppSettings settings = new AppSettings(true);
        settings.setWidth(width);
        settings.setHeight(height);

        setPauseOnLostFocus(true);
        setSettings(settings);
        createCanvas();
        startCanvas(true);

        JmeCanvasContext context = (JmeCanvasContext) getContext();
        Canvas canvas = context.getCanvas();
        canvas.setSize(settings.getWidth(), settings.getHeight());

        return canvas;
    }

    protected void createMark(Vector3f loc) {
        Sphere sphere = new Sphere(8, 8, 0.2f);
        Geometry mark = new Geometry("BOOM!", sphere);
        Material mark_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mark_mat.setColor("Color", ColorRGBA.Red);
        mark.setMaterial(mark_mat);
        mark.setLocalTranslation(loc.clone());
        rootNode.attachChild(mark);
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(40f);

        //Create tesint enviroment
        WorldTestHelper worldHelper = new WorldTestHelper(rootNode, assetManager);
        worldHelper.createLight();
        worldHelper.createGrid(40, 40);
        worldHelper.createFlatGround(80);
        worldHelper.createSkyBox();
        createParticle();
        initInput();
    }

    public void initInput() {
        flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);

        //inputManager.addListener(actionListener, "changeTexture");
        cam.setLocation(new Vector3f(-10, 10, -10));
        cam.lookAt(Vector3f.ZERO.clone(), Vector3f.UNIT_Y.clone());

        viewPort.setBackgroundColor(ColorRGBA.Blue);
    }

    public void createParticle() {
        ParticleFactory pf = new ParticleFactory(assetManager);
        currentParticle = pf.createFlame();
        rootNode.attachChild(currentParticle);
    }

    public ParticleEmitter getCurrentParticle() {
        return currentParticle;
    }

    public Texture changeTexture(File file) {
        try {

            //String filePath = file.getParent().replaceAll("\\", "/");
            //System.out.println("" + filePath + "/");
            String filePath = file.getParent();
            assetManager.registerLocator(filePath, FileLocator.class);
            Texture newTex = assetManager.loadTexture(file.getName());

            currentParticle.getMaterial().setTexture("Texture", newTex);
            return newTex;
        } catch (Exception e) {
            return null;

        }
    }
}