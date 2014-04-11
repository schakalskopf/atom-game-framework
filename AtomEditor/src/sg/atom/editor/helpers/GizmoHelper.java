/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.editor.helpers;

import sg.atom.corex.scenegraph.spatial.HelperSpatial;
import sg.atom.corex.scenegraph.spatial.SpatialList;
import sg.atom.corex.common.CommonTool;
import sg.atom.editor.managers.HelperManager;
import sg.atom.corex.scenegraph.shape.ArrowShape;
import sg.atom.corex.scenegraph.shape.BoxShape;
import sg.atom.corex.scenegraph.shape.CylinderShape;
import sg.atom.corex.scenegraph.shape.Shape;
import sg.atom.corex.scenegraph.shape.ShapeUtil;
import sg.atom.corex.scenegraph.shape.SphereShape;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import sg.atom.corex.scenegraph.spatial.EditorSpatialInfo;
import sg.atom.editor.managers.EditorSelectionManager;

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
    Transform targetTransform;
    EditorSpatialInfo targetSpatial;
    
    // SubTool Level
    private Node gizmo;
    private Shape scaleAllShape;
    private Shape scaleXY_WireShape;
    private Shape ScaleYZ_WireShape;
    private Shape scaleZX_WireShape;
    private Shape arrowZShape;
    private Shape arrowXShape;
    private Shape arrowYShape;
    private Shape scaleXYShape;
    private Shape scaleYZShape;
    private Shape scaleZXShape;
    private Shape rotateYZShape;
    private Shape rotateXYShape;
    private Shape rotateZXShape;
    private HelperSpatial thisHelperSpatial;
    private SpatialList selectableList = new SpatialList();

    public GizmoHelper(HelperManager helperManager) {
        super("GizmoHelper");

        this.commonTool = CommonTool.getDefault(null);
        this.shapeUtil = commonTool.getShapeUtil();
        this.helperManager = helperManager;
        this.cam = commonTool.getCurrentCam();
        this.camFix = true;

        // SubTool Level Init
        this.gizmo = new Node(this.getName());
        this.thisHelperSpatial = new HelperSpatial(gizmo, null);
    }

    public void initHelper() {

        // ARROW
        arrowXShape = ((ArrowShape.Builder) shapeUtil.createShape(Shape.ARROW, "ArrowX", gizmo))
                .setDir(Vector3f.UNIT_X)
                .setLineWidth(4)
                .setPos(Vector3f.ZERO)
                .setOpacity(1.0f)
                .setColor(ColorRGBA.Red)
                .build();

        arrowYShape = ((ArrowShape.Builder) shapeUtil.createShape(Shape.ARROW, "ArrowY", gizmo))
                .setDir(Vector3f.UNIT_Y)
                .setLineWidth(4)
                .setPos(Vector3f.ZERO)
                .setOpacity(1.0f)
                .setColor(ColorRGBA.Green)
                .build();

        arrowZShape = ((ArrowShape.Builder) shapeUtil.createShape(Shape.ARROW, "ArrowZ", gizmo))
                .setDir(Vector3f.UNIT_Z)
                .setLineWidth(4)
                .setPos(Vector3f.ZERO)
                .setOpacity(1.0f)
                .setColor(ColorRGBA.Blue)
                .build();

        // SCALE ALL
        scaleAllShape = ((SphereShape.Builder) shapeUtil.createShape(Shape.SPHERE, "ScaleAll", gizmo))
                .setAllParam(16, 16, 0.1f)
                .setPos(Vector3f.ZERO)
                .setColor(ColorRGBA.Orange)
                .build();

        // SCALE BOX WIRE
        scaleXY_WireShape = ((BoxShape.Builder) shapeUtil.createShape(Shape.BOX, "ScaleXY_Wire", gizmo))
                .setAllSize(0.25f, 0.25f, 0)
                .setPos(0.25f, 0.25f, 0)
                .setWired(true)
                .setColor(ColorRGBA.Blue)
                .setOpacity(0.7f)
                .build();

        ScaleYZ_WireShape = ((BoxShape.Builder) shapeUtil.createShape(Shape.BOX, "ScaleYZ_Wire", gizmo))
                .setAllSize(new Vector3f(0, 0.25f, 0.25f))
                .setPos(new Vector3f(0, 0.25f, 0.25f))
                .setWired(true)
                .setColor(ColorRGBA.Red)
                .setOpacity(0.7f)
                .build();

        scaleZX_WireShape = ((BoxShape.Builder) shapeUtil.createShape(Shape.BOX, "ScaleZX_Wire", gizmo))
                .setAllSize(0.25f, 0, 0.25f)
                .setPos(0.25f, 0, 0.25f)
                .setWired(true)
                .setColor(ColorRGBA.Green)
                .setOpacity(0.7f)
                .build();

        // SCALE BOX 
        scaleXYShape = ((BoxShape.Builder) shapeUtil.createShape(Shape.BOX, "ScaleXY", gizmo))
                .setAllSize(0.25f, 0.25f, 0)
                .setPos(0.25f, 0.25f, 0)
                .setWired(false)
                .setColor(ColorRGBA.Blue)
                .setOpacity(0.7f)
                .build();

        scaleYZShape = ((BoxShape.Builder) shapeUtil.createShape(Shape.BOX, "ScaleYZ", gizmo))
                .setAllSize(0, 0.25f, 0.25f)
                .setPos(0, 0.25f, 0.25f)
                .setWired(false)
                .setColor(ColorRGBA.Red)
                .setOpacity(0.7f)
                .build();

        scaleZXShape = ((BoxShape.Builder) shapeUtil.createShape(Shape.BOX, "ScaleZX", gizmo))
                .setAllSize(new Vector3f(0.25f, 0, 0.25f))
                .setPos(new Vector3f(0.25f, 0, 0.25f))
                .setWired(false)
                .setColor(ColorRGBA.Green)
                .setOpacity(0.7f)
                .build();


        // ROTATE CYLINDER
        float PI = (float) Math.PI;
        float PI2 = PI / 2;
        float PI4 = PI / 4;

        rotateXYShape = ((CylinderShape.Builder) shapeUtil.createShape(Shape.CYLINDER, "RotateXY", gizmo))
                .setAllParam(8, 8, 0.04f, 0.02f)
                .setHeight(0.1f)
                .setPos(0.75f, 0, 0)
                .setRot(0, PI2, PI2)
                .setColor(ColorRGBA.Red)
                .setOpacity(1f)
                .build();
        rotateYZShape = ((CylinderShape.Builder) shapeUtil.createShape(Shape.CYLINDER, "RotateYZ", gizmo))
                .setAllParam(8, 8, 0.04f, 0.02f)
                .setHeight(0.1f)
                .setPos(0, 0.75f, 0)
                .setRot(-PI2, PI4, 0)
                .setColor(ColorRGBA.Green)
                .setOpacity(1f)
                .build();

        rotateZXShape = ((CylinderShape.Builder) shapeUtil.createShape(Shape.CYLINDER, "RotateZX", gizmo))
                .setAllParam(8, 8, 0.04f, 0.02f)
                .setHeight(0.1f)
                .setPos(0, 0, 0.75f)
                .setRot(0, 0, 0)
                .setColor(ColorRGBA.Blue)
                .setOpacity(1f)
                .build();

        // 
        makeSelectableList();
        gizmo.addControl(this);
    }

    @Override
    public Node getSubNode() {
        return gizmo;
    }

    public void makeSelectableList() {
        selectableList.add(new HelperSpatial(arrowXShape, null));
        selectableList.add(new HelperSpatial(arrowYShape, null));
        selectableList.add(new HelperSpatial(arrowZShape, null));

        selectableList.add(new HelperSpatial(rotateXYShape, null));
        selectableList.add(new HelperSpatial(rotateYZShape, null));
        selectableList.add(new HelperSpatial(rotateZXShape, null));

        selectableList.add(new HelperSpatial(scaleAllShape, null));

        selectableList.add(new HelperSpatial(scaleXYShape, null));
        selectableList.add(new HelperSpatial(scaleYZShape, null));
        selectableList.add(new HelperSpatial(scaleZXShape, null));

        selectableList.add(new HelperSpatial(scaleXY_WireShape, null));
        selectableList.add(new HelperSpatial(ScaleYZ_WireShape, null));
        selectableList.add(new HelperSpatial(scaleZX_WireShape, null));
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (camFix) {
            Vector3f cursorPos = gizmo.getWorldTranslation();
            Vector3f campos = cam.getLocation();
            float dis2 = campos.distance(cursorPos);
            float scale = dis2 / 10;
            float customScale = 2.5f;
            float camScale = commonTool.getCamUtil().getCamFOV() / 50;
            float camScaleFix = camScale * camScale;
            gizmo.setLocalScale(scale * customScale * camScale);
        }

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public Control cloneForSpatial(Spatial spatial) {
        return this;
    }

    public boolean isCamFix() {
        return camFix;
    }

    public void setCamFix(boolean camFix) {
        this.camFix = camFix;
    }

    public void onAction(String name, boolean pressed, float tpf) {
        //System.out.println("You rotate : hehe !");
    }

    public void onAnalog(String name, float value, float tpf) {
        Geometry targetObj = commonTool.getManager(EditorSelectionManager.class).getCurrentSelectedObject();
        if (targetObj != null) {
            if (name.equals("RotateUp")) {
                targetObj.rotate(value * 4, 0, 0);
                //System.out.println("You rotate : " + targetObj.getName() + " hehe !");
            } else if (name.equals("RotateDown")) {
                targetObj.rotate(-value * 4, 0, 0);

            } else if (name.equals("RotateLeft")) {
                targetObj.rotate(0, value * 4, 0);

            } else if (name.equals("RotateRight")) {
                targetObj.rotate(0, -value * 4, 0);
            }
            //System.out.println("You rotate : hehe !");
        }
    }

    @Override
    public boolean isHelperNode(Spatial node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SpatialList getSelectableList() {
        return selectableList;
    }

    public SpatialList getShootableList() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    //TRANSFORMATION FUNCTIONS-----------------------------------------------------

    public void translate() {
    }

    public void rotate() {
    }

    public void scale() {
    }
}
