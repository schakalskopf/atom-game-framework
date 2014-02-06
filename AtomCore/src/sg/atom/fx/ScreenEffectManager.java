/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx;

import com.jme3.asset.AssetManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.post.Filter;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.DepthOfFieldFilter;
import com.jme3.renderer.ViewPort;
import java.util.ArrayList;
import sg.atom.stage.StageManager;
import sg.atom.fx.filter.CartoonEdgeProcessor;
import sg.atom.fx.filter.LUTCCFilter;

/**
 * Manager and Factory for all Screen effects
 * @author atomix
 */
public class ScreenEffectManager implements ActionListener {

    protected AssetManager assetManager;
    protected StageManager stageManager;
    
    protected FilterPostProcessor fpp;
    protected ViewPort viewPort;

    //FIXME: Replace this with a generic list!
    protected LUTCCFilter lut;
    protected BloomFilter bloom;
    
    //FIXME: Automatic Managing!!
    private boolean activeBloom;
    boolean activeLUT = true;
    ArrayList<Filter> filterList;
    
    public ScreenEffectManager(StageManager stageManager) {
        this.assetManager = stageManager.getApp().getAssetManager();
        this.stageManager = stageManager;
        this.viewPort = stageManager.getCurrentActiveViewPort();
    }

    public void setupFilter() {
        fpp = new FilterPostProcessor(assetManager);
        bloom = new BloomFilter(BloomFilter.GlowMode.SceneAndObjects);
        bloom.setBloomIntensity(0.5f);
        bloom.setExposureCutOff(0.2f);
        fpp.addFilter(bloom);
        activeBloom = true;

        //ColorCorrectionFilter ccf = new ColorCorrectionFilter();
        //fpp.addFilter(ccf); // add to FIlterPostProcessor
        //ccf.setMonochrome(2f);
        // ccf.setColor(1f);


        lut = new LUTCCFilter();
        fpp.addFilter(lut); // add to FIlterPostProcessor
        activeLUT = true;
        /*
         Vector3f lightDir = getLight().getDirection();
         LightScatteringFilter lsf = new LightScatteringFilter(lightDir.mult(-300));
         lsf.setLightDensity(1.0f);
         fpp.addFilter(lsf);
         * 
         */
        DepthOfFieldFilter dof = new DepthOfFieldFilter();
        dof.setFocusDistance(2);
        dof.setFocusRange(5);
        fpp.addFilter(dof);

        CartoonEdgeProcessor cartoonEdgeProcess = new CartoonEdgeProcessor();
        viewPort.addProcessor(cartoonEdgeProcess);
        //viewPort.addProcessor(fpp);
    }

    public void setupKeys(){
        //
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("toogleCCFilter")) {
            if (isPressed) {
                if (activeLUT) {
                    fpp.removeFilter(lut);
                    activeLUT = false;
                } else {
                    fpp.addFilter(lut);
                    activeLUT = true;
                }

                //
            }
        } else if (name.equals("toogleBloomFilter")) {
            if (isPressed) {
                if (activeBloom) {
                    fpp.removeFilter(bloom);
                    activeBloom = false;
                } else {
                    fpp.addFilter(bloom);
                    activeBloom = true;
                }

                //
            }
        }
    }
    /* Advanced stuffs */
    public Filter bakeFilter(String shaderPath){
        return null;
    }
    
}
