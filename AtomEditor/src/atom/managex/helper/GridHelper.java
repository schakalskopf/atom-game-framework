/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atom.managex.helper;

import atom.corex.spatial.SpatialList;
import atom.corex.common.CommonTool;
import atom.managex.manager.HelperManager;
import atom.corex.shape.GridShape;
import atom.corex.shape.LineShape;
import atom.corex.shape.Shape;
import atom.corex.shape.ShapeUtil;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;

/**
 *
 * @author hungcuong
 */
public class GridHelper extends AbstractHelper {

    // Tool level
    private CommonTool commonTool;
    private ShapeUtil shapeUtil;
    private HelperManager helperManager;
    
    // SubTool Level
    private Node gridPlane;
    private Camera cam;
    private boolean camFix;

    public GridHelper(HelperManager helperManager) {
        super("GridHelper");
        
        this.commonTool = CommonTool.getDefault(null);
        this.shapeUtil = commonTool.getShapeUtil();
        this.helperManager = helperManager;
        this.cam = commonTool.getCurrentCam();
        this.camFix = true;
        
        // SubTool Level Init
        gridPlane = new Node(this.getName());

    }

    public void initHelper() {
        // Make a grid
        int gridSizeNum = 17;
        float gridSizeStep = 0.2f;
        float width = (gridSizeNum - 1) * gridSizeStep / 2;
        
        ((GridShape.Builder) shapeUtil.createShape(Shape.GRID, "Grid", gridPlane))
                .setxSamples(gridSizeNum)
                .setySamples(gridSizeNum)
                .setGridWidth(gridSizeStep)
                .setToCenter(true)
                .setColor(ColorRGBA.DarkGray)
                .setPos(Vector3f.ZERO)
                .build();
        
        ((LineShape.Builder) shapeUtil.createShape(Shape.LINE, "LineX", gridPlane))
                .setAllParam(new Vector3f(width, 0, 0), new Vector3f(-width, 0, 0))
                .setColor(ColorRGBA.Red)
                .setOpacity(1f)
                .build();
        
         ((LineShape.Builder) shapeUtil.createShape(Shape.LINE, "LineX", gridPlane))
                .setAllParam(new Vector3f(0, 0, width), new Vector3f(0, 0, -width))
                .setColor(ColorRGBA.Blue)
                .setOpacity(1f)
                .build();       

        gridPlane.addControl(this);
    }

    @Override
    public Node getSubNode() {
        return gridPlane;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (camFix) {
            Vector3f cursorPos = gridPlane.getWorldTranslation();
            Vector3f campos = cam.getLocation();
            float dis2 = campos.distance(cursorPos);
            float scale = dis2 / 10;
            float customScale = 2.5f;

            gridPlane.setLocalScale(scale * customScale);
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

    @Override
    public SpatialList getSelectableList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
