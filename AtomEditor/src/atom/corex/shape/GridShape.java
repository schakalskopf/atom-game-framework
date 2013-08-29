/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atom.corex.shape;

import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Grid;

/**
 *
 * @author hungcuong
 */
public class GridShape extends SimpleShape {

    Vector3f startPoint = Vector3f.ZERO;
    Vector3f endPoint = Vector3f.UNIT_X;
    Vector3f sidePoint = new Vector3f(1, 1, 0);
    boolean toCenter = true;
    int xSamples = 5, ySamples = 5;
    float gridWidth = 0.2f;

    public static class Builder extends SimpleShape.Builder {

        Vector3f startPoint = Vector3f.ZERO;
        Vector3f endPoint = Vector3f.UNIT_X;
        Vector3f sidePoint = new Vector3f(1, 1, 0);
        boolean toCenter = true;
        int xSamples = 5, ySamples = 5;
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
            return new GridShape(this);
        }
    }

    GridShape(Builder builder) {
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
        Mesh newMesh;
        newMesh = new Grid(xSamples, ySamples, gridWidth);

        Geometry newGeo = new Geometry(this.getName() + "Grid", newMesh);

        geoList.add(newGeo);

        // MATERIAL
        Material mat = builder.getMaterial().clone();
        newGeo.setMaterial(mat);
        newGeo.setQueueBucket(builder.getBucket());

        if (builder.getAttachType() == Shape.GEO) {
            root.attachChild(newGeo);
            newGeo.setLocalTranslation(pos);
            newGeo.setLocalScale(scale);
        } else if (builder.getAttachType() == Shape.SHAPE) {
            this.attachChild(newGeo);
        }
        
        if (builder.toCenter) {
            newGeo.center();
            newGeo.move(pos);
        } else {
        }
    }
}