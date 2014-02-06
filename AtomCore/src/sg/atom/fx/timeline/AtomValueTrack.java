/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.timeline;

import com.jme3.cinematic.KeyFrame;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A ValueTrack is an ordered list of keyframes.
 *
 * @author CuongNguyen
 * @param <V>
 */
public abstract class AtomValueTrack<V> implements Savable, List<AtomKeyframe<V>> {

    //FIXME: Use Guave MultiMap instead!
    protected ArrayList<AtomKeyframe<V>> values = new ArrayList<AtomKeyframe<V>>();
    
    protected float duration;
    public AtomValueTrack(AtomKeyframe<V>... newValues){
        Collections.addAll(values,newValues);
        Collections.sort(values);
    }
    public void addKeyframe(AtomKeyframe<V> kf, float time) {
        if (time > duration) {
            duration = time;
            // increase duration if need
        }
        kf.setTime(time);
        insertKeyframeIntoTimeline(kf);
    }

    private void insertKeyframeIntoTimeline(AtomKeyframe<V> kf) {
        //FIXME: Should use Timsort or a merge sort to keep ordered list.

        // find the kf next to it

        // if null
        values.add(kf);
        Collections.sort(values);
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


    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule oc = ex.getCapsule(this);
        ArrayList list = new ArrayList();
        //list.addAll(values());
        oc.writeSavableArrayList(list, "keyFrames", null);
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        InputCapsule ic = im.getCapsule(this);
        ArrayList list = ic.readSavableArrayList("keyFrames", null);
        for (Iterator it = list.iterator(); it.hasNext();) {
            KeyFrame keyFrame = (KeyFrame) it.next();
            //addKeyFrameAtIndex(keyFrame.getIndex(), keyFrame);
        }
    }
}
