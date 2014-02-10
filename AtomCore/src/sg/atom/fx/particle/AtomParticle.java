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
import com.jme3.effect.ParticleEmitter;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import java.util.Queue;

/**
 * Represents a single particle in a {@link ParticleEmitter}.
 *
 * @author atomix
 */
public class AtomParticle extends Spatial {

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