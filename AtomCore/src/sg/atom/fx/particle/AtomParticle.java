/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.particle;

import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.UnsupportedCollisionException;
import com.jme3.effect.Particle;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import java.util.Queue;

/**
 * Represents a single particle in the whole particle system. In this level of
 * abstract AtomParticle is just a Spatial, which no garantee what is its
 * representor mean no graphics sides. So for short: It's just a moving point!
 * No more no less.
 *
 * <p>What make AtomParticle fancy is: It can be used in much more wide range of
 * application compare to JME3's original which only low level implementation.
 * At the momment of speech, I also aware of few others imlementation of
 * contributors but no one have this level of abstract and flexibility,
 * features.</p>
 *
 * <b>Features:</b>
 *
 * <ul>
 *
 * <li>Particle can just be a point. Particle can also "be" a force for
 * simmulation! </li>
 *
 * <li>Particle can also have children(optional). Forces, constrainst or
 * influencers affect and direct the parent motion and those children also. A
 * rapid bit-base tree or list used to index and loop through them if need.
 * </li>
 *
 * <li>Compressed form: AtomParticle save internal "hash" type of its self and
 * children to save memory. Allow milions of particles on screen. AtomParticle
 * can also be translated into different format: Most common usage is to
 * generate a similiar JME's Particle which have a same attributes and
 * structure. So each AtomParticle decomposed into a node with one or some
 * ParticleEmitter. AtomParticleControl manage AtomParticle and also can take
 * care of the generated structure.</li>
 *
 * <li> Gabage free, memory cleaning helper: Construction of indexing data
 * structure is "off" by default, and can be actived again anytime the parent
 * particle still actives. If it no longer actived (mean be swept away by GC)
 * the system still track down them automaticly! </li>
 *
 * <li>Particle can be edited/tracked/debug. Events are spread by EventBus.</li>
 *
 * <li>Particle motion can be very complex. </li> </ul>
 *
 * @author atomix
 */
public class AtomParticle extends Node {

    /**
     * Particle velocity.
     */
    public final Vector3f velocity = new Vector3f();
    /**
     * Current particle position
     */
    public final Vector3f position = new Vector3f();
    /**
     * Particle color
     */
    public final ColorRGBA color = new ColorRGBA(0, 0, 0, 0);
    /**
     * Particle size or radius.
     */
    public float size;
    /**
     * Particle remaining life, in seconds.
     */
    public float life;
    /**
     * The initial particle life
     */
    public float startlife;
    /**
     * Particle rotation angle (in radians).
     */
    public float angle;
    /**
     * Particle rotation angle speed (in radians).
     */
    public float rotateSpeed;
    /**
     * Particle image index.
     */
    public int imageIndex = 0;

    /**
     * Distance to camera. Only used for sorted particles.
     */
    //public float distToCam;
    public Particle fromParticle() {
        return null;

    }

    @Override
    public void updateModelBound() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setModelBound(BoundingVolume modelBound) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getVertexCount() {
        return 0;
    }

    @Override
    public int getTriangleCount() {
        return 0;
    }

    @Override
    public Spatial deepClone() {
        return null;
    }

    @Override
    public void depthFirstTraversal(SceneGraphVisitor visitor) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void breadthFirstTraversal(SceneGraphVisitor visitor, Queue<Spatial> queue) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int collideWith(Collidable other, CollisionResults results) throws UnsupportedCollisionException {
        return 0;
    }
}