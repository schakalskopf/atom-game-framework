package sg.atom.managex.api.select;

import sg.atom.corex.scenegraph.spatial.SpatialList;
import com.jme3.scene.Spatial;

/**
 * Selectable is an interface of Spatial, which indicate the albility of that
 * Spatial can be seen under the search of select function of the Editor.
 *
 * @author hungcuong
 */
public interface Selectable {

    public boolean selected = false;
    public boolean highlighted = false;
    public boolean selectable = true;

    public abstract Spatial getRoot();

    public abstract SpatialList getSelectList();

    public abstract boolean isSelected();

    public abstract boolean isSelectable();

    public abstract void highlight();
}
