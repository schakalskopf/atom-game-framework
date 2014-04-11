/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.editor.managers;

import sg.atom.corex.scenegraph.spatial.SpatialList;
import sg.atom.corex.common.CommonTool;
import sg.atom.editor.helpers.GizmoHelper;
import com.jme3.collision.CollisionResults;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import java.util.Properties;
import sg.atom.core.AbstractManager;

/**
 * S
 *
 * @author hungcuong
 */
public class EditorSelectionManager extends AbstractManager {

    private CommonTool commonTool;
    
    // Select methods
    private Node shootables;
    private CollisionResults results;
    private Ray ray;
    
    // List of selectable objects
    private SpatialList listSelectedSpatial = new SpatialList();
    private SpatialList listGeo;
    private SpatialList listHelper;
    private Geometry currentSelectedObject;
    private Geometry overGeo;
    
    // List of helper to exclude
    private GizmoHelper gizmoHelper;

    public EditorSelectionManager() {
        this.commonTool = CommonTool.getDefault(null);
        //initSelectableList();
    }

    private void initSelectableList() {
        shootables = commonTool.getRootNode();
        //shootables = new Node("Shootables");
        //commonTool.getRootNode().attachChild(shootables);
        //shootables.attachChild(commonTool.getRootNode().getChild(null));


        listHelper = gizmoHelper.getShootableList();
        /*
         for (int i = 0; i < listGeo.size(); i++) {
         shootables.attachChild(commonTool.getRootNode().getChild(listGeo[i]));
         }
         for (int i = 0; i < listHelper.size(); i++) {
         System.out.println(listHelper[i]);
         shootables.attachChild(gizmoHelper.getSubNode().getChild(listHelper[i]));
         }
         */

    }

    private void doHighlightHelper(Geometry obj, boolean highlight) {
        if (obj != null) {
            if (highlight) {
                // IF highlight=TRUE
                if (isHelper(obj)) {
                    if (!obj.getName().startsWith("Arrow")) {
                        //obj.setLocalScale(1.2f);
                    } else {
                    }
                    obj.getMaterial().getAdditionalRenderState().setBlendMode(BlendMode.Additive);
                } else {
                }
            } else {
                // IF highlight=FALSE

                obj.getMaterial().getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
            }
        }
    }

    private void doHighlightObject(Geometry obj, boolean highlight) {
        if (isSelectable(obj)) {
            if (highlight) {
                obj.getMaterial().getAdditionalRenderState().setBlendMode(BlendMode.Additive);
            } else {
                obj.getMaterial().getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
            }
        }
    }

    private void deHighLight() {
        if (overGeo != null) {
            if (isSelectable(overGeo)) {

                doHighlightHelper(overGeo, false);
            } else if (isHelper(overGeo)) {
                doHighlightHelper(overGeo, false);
            }
        }
    }

    private void doShootCheck() {

        //
        Vector2f cursorPos2D = commonTool.getInputManager().getCursorPosition();
        Camera currentCam = commonTool.getCurrentCam();
        Vector3f origin = currentCam.getWorldCoordinates(cursorPos2D, 0.0f);
        Vector3f direction = currentCam.getWorldCoordinates(cursorPos2D, 0.3f);
        direction.subtractLocal(origin).normalizeLocal();

        ray = new Ray(origin, direction);
        results = new CollisionResults();
        shootables.collideWith(ray, results);

    }

    int inList(String str, String[] list) {
        for (int i = 0; i < list.length; i++) {
            if (str.equals(list[i])) {
                return i;
            }
        }
        return -1;
    }

    public void doOver() {
        doShootCheck();

        // HIGHLIGHT OVER HELPER
        if (results.size() > 0) {

            Geometry hitObj = results.getClosestCollision().getGeometry();
            if (hitObj != overGeo) {
                deHighLight();
            }
            if (isHighlightable(hitObj)) {
                if (isSelectable(hitObj)) {
                } else if (isHelper(hitObj)) {
                    doHighlightHelper(hitObj, true);
                }
            } else {
            }
            overGeo = hitObj;
        } else {
            deHighLight();
        }
    }

    public void doListHitByRay() {
        // LIST THE HIT OBJECTS
        System.out.println("----- Collisions? " + results.size() + "-----");
        if (results.size() > 0) {
            for (int i = 0; i < results.size(); i++) {

                // For each hit, we know distance, impact point, name of geometry.
                float dist = results.getCollision(i).getDistance();
                Vector3f pt = results.getCollision(i).getContactPoint();
                String hit = results.getCollision(i).getGeometry().getName();

                System.out.println("* Collision #" + i);
                System.out.println("  You shot " + hit + " at " + pt + ", " + dist + " wu away.");
            }
        }
    }

    public void doClickHit() {
        doShootCheck();
        doListHitByRay();
        if (results.size() > 0) {
            Geometry hitObj = results.getClosestCollision().getGeometry();
            if (isHighlightable(hitObj)) {
                if (isSelectable(hitObj)) {
                    deSelectCurrentObject();
                    doSelectObject(hitObj);
                } else if (isHelper(hitObj)) {
                    deSelectCurrentHelper();
                    doSelectHelper(hitObj);
                }
            } else {
                deSelectCurrentObject();
                deSelectCurrentHelper();
            }
        }
    }

    private boolean isHighlightable(Geometry who) {
        if ((inList(who.getName(), listHelper) != -1) || (inList(who.getName(), listGeo) != -1)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isHelper(Geometry who) {
        if ((inList(who.getName(), listHelper) != -1)) {
            return true;

        } else {
            return false;
        }
    }

    private boolean isSelectable(Geometry who) {
        if ((inList(who.getName(), listGeo) != -1)) {
            return true;
        } else {
            return false;
        }
    }

    // IMPLEMENT actionListener
    public void onAction(String name, boolean keyPressed, float tpf) {
        if (name.equals("Shoot") && !keyPressed) {
            doClickHit();
            //doClickMarkObject();
            //doClickAction();

        }
    }

    // CURRENT SELECTED OBJECTs ================================================
    public Geometry getCurrentSelectedObject() {
        return currentSelectedObject;
    }

    public CollisionResults getCurrentShootResult() {
        return results;
    }
    // DO select or deselect

    int doSelectHelper(Geometry helper1) {
        deSelectCurrentHelper();

        return 0;
    }

    int doSelectObject(Geometry hitObj) {
        currentSelectedObject = hitObj;
        System.out.println("You select : " + currentSelectedObject.getName());
        return 0;
    }

    void deSelectCurrentHelper() {
    }

    void deSelectCurrentObject() {
    }

    private int inList(String name, SpatialList listHelper) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

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
