/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.scenegraph.spatial;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Proxy for Spatial.
 *
 * @author hungcuong
 */
public class SpatialPresentor {

    public static final int NORMAL = 0;
    public static final int BOX = 1;
    public static final int VERTEX = 2;
    public static final int BILLBOARD = 3;
    int type;
    private int currentDisplayType;
    Vector3f localTranslate;
    private Spatial orginalSpatial;

    public SpatialPresentor(Spatial spatial, int type) {
        this.type = type;
        this.localTranslate = new Vector3f(Vector3f.ZERO);
        this.orginalSpatial = spatial;
        this.currentDisplayType = 0;
    }

    public Spatial getCurrentSpatial() {
        switch (currentDisplayType) {
            case NORMAL:
                return this.getOrginalSpatial();

        }
        return null;
    }

    public void initPresentor() {
        createBox();
        createBillboard();
        createPoint();
    }

    private void createBox() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void createBillboard() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void createPoint() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * @return the currentDisplayType
     */
    public int getCurrentDisplayType() {
        return currentDisplayType;
    }

    /**
     * @param currentDisplayType the currentDisplayType to set
     */
    public void setCurrentDisplayType(int currentDisplayType) {
        this.currentDisplayType = currentDisplayType;
    }

    /**
     * @return the orginalSpatial
     */
    public Spatial getOrginalSpatial() {
        return orginalSpatial;
    }

    /**
     * @param orginalSpatial the orginalSpatial to set
     */
    public void setOrginalSpatial(Spatial orginalSpatial) {
        this.orginalSpatial = orginalSpatial;
    }
}
