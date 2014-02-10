/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.game2d.graphics.fx.particle.values;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import sg.atom.utils.CommonParser;
import sg.atom2d.game2d.graphics.fx.particle.ParticleEmitter;
import sg.atom2d.game2d.graphics.fx.particle.ParticleEmitter;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class NumericValue extends EditableValue {
    float value;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void save(Writer output) throws IOException {
        super.save(output);
        if (!active) {
            return;
        }
        output.write("value: " + value + "\n");
    }

    public void load(BufferedReader reader) throws IOException {
        super.load(reader);
        if (!active) {
            return;
        }
        value = CommonParser.readFloat(reader, "value");
    }

    public void load(NumericValue value) {
        super.load(value);
        this.value = value.value;
    }
    
}
