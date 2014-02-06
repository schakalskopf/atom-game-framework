/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world;

import com.jme3.scene.Spatial;
import java.util.HashMap;
import sg.atom.core.timing.TimeProvider;

/**
 * A simple delayed detachment system.
 * @author cuong.nguyenmanh2
 */
public class SpatialExpires {

    protected HashMap<Spatial, Float> expires = new HashMap<Spatial, Float>();

    public SpatialExpires() {
    }

    public SpatialExpires(TimeProvider timeProvider) {
    }

    public void checkExpires(float tpf) {
        // Update particle expires
        for (Spatial particle : expires.keySet()) {
            float floatValue = expires.get(particle).floatValue();
            if (floatValue > 0) {
                floatValue -= tpf;
                expires.put(particle, new Float(floatValue));
            } else {
                particle.removeFromParent();
                //System.out.println("Detach particle");
            }
        }
    }

    public void setExpire(Spatial sp, float lifeTime) {
        expires.put(sp, new Float(lifeTime));
    }
}
