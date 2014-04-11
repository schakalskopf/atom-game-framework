/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.anim;

import rx.Observer;
import sg.atom.utils.datastructure.stream.AtomStreaming;

/**
 * AtomAnimation [Extenstion of JME3's Animation] is an abstract way to declare
 * a "non static" motion. An Animation happen in a particular "spatial" object,
 * in a duration of time with a motion type (or not).
 *
 * <p>This implementation contract that streaming must be the nature of
 * Animation playing (its mean sequence of data, yes!). That why it use Buffer
 * to save/load animation and, and a very fast cicular buffer to play/replay
 * snapshot of animations in runtime.
 *
 * <p>Because of being a buffer by nature, AtomAnimation can be streamed to
 * other pipeline and also to OpenGL buffer in appropriate format to play vertex
 * or fragment animation in lower level.
 *
 * @author CuongNguyen
 */
public abstract class AtomAnimation implements AtomStreaming, Comparable<AtomAnimation>, Iterable<IAnimationState>, Observer<IAnimationState> {
}
