/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage;

import sg.atom.stage.sound.SoundClip;
import com.google.common.collect.HashBiMap;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioSource;
import com.jme3.audio.AudioRenderer;
import com.jme3.audio.LowPassFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import sg.atom.core.AbstractManager;
import sg.atom.core.AtomMain;
import sg.atom.world.enviroment.AtomEnviroment;
import sg.atom.world.enviroment.EnviromentContributer;

/**
 * A better SoundManager which envolve in Cycle. which:
 *
 * <ul> <li>have its own Repository for Sound. Different Cache/Pools mechanism
 * beside of AssetManager.</li>
 *
 * <li>have global values and mapping to invidual sound. Categorize manage sound
 * with tags.</li>
 *
 * <li>have a way to config accoring to the system</li>
 *
 * <li>intergrated deeply with the EnviromentManager. </li> </ul>
 *
 * @author atomix
 */
public class SoundManager extends AbstractManager implements EnviromentContributer {

    protected static final Logger logger = Logger.getLogger(SoundManager.class.getName());
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
    Map<SoundClip, AudioNode> soundMap;
    private final AtomMain app;

    public SoundManager(AtomMain app) {
        this.app = app;
        this.stageManager = app.getStageManager();
        this.audioRenderer = app.getAudioRenderer();
        this.assetManager = app.getAssetManager();

        soundMap = HashBiMap.create();
    }

    public void initSound() {
    }

    public void setupAudios() {
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

    public void setupAudioPresets() {
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

    @Override
    public void init() {
    }

    @Override
    public void load() {
    }

    @Override
    public void config(Properties props) {
    }

    @Override
    public void update(float tpf) {
    }

    @Override
    public void finish() {
    }

    @Override
    public LifeCyclePhase getCurrentPhase() {
        return null;
    }

    @Override
    public float getProgressPercent(LifeCyclePhase aPhrase) {
        return 0;
    }

    // loads all sound effects which will be needed for that level
    private void loadSoundEffects(SoundClip[] sounds) {

        for (SoundClip s : sounds) {
            AudioNode soundNode = new AudioNode(assetManager, s.getPath());
            soundMap.put(s, soundNode);
        }
    }

    public SoundClip loadMusic(String path) {
        SoundClip s = new SoundClip(path, true);
        addSoundClip(s);
        return s;
    }
    // load all music which will be streamed

    public void loadMusics(SoundClip[] music) {

        for (SoundClip s : music) {
            if (s != null) {
                addSoundClip(s);
            }
        }
    }

    void addSoundClip(SoundClip s) {
        AudioNode musicNode = new AudioNode(assetManager, s.getPath(), true);
        musicNode.setLooping(false);
        musicNode.setPositional(false);
        musicNode.setDirectional(false);

        soundMap.put(s, musicNode);
    }

    public void unloadMusics(SoundClip[] music) {
        for (SoundClip s : music) {
            soundMap.remove(s);
        }
    }

    public void unloadAllMusics() {
        ArrayList<SoundClip> musicList = new ArrayList<SoundClip>();

        for (SoundClip s : soundMap.keySet()) {
            if (soundMap.get(s).isLooping()) {
                musicList.add(s);
            }
        }

        for (SoundClip soundClip : musicList) {
            soundMap.get(soundClip).stop();
            soundMap.remove(soundClip);
        }
    }

    public void play(SoundClip sound) {
        this.play(sound, 1f);
    }

    public void play(SoundClip sound, float startVolume) {
        AudioNode toPlay = soundMap.get(sound);

        if (toPlay != null) {
            if (sound.isMusic()) {
                /*
                 if (app.getUserSettings().isMusicMuted()) {
                 return;
                 }
                 */
                toPlay.setVolume(startVolume);
                toPlay.play();
            } else {
                /*
                 if (app.getUserSettings().isSoundFXMuted()) {
                 return;
                 }
                 */
                toPlay.setVolume(startVolume);
                toPlay.playInstance();
            }
        }
    }
    // pause the music

    public void pause(SoundClip sound) {

        AudioNode toPause = soundMap.get(sound);

        if (toPause != null) {
            audioRenderer.pauseSource(toPause);
        }
    }

    // if paused it will play, if playing it will be paused
    public void togglePlayPause(SoundClip sound) {
        AudioNode toToggle = soundMap.get(sound);

        if (toToggle != null) {
            if (toToggle.getStatus() == AudioSource.Status.Paused
                    || toToggle.getStatus() == AudioSource.Status.Stopped) {
                play(sound);
            } else {
                pause(sound);
            }
        }
    }

    // tries to stop a sound, will probably only work for streaming music though
    public void stop(SoundClip sound) {
        AudioNode toStop = soundMap.get(sound);

        toStop.stop();
    }

    public void cleanup() {
        unloadAllMusics();
        soundMap.clear();
    }

    //ASSET ANALYSING ----------------------------------------------------------
    // From Game Engine Gems 2.
    /**
     *
     */
    public void analyzeSoundEvents() {
    }

    public void constructSoundMap() {
    }

    public void contributeEnviroment(AtomEnviroment enviroment) {
    }
}
