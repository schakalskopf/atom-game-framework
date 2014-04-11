/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world.spatial;

import com.jme3.math.Transform;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.io.Serializable;

/**
 * Serializable info of Spatial, Wraper for some scenegraph functions. Concepts
 * from ManagedObject, Netbean API's Node.
 *
 * <p>Fly weight pattern to serialize spatial information into compressed form.
 * This is also a form of SpatialRelation between Node-Spatial which popular in
 * scenegraph, called Parent-Child. Can be seen as a ManagedReference to a
 * Spatial via SpatialProxy.
 *
 * <p>Concurrent (Task and flow): Any operation over the holded SpatialInfo of
 * the associated Node will result as concurrent operations (delay, defered,
 * lazy, parallel ...) and the dataflow is monitored in the dataflow framework.
 *
 * <p>Hash: Under it create a Guava's Funnel and compressed with automatic
 * decoders via protocol buffer.
 *
 * <ul></ul>
 *
 * FIXME: Use soft reference
 *
 * @author atomix
 */
public class SpatialInfo implements Serializable, Cloneable {

    public Spatial spatial;
    public Node parent;
    public Transform localTransform;
    public Transform worldTransform;
    public boolean attached;
    int index;

    public Spatial getSpatial() {
        return spatial;
    }

    public void setSpatial(Spatial spatial) {
        this.spatial = spatial;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isAttached() {
        return attached;
    }

    public void setAttached(boolean attached) {
        this.attached = attached;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Transform getLocalTransform() {
        return localTransform;
    }

    public Transform getWorldTransform() {
        return worldTransform;
    }

    public void setLocalTransform(Transform localTransform) {
        this.localTransform = localTransform;
    }

    public void setWorldTransform(Transform worldTransform) {
        this.worldTransform = worldTransform;
    }
}
