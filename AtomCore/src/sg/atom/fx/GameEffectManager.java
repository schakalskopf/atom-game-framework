/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx;

import com.google.common.collect.Multimap;
import com.jme3.asset.AssetManager;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.Properties;
import sg.atom.core.AbstractManager;
import sg.atom.core.timing.TimeProvider;
import sg.atom.fx.particle.ParticleFactory;
import sg.atom.stage.StageManager;

/**
 * GameEffectManager. Manager and factory for all particles effects.
 *
 * FIXME: GameEffectManager Should be an AppState!
 *
 * @author atomix
 */
public class GameEffectManager extends AbstractManager implements TimeProvider{

    protected AssetManager assetManager;
    protected StageManager stageManager;
    //Manage effects which associate directly to the spatial and normal indexing
    protected Multimap<Spatial, AtomEffect> spatialEffectMap;
    protected ArrayList<AtomEffect> effectList;
    
    //Factory
    protected FilterPostProcessor fpp;
    protected ParticleFactory defaultFactory;

    public GameEffectManager(StageManager stageManager) {
        this.stageManager = stageManager;
        this.assetManager = stageManager.getApp().getAssetManager();
        this.defaultFactory = new ParticleFactory(assetManager);
    }

    public void initEffectManager() {
        System.out.println("Init filter");
        fpp = new FilterPostProcessor(assetManager);
    }

    public void update(float tpf) {
        // Empty implement
    }

    public void disableAllEffects() {
    }

    public void enableAllEffects() {
    }

    /* Getter and setter */
    public ViewPort getViewport() {
        return stageManager.getApp().getViewPort();
    }

    public ParticleFactory getDefaultFactory() {
        return defaultFactory;
    }

    public <T extends AtomEffectFactory> T getFactory(Class<T> clazz) {
        return null;

    }

    @Override
    public float getTimeInSeconds() {
        return stageManager.getTimeInSeconds();
    }

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
