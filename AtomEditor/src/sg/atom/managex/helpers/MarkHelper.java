/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.helpers;

import sg.atom.corex.scenegraph.spatial.SpatialList;
import sg.atom.corex.common.CommonTool;
import sg.atom.managex.managers.HelperManager;
import sg.atom.corex.scenegraph.shape.ShapeUtil;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author hungcuong
 */
public class MarkHelper extends AbstractHelper {
    // Tool level
    private CommonTool commonTool;
    private ShapeUtil shapeUtil;
    private HelperManager helperManager;
    
    // SubTool Level
    public Geometry mark, mark2, markObject, markObject2;

    public Geometry getMarkObject() {
        return markObject;
    }

    public void setMarkObject(Geometry markObj) {
        this.markObject = markObject;
    }


    public MarkHelper(HelperManager helperManager) {
        super("MarkHelper");
        
        this.commonTool = CommonTool.getDefault(null);
        this.shapeUtil = commonTool.getShapeUtil();
        this.helperManager = helperManager;
        
    }

    private void doMarkUpdate() {
        CollisionResults results = helperManager.getCurrentShootResult();
        CollisionResult closest = results.getClosestCollision();
        Vector3f contactPoint = closest.getContactPoint();
        Vector3f contactNormal = closest.getContactNormal();

        // SET MARK POS

        Quaternion q = new Quaternion();
        q.lookAt(contactNormal, Vector3f.UNIT_Y);
        mark.setLocalRotation(q);
        mark.setLocalTranslation(contactPoint);
    }

    @Override
    public void initHelper() {
        Arrow arrow = new Arrow(Vector3f.UNIT_Z.mult(0.2f));
        arrow.setLineWidth(3);

        Sphere sphere = new Sphere(10, 10, 0.02f);
        mark = new Geometry("BOOM!", arrow);
        mark2 = new Geometry("BOOM2!", sphere);

        Material mark_mat = new Material(commonTool.getAssetManager(), "SolidColor2.j3md");
        mark_mat.setColor("Color", ColorRGBA.Red);
        mark.setMaterial(mark_mat);
        mark2.setMaterial(mark_mat);
    }

    public void setMarkLocation(Vector3f loc) {
        mark2.setLocalTranslation(loc);
    }

    public void setMarkVisible(boolean visible) {
        if (visible == true) {
            commonTool.getRootNode().attachChild(mark2);
        } else {
            commonTool.getRootNode().detachChild(mark2);
        }
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
