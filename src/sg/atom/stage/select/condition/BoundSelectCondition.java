/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.select.condition;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import sg.atom.stage.select.AbstractSpatialSelectCondition;
import sg.atom.stage.select.ui.SelectRectUI;

/**
 *
 * @author hungcuong
 */
public class BoundSelectCondition extends AbstractSpatialSelectCondition {

    @Override
    public boolean isSelected(Spatial spatial) {
        Vector3f centerPoint = spatial.getWorldTranslation();
        //BoundingVolume bv = entity.getSpatial().getWorldBound();
        //select(entity);
        //if ( checkBoundInCamFrustum(bv,cam2).equals(Camera.FrustumIntersect.Inside)) {
        return true;
    }

    @Override
    public Spatial getDebugShape(AssetManager assetManager) {
        return new Geometry();
    }

    Camera.FrustumIntersect checkBoundInCamFrustum(BoundingVolume bv, Camera cam) {
        int ps = cam.getPlaneState();
        cam.setPlaneState(0);
        int planeState = bv.getCheckPlane();
        bv.setCheckPlane(0);

        Camera.FrustumIntersect result = cam.contains(bv);
        bv.setCheckPlane(planeState);
        return result;
    }

    public void createSphere(AssetManager assetManager, Vector3f loc, Node rootNode) {
        /**
         * A cube with a color "bleeding" through transparent texture. Uses Texture from jme3-test-data library!
         */
        Box boxshape = new Box(Vector3f.ZERO, 0.5f, 0.5f, 0.5f);
        Geometry cube = new Geometry("Cube", boxshape);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Green);
        mat.getAdditionalRenderState().setDepthTest(false);
        cube.setMaterial(mat);
        cube.setLocalTranslation(loc);
        rootNode.attachChild(cube);
    }

    @Override
    public AbstractSpatialSelectCondition createCondition(Camera cam, SelectRectUI selectRectUI) {
        //System.out.println(" CAM : " + cam.getFrustumLeft() + cam.getFrustumRight());
        // make BoundingBox
        float x = selectRectUI.x;
        float y = selectRectUI.y;

        float x1 = selectRectUI.x + selectRectUI.width;
        float y1 = selectRectUI.y + selectRectUI.height;

        float left = (x <= x1) ? (x / cam.getWidth()) : (x1 / cam.getWidth());
        float right = (x > x1) ? (x / cam.getWidth()) : (x1 / cam.getWidth());

        float top = (y <= y1) ? (y / cam.getHeight()) : (y1 / cam.getHeight());
        float bottom = (y > y1) ? (y / cam.getHeight()) : (y1 / cam.getHeight());

        float far = cam.getFrustumFar();
        float near = cam.getFrustumNear() + 30;




        return new BoundSelectCondition();
    }
}
