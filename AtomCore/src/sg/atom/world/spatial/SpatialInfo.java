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
 * Serializable info of Spatial, Wraper for some scenegraph functions.
 *
 * <p>Fly weight pattern to serialize spatial information into compressed form.
 *
 * <p>This is a form of SpatialRelation between Node-Spatial which popular in
 * scenegraph, called Parent-Child.
 *
 * <p>Under it create a Guava's Funnel and compressed with automatic decoders via
 * protocol buffer.
 *
 * <ul></ul>
 *
 * @author atomix
 */
public class SpatialInfo implements Serializable, Cloneable {

    public Spatial self;
    public Node parent;
    public Transform trans;
    public Transform worldTrans;
    public boolean attached;
    int index;
}
