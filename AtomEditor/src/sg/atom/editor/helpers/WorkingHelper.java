/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.editor.helpers;

import sg.atom.corex.scenegraph.spatial.SpatialList;
import sg.atom.corex.common.CommonTool;
import sg.atom.editor.managers.HelperManager;
import sg.atom.corex.scenegraph.shape.ShapeUtils;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Plane;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.jme3.scene.shape.Line;
import java.util.HashMap;

/**
 * Working helpers are visual stuff help editing operations. They include :
 *
 * <ul> <li>working plane: indicate surface
 *
 * <li>working point: indicate position <li>working normal: indicate normal of
 * surface
 *
 * <li>working line : indicate alignment
 *
 * <li>working linkage: indicate linkage. </ul>
 *
 * @author hungcuong
 */
public class WorkingHelper extends AbstractHelper {
    // Tool level

    private CommonTool commonTool;
    private ShapeUtils shapeUtil;
    private HelperManager helperManager;
    // SubTool Level
    public HashMap<String, Vector3f> workingPoints;
    public Plane workingPlane;
    public Line workingLine;
    public Line workingLinkageLine;
    private Geometry tempLine;
    //Function
    //FIXME: This should get via CommonTools, HelperManager or EditorContext
    public String nowAction;

    public WorkingHelper(HelperManager helperManagers) {
        super("WorkingHelper");

        this.commonTool = CommonTool.getDefault(null);
        this.shapeUtil = commonTool.getShapeUtil();
        this.helperManager = helperManager;
    }

    public void initHelper() {

        workingPlane = new Plane();
        workingPoints = new HashMap<String, Vector3f>();
        workingPlane.setOriginNormal(new Vector3f(0, 0, 0), Vector3f.UNIT_Y);
        tempLine = new Geometry("tempLine", new Line());

        shapeUtil.putGrid("AutoGrid", commonTool.getRootNode(), new Vector3f(3.5f, 0, 0), ColorRGBA.White, 1f);
    }

    private void doUpdateWorkingPoint(Ray ray) {
        Vector3f wp1 = new Vector3f();
        ray.intersectsWherePlane(workingPlane, wp1);
        System.out.println("Ray hit : " + wp1);

        workingPoints.put("radiusPoint", wp1);
    }

    private void doUpdateWorkingPlaneGrid(CollisionResults results) {
        CollisionResult closest = results.getClosestCollision();
        Vector3f contactPoint = closest.getContactPoint();
        Vector3f contactNormal = closest.getContactNormal();

        // SET AUTO GRID POS
        Geometry grid2 = (Geometry) commonTool.getRootNode().getChild("AutoGrid");
        Vector3f nor1 = contactNormal.normalizeLocal();

        float angle1 = nor1.angleBetween(Vector3f.UNIT_Y);
        Quaternion q = new Quaternion();
        if (angle1 == 0) {
            q.lookAt(Vector3f.UNIT_X, Vector3f.UNIT_Z);
        } else {
            q.lookAt(Vector3f.UNIT_Z, contactNormal);
        }
        q.fromAngleNormalAxis(90 * FastMath.DEG_TO_RAD, nor1);
        grid2.setLocalTranslation(contactPoint);
        grid2.setLocalRotation(q);
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

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        return this;
    }

    public HashMap<String, Vector3f> getWorkingPoints() {
        return workingPoints;
    }

    @Override
    public SpatialList getSelectableList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
