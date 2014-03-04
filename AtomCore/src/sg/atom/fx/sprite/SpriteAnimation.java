/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.sprite;

import java.util.ArrayList;
import sg.atom.fx.anim.AtomAnimation;
import sg.atom.fx.anim.IFrameAnimation;

/**
 * A Sprite Animation is a kind of Animation which replace the current Sprite
 * with the next Sprite. Underlying, a sprite animation is a kind of Frame based
 * animation.
 *
 * @author CuongNguyen
 */
public class SpriteAnimation extends AtomAnimation implements IFrameAnimation<SpriteImage> {

    public ArrayList<Integer> getFrameIndexes() {
        return null;
    }

    public ArrayList<SpriteImage> getFrames() {
        return null;
    }

    public String getName() {
        return "";
    }

    public SpriteImage getFrameAt(float time) {
        return null;
    }
}
