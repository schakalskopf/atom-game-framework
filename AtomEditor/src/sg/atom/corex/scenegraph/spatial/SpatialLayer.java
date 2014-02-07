/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.scenegraph.spatial;

import sg.atom.managex.api.select.Selectable;
import com.jme3.scene.Spatial;

/**
 *
 * @author hungcuong
 */
public class SpatialLayer implements Selectable {
    private SpatialList list=new SpatialList();

    public SpatialLayer(SpatialList list) {
        this.list = list;
    }

    public Spatial getRoot() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SpatialList getSelectList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isSelected() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isSelectable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void highlight() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    
}
