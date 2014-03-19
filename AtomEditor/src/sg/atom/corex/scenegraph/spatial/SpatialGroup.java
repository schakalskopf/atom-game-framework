/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.scenegraph.spatial;

import com.google.common.base.Function;
import sg.atom.managex.api.select.Selectable;
import com.jme3.scene.Spatial;
import java.util.ArrayList;

/**
 * An named group of Spatial. Use spatialList underlied. Can be nested.
 *
 * @author hungcuong
 */
public class SpatialGroup implements Selectable {

    public static enum GroupPartition {

        Layered, Overlap, NonOverlap
    }
    private SpatialList list;
    private GroupPartition groupPartition;
    private String name;
    private int id;
    ArrayList<SpatialGroup> children;

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

    public GroupPartition getGroupPartition() {
        return groupPartition;
    }

    public void setGroupPartition(GroupPartition groupPartition) {
        this.groupPartition = groupPartition;
    }

    public void group(Spatial... spatials) {
    }

    public void unGroup(Spatial spatials) {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addChild(SpatialGroup group) {
        children.add(group);
    }

    public void attachAnotherGroup(SpatialGroup group) {
        children.add(group);
    }

    public void apply(Function<Spatial, Object> modifier) {
    }
}
