/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atom.corex.scenegraph.shape;

import atom.corex.scenegraph.shape.SphereShape;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import sg.atom.utils.factory.AtomFactory;

/**
 *
 * @author hungcuong
 */
public class ShapeUtil implements AtomFactory<Shape> {
    
    private static ShapeUtil singletonObject;
    //private final CommonTool commonTool;
    private int opacityLayer = 0;
    
    public ShapeUtil() {
        //this.commonTool = CommonTool.getDefault(null);
    }
    
    public static synchronized ShapeUtil getDefault() {
        if (singletonObject == null) {
            singletonObject = new ShapeUtil();
        }
        return singletonObject;
    }
    
    public int getOpacityLayer() {
        return opacityLayer;
    }
    
    public void setOpacityLayer(int opacityLayer) {
        this.opacityLayer = opacityLayer;
    }
    
    public ShapeUtilBuilder createShape(int type, String shapeName, Node root) {
        switch (type) {
            case Shape.BOX:
                return new BoxShape.Builder(shapeName, root, this);
            case Shape.SPHERE:
                return new SphereShape.Builder(shapeName, root, this);
            case Shape.CYLINDER:
                return new CylinderShape.Builder(shapeName, root, this);
            case Shape.ARROW:
                return new ArrowShape.Builder(shapeName, root, this);
            case Shape.LIGHT:
                return new LightShape.Builder(shapeName, root, this);
            case Shape.LINE:
                return new LineShape.Builder(shapeName, root, this);
            case Shape.GRID:
                return new GridShape.Builder(shapeName, root, this);
            case Shape.GRIDHELPER:
                return new GridHelperShape.Builder(shapeName, root, this);
        }
        return null;
        
    }
    
    public void putGrid(String autoGrid, Node rootNode, Vector3f vector3f, ColorRGBA White, float f) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public Shape create(Object param) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Shape create(Object... params) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Shape cloneObject(Shape orginal) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
