/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.sprite;

import java.util.ArrayList;
import java.util.Iterator;
import sg.atom.fx.anim.AtomAnimation;
import sg.atom.fx.anim.IAnimationState;
import sg.atom.fx.anim.IFrameAnimation;
import sg.atom.utils.datastructure.buffer.AtomBuffer;
import sg.atom.utils.datastructure.stream.StreamCursor;

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

    @Override
    public AtomBuffer getBuffer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AtomBuffer getSlice(StreamCursor cursor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int compareTo(AtomAnimation o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterator<IAnimationState> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onCompleted() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onError(Throwable e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onNext(IAnimationState t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
