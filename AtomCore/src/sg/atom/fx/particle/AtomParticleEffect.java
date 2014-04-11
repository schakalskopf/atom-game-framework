/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.particle;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;
import java.util.Properties;
import org.apache.commons.configuration.Configuration;
import sg.atom.core.AbstractManager;
import sg.atom.fx.AtomEffect;

/**
 * Facade of the Particle effect, which be managed by the
 * AtomParticleSystemManager.
 *
 * @author cuong.nguyenmanh2
 */
public class AtomParticleEffect implements AtomEffect{

    @Override
    public int getIndex() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void active(Spatial target) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deactive() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void init(Application app, AbstractManager... managers) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void load(AssetManager assetManager) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void finish() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void config(Configuration configuration) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
