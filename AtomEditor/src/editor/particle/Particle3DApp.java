package editor.particle;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.effect.ParticleEmitter;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.util.SkyFactory;
import java.awt.Canvas;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.atom.fx.particle.ParticleFactory;

public class Particle3DApp extends SimpleApplication {

    public static void main(String[] args) {
        Particle3DApp app = new Particle3DApp();
        app.setShowSettings(false);
        app.start();

        Logger.getLogger("com.jme3").setLevel(Level.WARNING);
    }
    private Geometry ground;
    private Material matGroundL;
    private ParticleEmitter currentParticle;

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

    void createLight() {
        /**
         * Must add a light to make the lit object visible!
         */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-1, -4, -1).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        /**
         * A white ambient light source.
         */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);
        rootNode.addLight(sun);
        /**
         * A white, spot light source.
         */
        PointLight lamp = new PointLight();
        lamp.setPosition(new Vector3f(0, 20, 20));
        lamp.setColor(ColorRGBA.White);
        rootNode.addLight(lamp);

    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(40f);
        createLight();
        createGrid(40, 40);
        createParticle();
        initInput();
    }

    public Material getUnshadeMat() {
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        return mat;
    }

    public Material getLightMat() {
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        //Texture window = assetManager.loadTexture("Textures/window.jpg");
        //window.setWrap(Texture.WrapMode.Repeat);
        //mat.setTexture("DiffuseMap", window);
        //mat.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Off);
        //matLighting.getAdditionalRenderState().setWireframe(true);
        return mat;
    }

    public void createGrid(int gw, int gh) {
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.DarkGray);

        Grid grid = new Grid(gw - 1, gh - 1, 1);
        Geometry gridGeo = new Geometry("Grid", grid);
        gridGeo.setMaterial(mat);
        gridGeo.setLocalTranslation(-gw / 2, -0.5f, -gh / 2);
        rootNode.attachChild(gridGeo);
    }

    public void initInput() {
        flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);

        //inputManager.addListener(actionListener, "changeTexture");
        cam.setLocation(new Vector3f(-10, 10, -10));
        cam.lookAt(Vector3f.ZERO.clone(), Vector3f.UNIT_Y.clone());

        viewPort.setBackgroundColor(ColorRGBA.Blue);
    }

    public void createTerrain() {
        Box b = new Box(new Vector3f(0, -1, 550), 1000, 0.01f, 1000);
        b.scaleTextureCoordinates(new Vector2f(40, 40));
        ground = new Geometry("soil", b);
        matGroundL = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        Texture grass = assetManager.loadTexture("Textures/Terrain/splat/grass.jpg");
        grass.setWrap(WrapMode.Repeat);
        matGroundL.setTexture("DiffuseMap", grass);

        ground.setMaterial(matGroundL);
        ground.setShadowMode(ShadowMode.Receive);
        rootNode.attachChild(ground);

        Spatial sky = SkyFactory.createSky(assetManager, "Scenes/Beach/FullskiesSunset0068.dds", false);
        sky.setLocalScale(350);

        rootNode.attachChild(sky);
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