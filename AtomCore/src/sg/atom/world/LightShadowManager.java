/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world;

import com.jme3.asset.AssetManager;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.shadow.PssmShadowRenderer;
import com.jme3.util.SkyFactory;
import java.util.Properties;
import sg.atom.core.AbstractManager;
import sg.atom.stage.StageManager;

/**
 * <p>Manager and Wrapper for useful functions for Lights.
 *
 * <p>FIXME: Ready for different light scheme like Deferred lights
 *
 * @author atomix
 */
public class LightShadowManager extends AbstractManager {

    WorldManager worldManager;
    AssetManager assetManager;
    StageManager stageManager;
    private Node rootNode;
    // The main lights of the screen
    DirectionalLight sun;
    DirectionalLight dl;
    PointLight light;

    public LightShadowManager(WorldManager worldManager, StageManager stageManager) {
        this.stageManager = stageManager;
        this.worldManager = worldManager;
        this.assetManager = worldManager.getAssetManager();

    }


    public void initLights() {
        /* Compute the light, sky and color 
         * base on the game scene and enviroment if need
         * Shadow type base on settings
         */
    }

    public void createLights() {
        // Create the light
        // Directional light
        Vector3f direction = new Vector3f(-0.1f, -0.7f, -1).normalizeLocal();
        dl = new DirectionalLight();
        dl.setDirection(direction);
        dl.setColor(new ColorRGBA(0.8f, 0.8f, 0.8f, 1.0f));

        // The sun
        sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-1, 0, -2).normalizeLocal());
        //sun.setColor(ColorRGBA.White);
        sun.setColor(new ColorRGBA(2, 2, 2, 1));

        // Some point light
        light = new PointLight();

//        /** A white ambient light source. */
//        AmbientLight ambient = new AmbientLight();
//        ambient.setColor(ColorRGBA.White);
//        rootNode.addLight(ambient);
////        /** A white, directional light source */
//        DirectionalLight sun = new DirectionalLight();
//        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalize());
//        sun.setColor(ColorRGBA.White);
//        rootNode.addLight(sun);
        /**
         * A white, spot light source.
         */
//        PointLight lamp = new PointLight();
//        lamp.setPosition(Vector3f.ZERO);
//        lamp.setColor(ColorRGBA.White);
//        rootNode.addLight(lamp);
    }

    public void attachLights() {
        this.rootNode = worldManager.getRootNode();
        rootNode.addLight(dl);
        rootNode.addLight(sun);
        //rootNode.addLight(light);
        createShadow();
        createSky();
    }

    private void createSky() {
        rootNode.attachChild(SkyFactory.createSky(assetManager, "Textures/Sky/Bright/BrightSky.dds", false));
    }

    void createShadow() {

        /**
         * Advanced shadows for uneven surfaces
         */
        PssmShadowRenderer pssm = new PssmShadowRenderer(assetManager, 1024, 3);
        pssm.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
        ViewPort viewPort = stageManager.getCurrentActiveViewPort();
        viewPort.addProcessor(pssm);
    }

    public DirectionalLight getFirstDirectionalLight(Node node) {
        for (Light l : node.getLocalLightList()) {
            if (l.getType() == Light.Type.Directional) {
                return (DirectionalLight) l;
            }

        }
        return null;
    }

    public void createAO() {
        /**
         * Ambient occlusion shadows
         */
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        SSAOFilter ssaoFilter = new SSAOFilter(12.94f, 43.92f, 0.33f, 0.61f);
        fpp.addFilter(ssaoFilter);
        stageManager.getApp().getViewPort().addProcessor(fpp);

    }
    //Cycle---------------------------------------------------------------------
    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void load() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void config(Properties props) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void finish() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LifeCyclePhase getCurrentPhase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float getProgressPercent(LifeCyclePhase aPhrase) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
