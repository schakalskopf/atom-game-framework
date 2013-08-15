/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioRenderer;
import com.jme3.audio.LowPassFilter;
import java.util.HashMap;
import sg.atom.stage.WorldManager;

/**
 *
 * @author hungcuong
 */
public class SoundManager {
    
    AudioRenderer audioRenderer;
    AssetManager assetManager;
    WorldManager worldManager;
    StageManager stageManager;
    AudioNode waves;
    HashMap<String, LowPassFilter> filters = new HashMap<String, LowPassFilter>(10);
    LowPassFilter underWaterAudioFilter = new LowPassFilter(0.5f, 0.1f);
    LowPassFilter underWaterReverbFilter = new LowPassFilter(0.5f, 0.1f);
    LowPassFilter aboveWaterAudioFilter = new LowPassFilter(1, 1);
    boolean uw;
    
    public SoundManager(AtomMain app) {
        this.stageManager = app.getStageManager();
        this.audioRenderer = app.getAudioRenderer();
        this.assetManager = app.getAssetManager();
    }
    
    public void setupAudios(){
        
    }
    void setupAudio() {
        waves = new AudioNode(audioRenderer, assetManager, "Sound/Environment/Ocean Waves.ogg");
        waves.setLooping(true);
        waves.setReverbEnabled(true);
        if (uw) {
            waves.setDryFilter(new LowPassFilter(0.5f, 0.1f));
        } else {
            waves.setDryFilter(aboveWaterAudioFilter);
        }
        audioRenderer.playSource(waves);
    }
    public void setupAudioPresets(){
        
    }
    public void setEnviroment(boolean underWater) {
        if (underWater && !uw) {
            
            waves.setDryFilter(new LowPassFilter(0.5f, 0.1f));
            uw = true;
        }
        if (!underWater && uw) {
            uw = false;
            //waves.setReverbEnabled(false);
            waves.setDryFilter(new LowPassFilter(1, 1f));
            //waves.setDryFilter(new LowPassFilter(1,1f));

        }
    }

    public HashMap<String, LowPassFilter> getFilters() {
        return filters;
    }
    
    
}
