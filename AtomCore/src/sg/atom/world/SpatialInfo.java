/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world;

import com.jme3.math.Transform;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Serializable info of Spatial, Wraper for some scenegraph functions
 * @author atomix
 */
public class SpatialInfo {
    public Spatial self;
    public Node parent;
    
    public Transform trans;
    public Transform worldTrans;
    public boolean attached;
    int index;
}
