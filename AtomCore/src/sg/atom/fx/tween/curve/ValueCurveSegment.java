/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.tween.curve;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Spline;
import java.io.IOException;
import java.util.ArrayList;
import sg.atom.core.timing.TimeValueIndicator;

/**
 * A single curve segment.
 * @author CuongNguyen
 */
public class ValueCurveSegment<V> implements Savable{

    protected Spline.SplineType type;
    protected String stype;
    protected TimeValueIndicator<V> start, end;
    protected ArrayList<TimeValueIndicator<V>> controls;

    @Override
    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
