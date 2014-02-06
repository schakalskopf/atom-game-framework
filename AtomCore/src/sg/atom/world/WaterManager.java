/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world;

import sg.atom.stage.SoundManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Plane;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture2D;
import com.jme3.water.SimpleWaterProcessor;
import com.jme3.water.WaterFilter;
import sg.atom.stage.StageManager;
import sg.atom.stage.WorldManager;

/**
 *
 * @author atomix
 */
public class WaterManager implements ActionListener {

    WorldManager worldManager;
    AssetManager assetManager;
    private final StageManager stageManager;
    // More about the world
    private Node reflectionNode;
    private final Node rootNode;
    private final ViewPort viewPort;
    // WATER -----------------------------------------------------------------------
    //This part is to emulate tides, slightly varrying the height of the water plane
    private float time = 0.0f;
    private float waterHeight = 0.0f;
    private float initialWaterHeight = 0.8f;
    private boolean uw = false;
    SimpleWaterProcessor waterProcessor;
    private WaterFilter waterFilter;
    private final SoundManager soundManager;

    public WaterManager(StageManager stageManager, WorldManager worldManager) {
        this.worldManager = worldManager;
        this.assetManager = worldManager.getAssetManager();

        this.rootNode = worldManager.getRootNode();
        this.viewPort = stageManager.getCurrentActiveViewPort();
        this.stageManager = stageManager;
        this.soundManager = worldManager.getSoundManager();
    }
    Texture2D foam1, foam2, foam3;

    public void loadWaterAsset() {
        foam1 = (Texture2D) assetManager.loadTexture("Common/MatDefs/Water/Textures/foam.jpg");
        foam2 = (Texture2D) assetManager.loadTexture("Common/MatDefs/Water/Textures/foam2.jpg");
        foam3 = (Texture2D) assetManager.loadTexture("Common/MatDefs/Water/Textures/foam3.jpg");
    }

    public void onAction(String name, boolean isPressed, float tpf) {

        if (isPressed) {
            if (name.equals("foam1")) {
                waterFilter.setFoamTexture(foam1);
            }
            if (name.equals("foam2")) {
                waterFilter.setFoamTexture(foam2);
            }
            if (name.equals("foam3")) {
                waterFilter.setFoamTexture(foam3);
            }
        }
    }

    public void initWater() {
        waterProcessor = new SimpleWaterProcessor(assetManager);
    }

    public void configWater(Node reflectionNode) {
        this.reflectionNode = reflectionNode;
        waterProcessor.setReflectionScene(reflectionNode);
        //Vector3f lightDir = getLight().getDirection();
        Vector3f lightDir = new Vector3f(-0.37352666f, -0.50444174f, -0.7784704f);
        Vector3f lightPos = lightDir.multLocal(-400);
        waterProcessor.setLightPosition(lightPos);
        waterProcessor.setRefractionClippingOffset(1.0f);

        //setting the water plane
        Vector3f waterLocation = new Vector3f(0, 0, 0);
        waterProcessor.setPlane(new Plane(Vector3f.UNIT_Y, waterLocation.dot(Vector3f.UNIT_Y)));
        waterProcessor.setWaterColor(ColorRGBA.Brown);
        //waterProcessor.setDebug(true);
        //lower render size for higher performance
        waterProcessor.setRenderSize(128, 128);
        //raise depth to see through water
        waterProcessor.setWaterDepth(20);
        //lower the distortion scale if the waves appear too strong
        waterProcessor.setDistortionScale(0.1f);
        //lower the speed of the waves if they are too fast
//        waterProcessor.setWaveSpeed(0.01f);
    }

    public void attachWater() {

        Quad quad = new Quad(50, 50);

        //the texture coordinates define the general size of the waves
        quad.scaleTextureCoordinates(new Vector2f(6f, 6f));

        Geometry water = new Geometry("water", quad);
        //water.setShadowMode(ShadowMode.Receive);
        water.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));
        water.setMaterial(waterProcessor.getMaterial());

        water.setLocalScale(10f);
        water.setLocalTranslation(-150, 0.2f, 150);
        rootNode.attachChild(water);

        viewPort.addProcessor(waterProcessor);
    }

    void initWaterPost() {
        Vector3f lightDir = new Vector3f(-0.37352666f, -0.50444174f, -0.7784704f);
        waterFilter = new WaterFilter(rootNode, lightDir);

        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);

        fpp.addFilter(waterFilter);
//        

        //   fpp.addFilter(new TranslucentBucketFilter());
        //       

        // fpp.setNumSamples(4);


        waterFilter.setWaveScale(0.003f);
        waterFilter.setMaxAmplitude(0.2f);
        waterFilter.setFoamExistence(new Vector3f(1f, 0.4f, 0.5f));
        waterFilter.setFoamTexture(foam2);
        //water.setNormalScale(0.5f);
        waterFilter.setFoamIntensity(0.4f);


        //water.setRefractionConstant(0.25f);
        waterFilter.setRefractionStrength(0.2f);
        waterFilter.setFoamHardness(0.1f);

        waterFilter.setWaterHeight(initialWaterHeight);
        Camera cam = stageManager.getCurrentActiveCamera();
        uw = cam.getLocation().y < waterHeight;


        //  
        viewPort.addProcessor(fpp);
    }

    void updateWater(float tpf) {
        //super.simpleUpdate(tpf);
        //     box.updateGeometricState();
        time += tpf;
        waterHeight = (float) Math.cos(((time * 0.6f) % FastMath.TWO_PI)) * 1.5f;
        waterFilter.setWaterHeight(initialWaterHeight + waterHeight);
        if (waterFilter.isUnderWater() && !uw) {

            soundManager.setEnviroment(true);

            uw = true;
        }
        if (!waterFilter.isUnderWater() && uw) {
            uw = false;
            soundManager.setEnviroment(false);

        }

    }
}
