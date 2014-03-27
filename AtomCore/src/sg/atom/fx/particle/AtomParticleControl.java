/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.particle;

import sg.atom.fx.particle.generation.AtomParticleEmitter;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.io.IOException;

/**
 * A simple hook to the Control's spatial as seen as the original
 * ParticleEmitterControl.
 *
 * <p> In Spatial level, this control managed some local aspects of the
 * particles generation, influence and distribution, as well as the creation and
 * destroy! </p>
 *
 * <p>This is the prototype with the same concept with the SpatialInfoControl in
 * the World package, also hook to the JME3's mechanism and help the manager to
 * tidy up the entities.</p>
 *
 * @author atomix
 */
public class AtomParticleControl implements Control {

    public AtomParticleEmitter parentEmitter;

    public AtomParticleControl() {
    }

    public AtomParticleControl(AtomParticleEmitter parentEmitter) {
        this.parentEmitter = parentEmitter;
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        return this; // WARNING: Sets wrong control on spatial. Will be
        // fixed automatically by AtomParticleEmitter.clone() method.
    }

    @Override
    public void setSpatial(Spatial spatial) {
    }

    public void setEnabled(boolean enabled) {
        parentEmitter.setEnabled(enabled);
    }

    public boolean isEnabled() {
        return parentEmitter.isEnabled();
    }

    @Override
    public void update(float tpf) {
        parentEmitter.updateFromControl(tpf);
    }

    @Override
    public void render(RenderManager rm, ViewPort vp) {
        parentEmitter.renderFromControl(rm, vp);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
    }

    @Override
    public void read(JmeImporter im) throws IOException {
    }
}
