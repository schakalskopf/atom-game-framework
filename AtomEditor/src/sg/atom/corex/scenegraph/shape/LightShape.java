/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.scenegraph.shape;

import sg.atom.corex.scenegraph.shape.Shape;
import sg.atom.corex.scenegraph.shape.ShapeUtils;
import sg.atom.corex.scenegraph.shape.SimpleShape;
import sg.atom.corex.scenegraph.shape.SphereShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author hungcuong
 */
public class LightShape extends SimpleShape {

    float radius = 1;
    float radius2 = 1.5f;
    int axisSamples = 4, radialSamples = 8, zSamples = 8;
    float height = 2f;
    float height2 = 3f;
    ColorRGBA lightColor=ColorRGBA.Yellow;

    public static class Builder extends SimpleShape.Builder {

        float radius = 1;
        float radius2 = 1.5f;
        int axisSamples = 4, radialSamples = 8, zSamples = 8;
        float height = 2f;
        float height2 = 3f;
        ColorRGBA lightColor=ColorRGBA.Yellow;

        public Builder(String name, Node root, ShapeUtils shapeUtil) {
            super(name, root, shapeUtil);
        }

        @Override
        public Shape build() {
            return new LightShape(this);
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
            this.radius2 = radius2;
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

        public Builder setHeight2(float height) {
            this.height2 = height;
            return this;
        }

        public Builder setAllParam(int axisSamples, int radialSamples, float radius, float radius2) {
            this.radius = radius;
            this.radius2 = radius2;
            this.radialSamples = radialSamples;
            this.axisSamples = axisSamples;
            return this;
        }

        public Builder setLightColor(ColorRGBA color) {
            this.lightColor = color;
            return this;
        }
    }

    LightShape(Builder builder) {
        super(builder);
        this.radius = builder.radius;
        this.radius2 = builder.radius2;
        this.height = builder.height;
        this.height2 = builder.height2;
        this.zSamples = builder.zSamples;
        this.radialSamples = builder.radialSamples;
        this.axisSamples = builder.axisSamples;
        this.lightColor = builder.lightColor;
        height2 = (float) (height + Math.sqrt(radius2 * radius2 - (radius * radius)));
        this.createShape(builder);
    }
  
    void createShape(Builder builder){
        //SHAPE
        
         SimpleShape lightBulb=(SimpleShape)
                 ((CylinderShape.Builder)this.shapeUtil.createShape(Shape.CYLINDER, this.getName()+"LightBulb", this))
                .setRadius(radius)
                .setRadius2(radius)
                .setHeight(height)
                .setAxisSamples(axisSamples)
                .setRadialSamples(radialSamples)
                .setMaterial(this.material)
                .setWired(wired)
                .setOpacity(0.9f)
                .setPos(0,0,height / 2)
                .setColor(ColorRGBA.Gray)
                .setAttachType(Shape.GEO)
                .build();   
        geoList.addAll(lightBulb.getGeoList());
        
        SimpleShape lightSphere=(SimpleShape)
        ((SphereShape.Builder)this.shapeUtil.createShape(Shape.SPHERE, this.getName()+"LightSphere", this))
                .setRadius(radius2)
                .setzSamples(zSamples)
                .setRadialSamples(radialSamples)
                .setMaterial(this.material)
                .setWired(wired)
                .setOpacity(0.5f)
                .setPos(0,0,height2)
                .setColor(lightColor)
                .setAttachType(Shape.GEO)
                .build();
        geoList.addAll(lightSphere.getGeoList());
        
        //System.out.println("Height" + height);
        //System.out.println("Height2"+height2);
    }
}