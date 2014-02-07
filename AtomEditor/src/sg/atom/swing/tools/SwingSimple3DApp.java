package sg.atom.swing.tools;

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
        worldHelper.createFlatGround();
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