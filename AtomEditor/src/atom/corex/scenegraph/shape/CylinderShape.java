/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atom.corex.scenegraph.shape;

import atom.corex.scenegraph.shape.Shape;
import atom.corex.scenegraph.shape.ShapeUtil;
import atom.corex.scenegraph.shape.SimpleShape;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;

/**
 *
 * @author hungcuong
 */
public class CylinderShape extends SimpleShape {

    float radius = 2;
    float radius2 = 2;
    int axisSamples = 4, radialSamples = 8;
    float height = 1f;
    
    public static class Builder extends SimpleShape.Builder {

        float radius = 2;
        float radius2 = 2;
        int axisSamples = 4, radialSamples = 8;
        float height = 1f;
        public Builder(String name, Node root, ShapeUtil shapeUtil) {
            super(name, root, shapeUtil);
        }

        @Override
        public Shape build() {
            return new CylinderShape(this);
        }

        public Builder setRadialSamples(int radialSamples) {
            this.radialSamples = radialSamples;
            return this;
        }

        public Builder setRadius(float radius) {
            this.radius = radius;
            return this;
        }
        
        public Builder setRadius2(float radius2) {
            this.radius2 = radius;
            return this;
        }
        
        public Builder setAxisSamples(int axisSamples) {
            this.axisSamples = axisSamples;
            return this;
        }

        public Builder setHeight(float height) {
            this.height = height;
            return this;
        }

        
        public Builder setAllParam(int axisSamples, int radialSamples, float radius, float radius2) {
            this.radius = radius;
            this.radius2 = radius2;
            this.radialSamples = radialSamples;
            this.axisSamples = axisSamples;
            return this;
        }
    }

    public CylinderShape(Builder builder) {
        super(builder);
        this.radius = builder.radius;
        this.radius2 = builder.radius2;
        this.height = builder.height;
        this.radialSamples = builder.radialSamples;
        this.axisSamples = builder.axisSamples;

        this.createShape(builder);
    }

    void createShape(Builder builder) {
        // SHAPE
        Geometry newGeo = new Geometry(this.getName() + "Cylinder", new Cylinder(
                axisSamples,
                radialSamples,
                radius,
                radius2,
                height,
                true,
                false));
        geoList.add(newGeo);
        // MATERIAL
        Material mat = builder.getMaterial().clone();
        newGeo.setMaterial(mat);
        newGeo.setQueueBucket(builder.getBucket());
        
        if (builder.getAttachType()==Shape.GEO){
            root.attachChild(newGeo);
            newGeo.setLocalTranslation(pos);
            newGeo.setLocalScale(scale);
        } else if (builder.getAttachType()==Shape.SHAPE){
            this.attachChild(newGeo);
        }
        
    }
}
