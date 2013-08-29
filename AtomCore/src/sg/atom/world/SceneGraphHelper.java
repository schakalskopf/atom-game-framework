package sg.atom.world;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.jme3.terrain.geomipmap.TerrainQuad;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author hungcuong
 */
public class SceneGraphHelper {

    public static Spatial travelUpFindControl(Spatial sp, Class<? extends Control> aClass) {
        if (sp.getControl(aClass) == null) {
            if (sp.getParent() != null) {
                return travelUpFindControl(sp.getParent(), aClass);
            } else {
                return null;
            }
        } else {
            return sp;
        }
    }
    static Control controlResult = null;

    public static <T extends Control> T travelDownFindFirstControl(Spatial sp, final Class<T> aClass) {
        controlResult = null;
        sp.breadthFirstTraversal(new SceneGraphVisitor() {

            @Override
            public void visit(Spatial spatial) {
                if (spatial.getControl(aClass) != null) {
                    controlResult = spatial.getControl(aClass);
                }
            }
        });

        return (T) controlResult;

    }

    public static Geometry getFirstGeo(Spatial sp) {
        if (sp instanceof Geometry) {
            return (Geometry) sp;
        } else {
            for (Spatial child : ((Node) sp).getChildren()) {
                if (!(child instanceof Geometry)) {
                    continue; // could be an attachment node, ignore.
                }

                Geometry geom = (Geometry) child;

                return geom;
            }
        }
        return null;
    }

    public static List<Geometry> findGeometries(Node node, List<Geometry> geoms) {
        for (Iterator<Spatial> it = node.getChildren().iterator(); it.hasNext();) {
            Spatial spatial = it.next();
            if (spatial instanceof Geometry) {
                geoms.add((Geometry) spatial);
            } else if (spatial instanceof Node) {
                findGeometries((Node) spatial, geoms);
            }
        }
        return geoms;
    }
    static Spatial result = null;

    public static Spatial findSpatialByName(final String name, Node parent, final boolean useReg) {

        parent.breadthFirstTraversal(new SceneGraphVisitor() {

            public void visit(Spatial spatial) {
                if (useReg) {
                    if (spatial.getName().matches(name)) {
                        result = spatial;
                        return;
                    }
                } else {
                    if (spatial.getName().equals(name)) {
                        result = spatial;
                        return;
                    }
                }
            }
        });
        return result;

    }

    public static Geometry getGeoByName(String name, Node parent) {

        for (Spatial sp : parent.getChildren()) {
            if (sp instanceof Geometry) {
                if (sp.getName().equals(name)) {
                    return (Geometry) sp;
                }
            }
        }

        for (Spatial sp : parent.getChildren()) {
            if (sp instanceof Node) {
                Geometry result = getGeoByName(name, (Node) sp);
                if (result != null) {
                    return (Geometry) result;
                }
            }
        }
        return null;


    }

    public static Spatial getSpatialByPath(String path, Node searchNode) {
        String name[] = path.split("/");
        Node currentNode = searchNode;
        Spatial result;
        int i = 0;
        for (String currentName : name) {
            i++;
            result = currentNode.getChild(currentName);
            if (result == null) {
                // this current node don't have a child has the current name!
                return null;
            } else {
                if (i == name.length) {
                    return result;
                } else {
                    if (result instanceof Node) {
                        currentNode = (Node) result;
                    } else {
                        // Path inside a Geometry , WRONG!
                        return null;
                    }
                }
            }

        }
        return null;
    }

    public static TerrainQuad findTerrain(Node node) {
        for (Spatial child : node.getChildren()) {
            if (child instanceof TerrainQuad) {
                return (TerrainQuad) child;
            } else if (child instanceof Node) {
                return findTerrain((Node) child);
            }

        }
        return null;
    }
}