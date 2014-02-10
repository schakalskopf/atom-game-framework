/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.editor.managers.spatial;

import sg.atom.corex.scenegraph.spatial.SpatialInfo;
import sg.atom.corex.scenegraph.spatial.SpatialList;
import sg.atom.corex.common.CommonTool;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author hungcuong
 */
public class SpatialManager {

    private CommonTool commonTool;
    // SubTool Level
    SpatialList globalSpatialList;
    private SpatialInfo rootNodeInfo;

    public SpatialManager() {
        this.commonTool = CommonTool.getDefault(null);
        this.globalSpatialList = new SpatialList();
        this.rootNodeInfo= new SpatialInfo(commonTool.getRootNode(), null);
    }

}
