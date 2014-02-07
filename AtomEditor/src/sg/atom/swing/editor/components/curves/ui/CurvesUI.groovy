/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.helpers;

import sg.atom.corex.scenegraph.spatial.HelperSpatial;
import sg.atom.corex.scenegraph.spatial.SpatialList;
import sg.atom.corex.common.CommonTool;
import sg.atom.managex.managers.HelperManager;
import sg.atom.corex.scenegraph.shape.ArrowShape;
import sg.atom.corex.scenegraph.shape.BoxShape;
import sg.atom.corex.scenegraph.shape.CylinderShape;
import sg.atom.corex.scenegraph.shape.Shape;
import sg.atom.corex.scenegraph.shape.ShapeUtil;
import sg.atom.corex.scenegraph.shape.SphereShape;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;

/**
 *
 * @author hungcuong
 */
public class GizmoHelper extends AbstractHelper implements ActionListener, AnalogListener {

    // Tool level
    private CommonTool commonTool;
    private ShapeUtil shapeUtil;
    private HelperManager helperManager;
    private Camera cam;
    private boolean camFix;
    // SubTool Level
    private Node gizmo;
    private Shape scaleAllShape;
    private Shape scaleXY_WireShape;
    private Shape ScaleYZ_WireShape;
    private Shape scaleZX_WireShape;
    private Shape arrowZShape;
    private Shape arrowXShape;
    private Shape arrowYShape;
    private Sh