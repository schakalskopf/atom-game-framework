/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.sound;

import com.jme3.animation.AudioTrack;

/**
 * Facade of AudioNode for Atom system. Integrade in Stage, Effect, Cine and
 * Enviroment.
 *
 * @author CuongNguyen
 */
public class SoundClip {

    String path;
    boolean music;
    float initialVolume;
    
    public SoundClip(String filename, boolean isMusic) {
        path = filename;
        music = isMusic;
    }

    public boolean isMusic() {
        return music;
    }

    public void setMusic(boolean music) {
        this.music = music;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public AudioTrack toAudioTrack() {
        return null;

    }
}
