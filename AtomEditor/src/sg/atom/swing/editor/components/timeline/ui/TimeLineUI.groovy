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
    
    private List<AbstractHelper> helperList = new LinkedList<Abs