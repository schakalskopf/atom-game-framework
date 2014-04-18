/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.scenegraph.shape;

import sg.atom.corex.scenegraph.shape.Shape;
import sg.atom.corex.scenegraph.shape.ShapeUtils;
import sg.atom.corex.scenegraph.shape.SimpleShape;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 *
 * @author hungcuong
 */
public class BoxShape extends SimpleShape {

    public static class Builder extends SimpleShape.Builder {


        public Builder(String name, Node root, ShapeUtils shapeUtil) {
            super(name, root, shapeUtil);
        }

        public Builder setAllSize(Vector3f allSize) {
            this.allSize = allSize;
            return this;
        }
        public Builder setAllSize(float width, float height, float length)  {
            this.width = width;
            this.height = height;
            this.length = length;
            return this;
        }
        public Builder setHeight(float height) {
            this.height = height;
            return this;
        }

        public Builder setLength(float length) {
            this.length = length;
            return this;
        }

        public Builder setWidth(float width) {
            this.width = width;
            return this;
        }
        
        

        @Override
        public Shape build() {
            return new BoxShape(this);
        }
    }

    public BoxShape(Builder builder) {
        super(builder);
        createShape(builder);
    }
    
    void createShape(Builder builder) {
        // SHAPE
        Geometry newGeo = new Geometry(this.getName() + "Box", new Box(width, height, length));
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
