/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atom.corex.shape;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author hungcuong
 */
public class GridHelperShape extends SimpleShape {

    Vector3f startPoint = Vector3f.ZERO;
    Vector3f endPoint = Vector3f.UNIT_X;
    Vector3f sidePoint = new Vector3f(1, 1, 0);
    boolean toCenter = true;
    int xSamples = 17, ySamples = 17;
    float gridWidth = 0.2f;

    public static class Builder extends SimpleShape.Builder {

        Vector3f startPoint = Vector3f.ZERO;
        Vector3f endPoint = Vector3f.UNIT_X;
        Vector3f sidePoint = new Vector3f(1, 1, 0);
        boolean toCenter = true;
        int xSamples = 17, ySamples = 17;
        float gridWidth = 0.2f;

        Builder(String name, Node root, ShapeUtil shapeUtil) {
            super(name, root, shapeUtil);
        }

        public Builder setStartPoint(Vector3f startPoint) {
            this.startPoint = startPoint;
            return this;
        }

        public Builder setEndPoint(Vector3f end) {
            this.endPoint = endPoint;
            return this;
        }

        public Builder setAllParam(Vector3f startPoint, Vector3f endPoint) {
            this.startPoint = startPoint;
            this.endPoint = endPoint;
            return this;
        }

        public Builder setToCenter(boolean toCenter) {
            this.toCenter = toCenter;
            return this;
        }

        public float getGridWidth() {
            return gridWidth;
        }

        public Builder setGridWidth(float gridWidth) {
            this.gridWidth = gridWidth;
            return this;
        }

        public boolean isToCenter() {
            return toCenter;
        }

        public int getxSamples() {
            return xSamples;
        }

        public Builder setxSamples(int xSamples) {
            this.xSamples = xSamples;
            return this;
        }

        public int getySamples() {
            return ySamples;
        }

        public Builder setySamples(int ySamples) {
            this.ySamples = ySamples;
            return this;
        }

        public Vector3f getEndPoint() {
            return endPoint;
        }

        public Vector3f getStartPoint() {
            return startPoint;
        }

        @Override
        public Shape build() {
            return new GridHelperShape(this);
        }
    }

    GridHelperShape(Builder builder) {
        super(builder);
        this.startPoint = builder.startPoint;
        this.endPoint = builder.endPoint;
        this.sidePoint = builder.sidePoint;
        this.xSamples = builder.xSamples;
        this.ySamples = builder.ySamples;
        this.gridWidth = builder.gridWidth;

        if ((startPoint.distance(sidePoint) - gridWidth * xSamples < 0.01f)
                && (endPoint.distance(sidePoint) - gridWidth * ySamples < 0.01f)) {
        } else {
        }
        createShape(builder);
    }

    void createShape(Builder builder) {
        // SHAPE
        
         SimpleShape gridShape=(SimpleShape)
        ((GridShape.Builder) shapeUtil.createShape(Shape.GRID, this.getName()+"GridPlane", this))
                .setGridWidth(gridWidth)
                .setToCenter(toCenter)
                .setxSamples(xSamples)
                .setySamples(ySamples)
                .setColor(color)
                .setPos(0,0,0)
                .setAttachType(Shape.GEO)
                .build();   
        geoList.addAll(gridShape.getGeoList());
        
        float halfWidth = (xSamples - 1) * gridWidth / 2;
        
        SimpleShape lineShapeX=(SimpleShape)
        ((LineShape.Builder) shapeUtil.createShape(Shape.LINE, this.getName()+"GridLineX", this))
                .setAllParam(new Vector3f(halfWidth, 0, 0), new Vector3f(-halfWidth, 0, 0))
                .setLineWidth(2)
                .setColor(ColorRGBA.Red)
                .setPos(0,0,0)
                .setAttachType(Shape.GEO)
                .build();               
        geoList.addAll(lineShapeX.getGeoList());
        
       SimpleShape lineShapeY=(SimpleShape)
        ((LineShape.Builder) shapeUtil.createShape(Shape.LINE, this.getName()+"GridLineY", this))
                .setAllParam(new Vector3f(0, 0, halfWidth), new Vector3f(0, 0, -halfWidth))
               .setLineWidth(2)
                .setColor(ColorRGBA.Blue)
                .setPos(0,0,0)
                .setAttachType(Shape.GEO)
                .build();               
        geoList.addAll(lineShapeY.getGeoList());
    }
}
