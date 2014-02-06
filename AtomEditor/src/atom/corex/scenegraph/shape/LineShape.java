/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atom.corex.scenegraph.shape;

import atom.corex.scenegraph.shape.Shape;
import atom.corex.scenegraph.shape.ShapeUtil;
import atom.corex.scenegraph.shape.SimpleShape;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Line;

/**
 *
 * @author hungcuong
 */
public class LineShape extends SimpleShape {

    Vector3f startPoint = Vector3f.ZERO;
    Vector3f endPoint = Vector3f.UNIT_X;
    boolean toCenter;
    float lineWidth=1f;
    
    public static class Builder extends SimpleShape.Builder {

        Vector3f startPoint = Vector3f.ZERO;
        Vector3f endPoint = Vector3f.UNIT_X;
        boolean toCenter;
        float lineWidth=1f;
        
        public Builder(String name, Node root, ShapeUtil shapeUtil) {
            super(name, root, shapeUtil);
        }

        public Builder setStartPoint(Vector3f startPoint) {
            this.startPoint = startPoint;
            return this;
        }

        public Builder setEndPoint(Vector3f endPoint) {
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

        public Vector3f getEndPoint() {
            return endPoint;
        }

        public Vector3f getStartPoint() {
            return startPoint;
        }

        public float getLineWidth() {
            return lineWidth;
        }

        public Builder setLineWidth(float lineWidth) {
            this.lineWidth = lineWidth;
            return this;
        }

        
        
        @Override
        public Shape build() {
            return new LineShape(this);
        }
    }

    LineShape(Builder builder) {
        super(builder);
        this.startPoint = builder.startPoint;
        this.endPoint = builder.endPoint;
        this.lineWidth = builder.lineWidth;
        createShape(builder);
    }

    void createShape(Builder builder) {
        // SHAPE
        Mesh newMesh;
        if (builder.toCenter){
            newMesh = new Line(startPoint, endPoint);
        } else {
            newMesh = new Line(startPoint, endPoint);
        }
        newMesh.setLineWidth(lineWidth);
        
        Geometry newGeo = new Geometry(this.getName() + "Box", newMesh);
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
    }
}
