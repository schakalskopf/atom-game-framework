/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.managers;

import sg.atom.corex.scenegraph.spatial.SpatialList;
import sg.atom.corex.common.CommonTool;
import sg.atom.managex.helpers.AbstractHelper;
import sg.atom.managex.helpers.GizmoHelper;
import sg.atom.managex.helpers.GridHelper;
import sg.atom.managex.helpers.MarkHelper;
import sg.atom.managex.helpers.ShapeOperationHelper;
import sg.atom.managex.helpers.WorkingHelper;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hungcuong
 */
public class HelperManager extends AbstractControl {
    
    
    public Node helperNode, targetNode;
    
    public String nowAction;
    
    private CommonTool commonTool;
    
    // THE HELPER
    private WorkingHelper workingHelper;
    private GizmoHelper gizmoHelper; 
    private MarkHelper markHelper;
    private ShapeOperationHelper shapeHelper;
    private GridHelper gridHelper;
    
    private List<AbstractHelper> helperList = new LinkedList<AbstractHelper>();
    private SpatialList selectableList = new SpatialList();
    private CollisionResult results;

    public HelperManager() {
        this.commonTool = CommonTool.getDefault(null);
    }

    public void initHelper() {
        this.gizmoHelper = new GizmoHelper(this);
        this.gridHelper = new GridHelper(this);
        this.workingHelper = new WorkingHelper( this);
        this.markHelper = new MarkHelper(this);
        this.shapeHelper = new ShapeOperationHelper(this);

        gizmoHelper.initHelper();
        workingHelper.initHelper();
        markHelper.initHelper();
        shapeHelper.initHelper();
        gridHelper.initHelper();

        helperList.add(gizmoHelper);
        helperList.add(workingHelper);
        helperList.add(markHelper);
        helperList.add(shapeHelper);
        helperList.add(gridHelper);
        
        helperNode.attachChild(gridHelper.getSubNode());
        helperNode.attachChild(gizmoHelper.getSubNode());
        
        commonTool.getRootNode().attachChild(helperNode);
        
        initHelperAction();

    }

    public void initHelperAction() {
        commonTool.getInputManager().addMapping("RotateLeft", new MouseAxisTrigger(MouseInput.AXIS_X, true));

        commonTool.getInputManager().addMapping("RotateRight", new MouseAxisTrigger(MouseInput.AXIS_X, false));

        commonTool.getInputManager().addMapping("RotateUp", new MouseAxisTrigger(MouseInput.AXIS_Y, false));

        commonTool.getInputManager().addMapping("RotateDown", new MouseAxisTrigger(MouseInput.AXIS_Y, true));
        
        commonTool.getInputManager().addListener(gizmoHelper, new String[]{"RotateUp", "RotateDown","RotateLeft","RotateRight"});
    }
    /*
    public void doClickMarkObject() {
        if (results.size() > 0) {
            CollisionResult closest = results.getClosestCollision();
            markHelper.setMarkObject(closest.getGeometry());
            markHelper.setMarkLocation(closest.getContactPoint());
            markHelper.setMarkVisible(true);
        } else {
            markHelper.setMarkVisible(false);

            
            if (markHelper.markObj != null) {
            if (isSelectable(markHelper.markObj)) {
            doHighlightObject(markHelper.markObj, true);
            }
            }
             
        }
    }
    */
    public boolean isHelperNode(Spatial node){
        for (AbstractHelper helper:helperList){
            if (helper.isHelperNode(node)){
                return true;
            }
        }
        return false;
    }

    /**
     * @return the selectableList
     */
    public SpatialList getSelectableList() {
       for (AbstractHelper helper:helperList){
           selectableList.addList(helper.getSelectableList());
        }
        return selectableList;
    }

    @Override
    protected void controlUpdate(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public WorkingHelper getWorkingHelper() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public CollisionResults getCurrentShootResult() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
