/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.anim;

import java.util.ArrayList;

/**
 * A Timeline is an ordered list of keyframe.
 * @author CuongNguyen
 * @param <V> 
 */
public abstract class AtomValueTrack<V> {

    public static enum Status {

        Started, Stop, Pause
    }

    public static enum LoopType {

        Once, Circle, Pingpong
    }
    Status status;
    LoopType loopType;
    
    
    ArrayList<AtomKeyframe<V>> values = new ArrayList<AtomKeyframe<V>>();
    float duration;

    public void addKeyframe(AtomKeyframe<V> kf, float time) {
        if (time > duration) {
            duration = time;
            // increase duration if need
        }
        kf.setTime(time);
        insertKeyframeIntoTimeline(kf);
    }

    private void insertKeyframeIntoTimeline(AtomKeyframe<V> kf) {
        // find the kf next to it

        // if null
        values.add(kf);
    }

    public void enQueueKeyframe(AtomKeyframe<V> kf, float incTime) {
        duration += incTime;
        kf.setTime(duration);
        insertKeyframeIntoTimeline(kf);
    }

    public AtomKeyframe<V> getPreviousKf(float time) {
        if (values.isEmpty()) {
            return null;
        } else {
            for (int i = 0; i < values.size(); i++) {

                if (values.get(i).getTime() < time) {
                    if (values.get(i + 1) != null) {
                        if (values.get(i + 1).getTime() > time) {
                            return values.get(i);
                        }
                    } else {
                        return values.get(i);
                    }

                }
            }
        }
        return null;
    }

    public AtomKeyframe<V> getNextKf(float time) {
        return null;
    }

    public AtomKeyframe<V> getFirstKf() {
        return values.get(0);
    }

    public AtomKeyframe<V> getLastKf() {
        return values.get(values.size() - 1);
    }

    public abstract V getValueAtTime(float time);
}
