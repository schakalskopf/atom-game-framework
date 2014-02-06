/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.curve;

import com.google.common.base.Function;
import com.jme3.export.Savable;
import com.jme3.math.Spline;
import com.jme3.math.Spline.SplineType;
import java.util.ArrayList;
import java.util.List;
import sg.atom.core.timing.SimpleTimeValue;
import sg.atom.core.timing.TimeValueIndicator;
import sg.atom.core.timing.TimeValueProvider;
import sg.atom.fx.functional.InterpolatorFunction;

/**
 * ValueCurve is a kind of Interpolation, normally refered as ResponseCurve in
 * gamedev context.
 *
 * @author atomix
 */
public abstract class ValueCurve<V> extends InterpolatorFunction<TimeValueIndicator<V>> implements TimeValueProvider<V>, Savable {
// Use if the whole curve is one type

    protected Spline.SplineType type;
    protected String stype;
// Use if different type each indexed segments
    protected List<ValueCurveSegment<V>> segments;

    public ValueCurve(SplineType type, List<TimeValueIndicator<V>> source) {
        super(source);
        this.type = type;
        this.segments = new ArrayList<ValueCurveSegment<V>>();
        calculateTimes();
    }

    public ValueCurve(SplineType type, List<ValueCurveSegment<V>> segments, List<TimeValueIndicator<V>> source) {
        super(source);
        this.type = type;
        this.segments = segments;

    }
    
    Function getValueFunction = new Function<TimeValueIndicator<V>, V>() {
        @Override
        public V apply(TimeValueIndicator<V> key) {
            return key.getValue();
        }
    };

    public SplineType getType() {
        return type;
    }

    public String getStype() {
        return stype;
    }

    public void setType(SplineType type) {
        this.type = type;
    }

    @Override
    public V getValueAtTime(float t) {
        return this.apply(t).getValue();
    }

    @Override
    public TimeValueIndicator<V> interpolate(TimeValueIndicator<V> a, TimeValueIndicator<V> b) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return new SimpleTimeValue(0, a);
    }

    public void calculateTimes() {

        startTime = source.get(0).getTime();
        if (source.size() > 1) {
            endTime = source.get(source.size() - 1).getTime();
        } else {
            endTime = startTime;
        }
        duration = startTime - endTime;

    }
    public float startTime;
    public float endTime;
    public float duration;

    public float getStartTime() {
        return startTime;
    }

    public float getDuration() {
        return duration;
    }

    public float getEndTime() {
        return startTime + duration;
    }
}
