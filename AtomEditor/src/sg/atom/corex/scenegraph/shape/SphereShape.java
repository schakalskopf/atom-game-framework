/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.scenegraph.shape;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author hungcuong
 */
public class SphereShape extends SimpleShape {

    float radius = 0;
    int zSamples = 8, radialSamples = 8;

    public static class Builder extends SimpleShape.Builder {

        float radius = 0;
        int zSamples = 8, radialSamples = 8;

        public Builder(String name, Node root, ShapeUtils shapeUtil) {
            super(name, root, shapeUtil);
        }

        @Override
        public Shape build() {
            return new SphereShape(this);
        }

        public Builder setRadialSamples(int radialSamples) {
            this.radialSamples = radialSamples;
            return this;
        }

        public Builder setRadius(float radius) {
            this.radius = radius;
            return this;
        }

        public Builder setzSamples(int zSamples) {
            this.zSamples = zSamples;
            return this;
        }

        public Builder setAllParam(int zSamples, int radialSamples, float radius) {
            this.radius = radius;
            this.radialSamples = radialSamples;
            this.zSamples = zSamples;
            return this;
        }
    }

    public SphereShape(Builder builder) {
        super(builder);
        this.radius = builder.radius;
        this.radialSamples = builder.radialSamples;
        this.zSamples = builder.zSamples;

        this.createShape(builder);
    }

    void createShape(Builder builder) {
        // SHAPE
        Geometry newGeo = new Geometry(this.getName() + "Sphere", new Sphere(zSamples, radialSamples, radius));
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