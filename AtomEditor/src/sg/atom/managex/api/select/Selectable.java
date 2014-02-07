package sg.atom.managex.api.select;

import sg.atom.corex.scenegraph.spatial.SpatialList;
import com.jme3.scene.Spatial;

/**
 *
 * @author hungcuong
 */
public interface Selectable {
    boolean selected=false;
    boolean highlighted=false;
    boolean selectable=true;
    
    public abstract Spatial getRoot();
    public abstract SpatialList getSelectList();
    public abstract boolean isSelected();
    public abstract boolean isSelectable();
    public abstract void highlight();
}
