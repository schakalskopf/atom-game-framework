/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.curve;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.math.Spline.SplineType;
import java.io.IOException;
import java.util.List;
import sg.atom.core.timing.SimpleTimeValue;
import sg.atom.core.timing.TimeValueIndicator;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class FloatValueCurve extends ValueCurve<Float> {

    public FloatValueCurve(SplineType type, List<TimeValueIndicator<Float>> source) {
        super(type, source);
    }



    @Override
    public TimeValueIndicator<Float> apply(Float currentTime) {
        float curveValue = 0;
        calculateTimes();
        if (source.size() == 1) {
            //constant value
            curveValue = source.get(0).getValue();
        } else {

            float timeOffset = currentTime - startTime;
            List<Float> floatValues = Lists.transform(source, getValueFunction);
            if (type == SplineType.Bezier) {
                //curveValue = interpolate(timeOffset / duration, Lists.toArray(floatValues, Float.class));
                curveValue = 0;
            }
        }
        return new SimpleTimeValue(currentTime, curveValue);
    }
// SETTER & GETTER

    @Override
    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
        
    
    /**
     * Bezier Interpolate from 0-1 between two point
     *
     * @param p0
     * @param p1
     * @param t
     * @return
     */
    public static  float interpolateBezier(float t, float p0, float p1) {
        if (t < 0) {
            return p0;
        }
        if (t > 1) {
            return p1;
        }
        return (1 - t) * p0 + t * p1;
    }

    /**
     *
     * @param p0
     * @param p1
     * @param p2
     * @param p3
     * @param t
     * @return
     */
    public static float interpolateCubic(float t, float p0, float p1, float p2, float p3) {
        if (t < 0) {
            return p0;
        }
        if (t > 1) {
            return p3;
        }
        float a = (1 - t) * (1 - t) * (1 - t) * p0;
        float b = 3 * (1 - t) * (1 - t) * t * p1;
        float c = 3 * (1 - t) * t * t * p2;
        float d = t * t * t * p3;
        return a + b + c + d;
    }

    /**
     *
     * @param p0
     * @param p1
     * @param p2
     * @param t
     * @return
     */
    public static  float interpolateQuadratic(float t, float p0, float p1, float p2) {
        if (t < 0) {
            return p0;
        }
        if (t > 1) {
            return p2;
        }
        float a = (1 - t) * (1 - t) * p0;
        float b = 2 * (1 - t) * t * p1;
        float c = t * t * p2;
        return a + b + c;
    }
}
