/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx;

import com.jme3.asset.AssetManager;
import sg.atom.stage.StageManager;
import sg.atom.fx.particle.ParticleFactory;

/**
 *
 * @author hungcuong
 */
public class GameEffectManager {

    AssetManager assetManager;
    ParticleFactory defaultFactory;

    public GameEffectManager(StageManager stageManager) {
        this.assetManager = stageManager.getApp().getAssetManager();
        this.defaultFactory = new ParticleFactory(assetManager);
    }

    public ParticleFactory getDefaultFactory() {
        return defaultFactory;
    }
}
