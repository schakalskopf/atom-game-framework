/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.game2d.graphics.fx.particle.values;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import sg.atom.utils.CommonParser;
import sg.atom.utils.math.MathUtils;
import sg.atom2d.game2d.graphics.fx.particle.ParticleEmitter;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RangedNumericValue extends EditableValue {

    public float lowMin;
    public float lowMax;

    public float newLowValue() {
        return lowMin + (lowMax - lowMin) * MathUtils.random();
    }

    public void setLow(float value) {
        lowMin = value;
        lowMax = value;
    }

    public void setLow(float min, float max) {
        lowMin = min;
        lowMax = max;
    }

    public float getLowMin() {
        return lowMin;
    }

    public void setLowMin(float lowMin) {
        this.lowMin = lowMin;
    }

    public float getLowMax() {
        return lowMax;
    }

    public void setLowMax(float lowMax) {
        this.lowMax = lowMax;
    }

    public void save(Writer output) throws IOException {
        super.save(output);
        if (!active) {
            return;
        }
        output.write("lowMin: " + lowMin + "\n");
        output.write("lowMax: " + lowMax + "\n");
    }

    public void load(BufferedReader reader) throws IOException {
        super.load(reader);
        if (!active) {
            return;
        }
        lowMin = CommonParser.readFloat(reader, "lowMin");
        lowMax = CommonParser.readFloat(reader, "lowMax");
    }

    public void load(RangedNumericValue value) {
        super.load(value);
        lowMax = value.lowMax;
        lowMin = value.lowMin;
    }
}
