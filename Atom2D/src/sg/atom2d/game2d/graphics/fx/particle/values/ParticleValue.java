/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.game2d.graphics.fx.particle.values;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import sg.atom2d.game2d.graphics.fx.particle.ParticleEmitter;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class ParticleValue {
    boolean active;
    boolean alwaysActive;

    public void setAlwaysActive(boolean alwaysActive) {
        this.alwaysActive = alwaysActive;
    }

    public boolean isAlwaysActive() {
        return alwaysActive;
    }

    public boolean isActive() {
        return alwaysActive || active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void save(Writer output) throws IOException {
        if (!alwaysActive) {
            output.write("active: " + active + "\n");
        } else {
            active = true;
        }
    }

    public void load(BufferedReader reader) throws IOException {
        if (!alwaysActive) {
            active = ParticleEmitter.readBoolean(reader, "active");
        } else {
            active = true;
        }
    }

    public void load(ParticleValue value) {
        active = value.active;
        alwaysActive = value.alwaysActive;
    }
    
}
