/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.scenegraph.spatial;

import sg.atom.managex.api.select.Selectable;
import com.jme3.scene.Spatial;

/**
 * An atribite named group of Spatial. Use spatialList underlied.
 * @author hungcuong
 */
public class SpatialGroup implements Selectable {
    private SpatialList list;

    public SpatialGroup(SpatialList list) {
        this.list = list;
    }

    public Spatial getRoot() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SpatialList getSelectList() {
        return list;
    }

    public boolean isSelected() {
        return false;
    }

    public boolean isSelectable() {
        return true;
    }

    public void highlight() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
