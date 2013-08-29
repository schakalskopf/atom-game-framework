/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atom.corex.shape;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author hungcuong
 */
public class BoneShape extends Node{
    Vector3f start;
    Vector3f end;
    
    BoneShape(String bonename){
        super(bonename+ "_boneShape");
    }
    
        BoneShape(Node parent,String bonename, Vector3f start,Vector3f end){
        super(bonename+ "_boneShape");
        
        
    }
    
}
