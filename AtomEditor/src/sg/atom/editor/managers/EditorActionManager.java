/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.editor.managers;

import com.jme3.input.controls.ActionListener;
import java.util.Properties;
import sg.atom.core.AbstractManager;

/**
 *
 * @author hungcuong
 */
public class EditorActionManager extends AbstractManager implements ActionListener {

    public void onAction(String name, boolean isPressed, float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*
     public void doClickAction() {
     if (results.size() > 0) {
     commonTool.getCamUtil().setFlyByCam(true);
     } else {


     // Do the shape create
     if (nowAction.equals("Sphere")) {
     commonTool.getCamUtil().setFlyByCam(true);
     nowAction = "Sphere.Create.Radius";

     Vector3f wp1 = new Vector3f();
     ray.intersectsWherePlane(workingHelper.workingPlane, wp1);
     System.out.println("Ray hit : " + wp1);
     workingHelper.workingPoints.put("origin", wp1);

     Vector3f loc = (Vector3f) workingHelper.workingPoints.get("origin");
     shapeHelper.doSphereCreate("sphere1", loc);

     System.out.println("Create a sphere name  : " + "sphere1");

     } else if (nowAction.equals("Sphere.Create.Radius")) {
     nowAction = "Sphere.Create.Finish";
     } else if (nowAction.equals("Sphere.Create.Finish")) {

     workingHelper.workingPoints.clear();
     nowAction = "Select";
     commonTool.getCamUtil().setFlyByCam(false);

     shapeHelper.doFinishShapeCreate();
     }
     System.out.println("Now action : " + nowAction);
     }
     }
     * 
     */
    public void init() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void config(Properties props) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void update(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void finish() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public LifeCyclePhase getCurrentPhase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public float getProgressPercent(LifeCyclePhase aPhrase) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
