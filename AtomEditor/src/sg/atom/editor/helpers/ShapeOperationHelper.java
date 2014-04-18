/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.editor.helpers;

import sg.atom.corex.scenegraph.spatial.SpatialList;
import sg.atom.corex.common.CommonTool;
import sg.atom.editor.managers.HelperManager;
import sg.atom.corex.scenegraph.shape.ShapeUtils;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.util.HashMap;

/**
 *
 * @author hungcuong
 */
public class ShapeOperationHelper extends AbstractHelper {

    // Tool level
    private CommonTool commonTool;
    private ShapeUtils shapeUtil;
    private HelperManager helperManager;
    // SubTool Level
    private String nowAction;
    private Geometry tempLine;
    private Geometry tempShape;
    private float lastUpdateTime;
    private HashMap<String, Vector3f> workingPoints;
    private Node helperNode;
    private Node targetNode;

    public ShapeOperationHelper(HelperManager helperManager) {
        super("shapeHelper");
        this.commonTool = CommonTool.getDefault(null);
        this.shapeUtil = commonTool.getShapeUtil();
        this.helperManager = helperManager;

    }

    private void doShapeOperation() {
    }

    @Override
    public void initHelper() {
        workingPoints = helperManager.getWorkingHelper().getWorkingPoints();
    }

    public void setHelperNode(Node helperNode) {
        this.helperNode = helperNode;
    }

    public void setTargetNode(Node targetNode) {
        this.targetNode = targetNode;
    }

    public void doFinishShapeCreate() {
        helperNode.detachChild(tempShape);
        Vector3f oldLoc = helperNode.getLocalTranslation();
        targetNode.attachChild(tempShape);
        tempShape.setLocalTranslation(oldLoc);
    }

    @Override
    public Node getSubNode() {
        return null;
    }

    @Override
    protected void controlUpdate(float tpf) {
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public Control cloneForSpatial(Spatial spatial) {
        return this;
    }

    @Override
    public SpatialList getSelectableList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
