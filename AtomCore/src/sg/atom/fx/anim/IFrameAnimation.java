/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.anim;

import java.util.ArrayList;

/**
 * Use this for animation which have keyframe basis. This is very common in
 * animation work, one of those is sprite animation or skeleton animation, in
 * which the animtion state is defined by a single frame with a state, a pose,
 * an extreme called key.
 *
 * @author CuongNguyen
 */
public interface IFrameAnimation<F extends IAnimationState> {

    public ArrayList<Integer> getFrameIndexes();

    public ArrayList<F> getFrames();

    public String getName();

    public F getFrameAt(float time);
}
