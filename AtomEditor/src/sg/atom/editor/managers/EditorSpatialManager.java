/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.editor.managers;

import java.util.ArrayList;
import java.util.Properties;
import sg.atom.core.AbstractManager;
import sg.atom.corex.scenegraph.spatial.SpatialInfo;
import sg.atom.corex.scenegraph.spatial.SpatialList;
import sg.atom.corex.common.CommonTool;
import sg.atom.corex.scenegraph.spatial.SpatialGroup;

/**
 *
 * @author hungcuong
 */
public class EditorSpatialManager extends AbstractManager {

    private CommonTool commonTool;
    // SubTool Level
    SpatialList globalSpatialList;
    SpatialInfo rootNodeInfo;
    ArrayList<SpatialGroup> layers;

    public EditorSpatialManager() {
        this.commonTool = CommonTool.getDefault(null);
        this.globalSpatialList = new SpatialList();
        this.rootNodeInfo = new SpatialInfo(commonTool.getRootNode(), null);
    }

    public void init() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void config(Properties props) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void update(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void finish() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public LifeCyclePhase getCurrentPhase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public float getProgressPercent(LifeCyclePhase aPhrase) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
