/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.select.control;

import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingSphere;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Torus;
import java.util.ArrayList;
import sg.atom.stage.WorldManager;
import sg.atom.stage.select.SpatialSelectControl;

/**
 *
 * @author hungcuong
 */
public class CircleSelectMark extends SpatialSelectControl {
    
    Material savedMat;
    Spatial selectCircle;
    Spatial hoverMark;
    private ArrayList<Spatial> arrows = new ArrayList<Spatial>();
    private Material greenMat;
    
    public CircleSelectMark(WorldManager worldManager) {
        greenMat = new Material(worldManager.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        greenMat.setColor("Color", ColorRGBA.Green);
        greenMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        greenMat.getAdditionalRenderState().setDepthWrite(false);
        greenMat.getAdditionalRenderState().setDepthTest(false);
    }
    
    public Spatial createSelectCircle() {
        float radius = 16f;
        Vector3f center = Vector3f.UNIT_Y.clone().mult(10f);
        Node selectCircle = new Node("selectCircle");
        
        Torus torus = new Torus(32, 4, 0.2f, radius);
        Geometry c1 = new Geometry("c1", torus);
        Quaternion quat = new Quaternion();
        quat.fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_X);
        c1.setLocalRotation(quat);
        c1.setMaterial(greenMat);
        c1.setLocalTranslation(center);
        
        Torus torus2 = new Torus(4, 4, 1f, 1f);
        c1.setQueueBucket(RenderQueue.Bucket.Transparent);
        c1.setShadowMode(RenderQueue.ShadowMode.Off);
        for (int i = 0; i < 4; i++) {
            Geometry c2 = new Geometry("c" + i, torus2);
            c2.setMaterial(greenMat);
            arrows.add(c2);
            selectCircle.attachChild(c2);
            c2.setQueueBucket(RenderQueue.Bucket.Transparent);
            c2.setShadowMode(RenderQueue.ShadowMode.Off);
        }
        selectCircle.attachChild(c1);
        
        updateArrowPos(center, radius);
        return selectCircle;
    }
    
    public void updateArrowPos(Vector3f center, float radius) {
        
        Vector3f radiusVec = Vector3f.UNIT_X.clone().mult(radius);
        for (int i = 0; i < arrows.size(); i++) {
            Quaternion quat2 = new Quaternion();
            quat2.fromAngles(0, FastMath.HALF_PI * i, 0);
            arrows.get(i).setLocalTranslation(center.add(quat2.mult(radiusVec)));
        }
    }
    
    public void rotateAroundCenter(Vector3f center, Quaternion quat) {
        //quat.
    }
    
    public void addSelectCircle() {
        Node model = (Node) this.getSpatial();
        float size = 1;
        if (model.getWorldBound() instanceof BoundingBox) {
            size = ((BoundingBox) model.getWorldBound()).getXExtent();
            //System.out.println(" BoundingBox: " + size);
        } else if (model.getWorldBound() instanceof BoundingSphere) {
            size = ((BoundingSphere) model.getWorldBound()).getRadius();
            //System.out.println(" BoundingSphere: " + size);
        }
        selectCircle.setLocalScale(size / 16f * 1.2f);
        model.attachChild(selectCircle);
        
    }
    
    @Override
    protected void doSelected() {
        if (selectCircle == null) {
            selectCircle = createSelectCircle();
        } else {
        }
        addSelectCircle();
        
    }
    
    @Override
    protected void doDeselected() {
        
        Node model = (Node) this.getSpatial();
        if (selectCircle != null) {
            //System.out.println(" Detached Circle");
            model.detachChild(selectCircle);
            //selectCircle = null;
        }
    }
    
    @Override
    protected void doHovered() {
        if (hoverMark == null) {
            hoverMark = createHoverMark();
        } else {
        }
        addHoverMark();
    }
    
    @Override
    protected void doOutHovered() {
        
        Node model = (Node) this.getSpatial();
        if (hoverMark != null) {
            model.detachChild(hoverMark);
            hoverMark = null;
        }
    }
    
    private void addHoverMark() {
        Node model = (Node) this.getSpatial();
        float size = 1;
        if (model.getWorldBound() instanceof BoundingBox) {
            size = ((BoundingBox) model.getWorldBound()).getXExtent();
            //System.out.println(" BoundingBox: " + size);
        } else if (model.getWorldBound() instanceof BoundingSphere) {
            size = ((BoundingSphere) model.getWorldBound()).getRadius();
            //System.out.println(" BoundingSphere: " + size);
        }
        hoverMark.setLocalScale(size / 16f * 1.2f);
        model.attachChild(hoverMark);
    }
    
    private Spatial createHoverMark() {
        Node newNode = new Node("hoverMark");
        Sphere sp = new Sphere(8, 8, 1);
        Geometry c1 = new Geometry("hoverMarkGeo", sp);
        c1.setMaterial(greenMat);
        newNode.attachChild(c1);
        return newNode;
        
    }
}
