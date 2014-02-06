/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package atom.managex.helpers;

import atom.corex.scenegraph.spatial.SpatialList;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author hungcuong
 */
public abstract class AbstractHelper extends AbstractControl{
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    AbstractHelper(String name){
        this.name = name;

    }
    public abstract void initHelper();

    public abstract Node getSubNode();
    
    public abstract SpatialList getSelectableList();
    
    public boolean isHelperNode(Spatial sp){
        return getSubNode().hasChild(sp);
    }
    
    

}
