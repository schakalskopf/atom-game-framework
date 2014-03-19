/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.scenegraph.spatial;

import sg.atom.corex.common.CommonTool;
import sg.atom.managex.api.select.Selectable;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author hungcuong
 */
public class SpatialInfo implements Selectable {

    private SpatialPresentor presentor;
    private SpatialInfo parentInfo;
    private int type;
    private SpatialList children = new SpatialList();

    public SpatialInfo(Spatial spatial, SpatialInfo parentInfo) {

        this.parentInfo = parentInfo;
        constructSpatialInfoTree(spatial);
    }

    /**
     * @return the spartial
     */
    public Spatial getSpatial() {
        return this.presentor.getOrginalSpatial();
    }

    /**
     * @param spartial the spartial to set
     */
    public void setSpatial(Spatial spatial) {
        this.presentor.setOrginalSpatial(spatial);
    }

    /**
     * @return the presentor
     */
    public SpatialPresentor getPresentor() {
        return presentor;
    }

    /**
     * @param presentor the presentor to set
     */
    public void setPresentor(SpatialPresentor presentor) {
        this.presentor = presentor;
    }

    public Spatial getRoot() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SpatialList getSelectList() {
        SpatialList tempList = new SpatialList();
        tempList.add(this);
        return tempList;
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

    private void constructSpatialInfoTree(Spatial spatial) {

        if (CommonTool.getDefault(null).getHelperManager().isHelperNode(spatial)) {
            // Is a Helper Node
            //throw new IllegalArgumentException("The spatial is a Helper Node !");
            this.type = 2;
        } else {
            // Is a Real Node         

            if (spatial instanceof Node) {
                this.type = 0;
                for (Spatial sp : ((Node) spatial).getChildren()) {
                    SpatialInfo child = new SpatialInfo(sp, this);
                    children.add(child);
                }
            } else {
                this.type = 1;
            }


        }

        this.presentor = new SpatialPresentor(spatial, this.type);
    }

    /**
     * <code>SpatialDisplayInfo</code> : is the display info of a spatial in
     * Editor Level such as : <br> show/hide, color, displayType
     */
    class SpatialDisplayInfo {

        /**
         * <code>show</code> : Set the spatial to be visible or invisible in the
         * Editor Level
         */
        boolean show;
        /**
         * <code>displayType</code> : Set the display type for the
         * {@link: Spatial} in the editor<br> 0 : normal ( like the default
         * shape in the engine ) <br> 1 : simple ( mesh -> box ; particle -> dot
         * ) <br> 3 : optimize ( display as "-- LOD level" )
         */
        int displayType;
        ColorRGBA displayColor;

        SpatialDisplayInfo() {
            this.show = true;
            this.displayType = 0;
            this.displayColor = new ColorRGBA(ColorRGBA.White);

        }
    }
}
