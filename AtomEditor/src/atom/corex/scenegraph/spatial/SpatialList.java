/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atom.corex.scenegraph.spatial;

import atom.managex.select.Selectable;
import com.jme3.scene.Spatial;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hungcuong
 */
public class SpatialList implements Selectable {

    private List<SpatialInfo> list = new LinkedList<SpatialInfo>();

    /**
     * @return the list
     */
    public List<SpatialInfo> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<SpatialInfo> list) {
        this.list = list;
    }

    public void add(SpatialInfo spatialInfo) {
        list.add(spatialInfo);
    }

    public void addList(SpatialList otherList) {
        list.addAll(otherList.getList());
    }

    public Spatial getRoot() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SpatialList getSelectList() {
        return this;
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
    
    public int size(){
        return list.size();
    }
}
