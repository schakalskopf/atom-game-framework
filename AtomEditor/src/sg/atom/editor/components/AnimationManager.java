/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.editor.components;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;

/**
 *
 * @author hungcuong
 */
public class AnimationManager
        implements AnimEventListener {

    /** Use this listener to trigger something after an animation is done. */
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        if (animName.equals("Walk")) {
            /** After "walk", reset to "stand". */
            channel.setAnim("Kick", 0.50f);
            channel.setLoopMode(LoopMode.DontLoop);
            channel.setSpeed(1f);
        }
    }

    /** Use this listener to trigger something between two animations. */
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
        // unused
    }
}
