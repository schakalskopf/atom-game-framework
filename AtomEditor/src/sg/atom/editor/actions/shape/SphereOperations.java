/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.editor.actions.shape;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Line;
import com.jme3.scene.shape.Sphere;
import java.util.HashMap;
import sg.atom.corex.common.CommonTool;
import sg.atom.corex.scenegraph.shape.ShapeUtil;
import sg.atom.editor.managers.HelperManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class SphereOperations {
    // Tool level

    private CommonTool commonTool;
    private ShapeUtil shapeUtil;
    private HelperManager helperManager;
    // SubTool Level
    private String nowAction;
    private Geometry tempLine;
    private Geometry tempShape;
    private float lastUpdateTime;
    private HashMap<String, Vector3f> workingPoints;
    private Node helperNode;
    private Node targetNode;

    public void doSphereRadiusMark(Vector3f loc, Vector3f rp) {
        //float oldRadius = ((Sphere) tempShape.getMesh()).radius;
        Vector3f helperPos = helperManager.helperNode.getLocalTranslation();
        Line mLine = new Line(loc.subtract(helperPos), rp.subtract(helperPos));
        tempLine.setMesh(mLine);

    }

    public Geometry doSphereRadius(Geometry tempSphere, float radius) {
        float oldRadius = ((Sphere) tempSphere.getMesh()).radius;
        tempSphere.setLocalScale(radius / oldRadius);
        return tempSphere;
    }

    public Geometry doSphereCreate(String geoName, Vector3f loc) {
        float radius = 1f;


        Material mat = new Material(commonTool.getAssetManager(), "Unshaded.j3md");
        //mat.getAdditionalRenderState().setWireframe(true);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);  // activate transparency
        mat.setColor("Color", ColorRGBA.White);
        mat.setFloat("Opacity", 1f);

        tempLine.setMaterial(mat);
        helperManager.helperNode.attachChild(tempLine);

        return drawSphere(geoName + "_temp", commonTool.getRootNode(), loc, radius, ColorRGBA.Brown, 0.8f);
    }

    public Geometry drawSphere(String geoName, Node root, Vector3f loc, float radius, ColorRGBA color, float opacity) {
        int zSamples = 16;
        int radialSamples = 16;

        Sphere tSphere = new Sphere(zSamples, radialSamples, radius);
        Geometry tempSphere = new Geometry(geoName, tSphere);


        Material mat = new Material(commonTool.getAssetManager(), "Unshaded.j3md");
        //mat.getAdditionalRenderState().setWireframe(true);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);  // activate transparency
        mat.setColor("Color", color);
        mat.setFloat("Opacity", opacity);

        tempSphere.setMaterial(mat);

        helperManager.helperNode.attachChild(tempSphere);
        commonTool.getRootNode().attachChild(helperManager.helperNode);

        helperManager.helperNode.setLocalTranslation(loc);
        return tempSphere;
    }

    public void doShapeOperation() {
        // Do the shape create
        if (helperManager.nowAction.equals("Select")) {
        }
        if (helperManager.nowAction.equals("Sphere.Create")) {
        } else if (helperManager.nowAction.equals("Sphere.Create.Radius")) {
            float nowTime = System.nanoTime();
            if (nowTime - lastUpdateTime > 20) {


                if (tempShape != null) {
                    Vector3f loc = (Vector3f) workingPoints.get("origin");
                    Vector3f rp = (Vector3f) workingPoints.get("radiusPoint");

                    float radius = rp.distance(loc);
                    System.out.println("Radius : " + radius);
                    doSphereRadiusMark(loc, rp);
                    doSphereRadius(tempShape, radius + 0.1f);
                }
                lastUpdateTime = nowTime;
            }
        }
    }
}
