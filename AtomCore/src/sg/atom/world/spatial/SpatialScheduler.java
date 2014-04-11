/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.spatial;

import com.jme3.scene.Spatial;
import java.util.HashMap;
import sg.atom.core.timing.TimeProvider;

/**
 * A simple but useful delayed attach/ detachment system. That's it, it's not a
 * bunch of complex cinematic events, nor have a timeline nor trying to "notify"
 * anyone else. Beside of that, its enable highly concurrent and timing jobs
 * upon the scenegraph.
 *
 * <p>Work together with StageManager to make character and actor, entities
 * work.</p>
 *
 * <p>FIXME: Use guava Cache instead of HashMap. Use forwardingCache to search
 * in SceneGraphUtils mainCache. So if the Spatial relation already changed in
 * another thread, it will not cause any exception.
 *
 * <p>FIXME: Going to extends AlignedScheduler for better alignment and
 * coordnation with other scheduler and concurrent controls.
 *
 * @author cuong.nguyenmanh2
 */
public class SpatialScheduler {

    protected HashMap<SpatialInfo, Float> spawns = new HashMap<SpatialInfo, Float>();
    protected HashMap<Spatial, Float> expires = new HashMap<Spatial, Float>();

    public SpatialScheduler() {
    }

    public SpatialScheduler(TimeProvider timeProvider) {
    }

    public void checkSpawns(float tpf) {
        // Update particle expires
        for (SpatialInfo sp : spawns.keySet()) {
            float floatValue = spawns.get(sp).floatValue();
            if (floatValue > 0) {
                floatValue -= tpf;
                spawns.put(sp, new Float(floatValue));
            } else {
                //sp.addToParent();
                //System.out.println("Detach particle");
            }
        }
    }

    public void setSpawns(Spatial sp, float lifeTime) {
        spawns.put(createDummySpatialInfo(sp), new Float(lifeTime));
    }

    public void setSpawns(SpatialInfo sp, float lifeTime) {
        spawns.put(sp, new Float(lifeTime));
    }

    private SpatialInfo createDummySpatialInfo(Spatial sp) {
        return new SpatialInfo();
    }

    public void checkExpires(float tpf) {
        // Update particle expires
        for (Spatial sp : expires.keySet()) {
            float floatValue = expires.get(sp).floatValue();
            if (floatValue > 0) {
                floatValue -= tpf;
                expires.put(sp, new Float(floatValue));
            } else {
                sp.removeFromParent();
                //System.out.println("Detach particle");
            }
        }
    }

    public void setExpire(Spatial sp, float lifeTime) {
        expires.put(sp, new Float(lifeTime));
    }
}
