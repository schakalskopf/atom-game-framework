/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.timeline;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import java.io.IOException;
import sg.atom.core.timing.TimeValueIndicator;

/**
 * Different from JME3's Cinematic <b>Keyframe</b> term, which is a <i>list of
 * Event with time indicator</i>; <b>AtomKeyFrame</b> is just a <i>warper of
 * Value with time indicator</i>.
 *
 * <p>For that reason, AtomKeyFrame and the FX framework its self is much more
 * generic. It can be embeded in JME3 cinematic system seamlessly.</p>
 *
 * @see com.jme3.cinematic.KeyFrame
 * @author atomix
 */
public class AtomKeyframe<V> implements Savable, TimeValueIndicator<V>, Comparable<TimeValueIndicator<V>> {

    private V value;
    private float time;
    private int id;
    // Support tweening
    private TimeValueIndicator<V> inKey, outKey;

    public AtomKeyframe(V value, float time, int id) {
        this.value = value;
        this.time = time;
        this.id = id;
    }

    public AtomKeyframe(V value, float time) {
        this(value, time, -1);
    }

    public AtomKeyframe(V value) {
        this(value, 0, -1);
    }

    @Override
    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public TimeValueIndicator<V> getInKey() {
        return inKey;
    }

    public TimeValueIndicator<V> getOutKey() {
        return outKey;
    }

    public void setInKey(TimeValueIndicator<V> inKey) {
        this.inKey = inKey;
    }

    public void setOutKey(TimeValueIndicator<V> outKey) {
        this.outKey = outKey;
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public AtomKeyframe<V> getInstance(float time, V value) {
        return new AtomKeyframe<V>(value, time);
    }

    @Override
    public int compareTo(TimeValueIndicator<V> otherKey) {
        if (time - otherKey.getTime() < 0) {
            return -1;
        } else if (time - otherKey.getTime() > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
