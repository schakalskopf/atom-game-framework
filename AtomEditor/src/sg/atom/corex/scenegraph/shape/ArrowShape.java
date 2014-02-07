/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.scenegraph.shape;

import sg.atom.corex.scenegraph.shape.Shape;
import sg.atom.corex.scenegraph.shape.ShapeUtil;
import sg.atom.corex.scenegraph.shape.SimpleShape;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.Arrow;

/**
 *
 * @author hungcuong
 */
public class ArrowShape extends SimpleShape {

    Vector3f dir = Vector3f.UNIT_X;
    float lineWidth=1;
    private Vector3f arrowExtent=new Vector3f(1, 1, 1);;
    
    public static class Builder extends SimpleShape.Builder {

        Vector3f dir = Vector3f.UNIT_X;
        private Vector3f target;

        float lineWidth=1;
        private Vector3f arrowExtent=new Vector3f(1, 1, 1);
        
        public Builder(String name, Node root, ShapeUtil shapeUtil) {
            super(name, root, shapeUtil);
        }

        @Override
        public Shape build() {
            return new ArrowShape(this);
        }

        public Builder setAllParam(Vector3f dir,Vector3f target) {
            this.dir = dir;
            this.target = target;
            return this;
        }

        public Builder setLookAt(Vector3f target) {
            this.target = target;
            return this;
        }

        public Builder setLookAt(Spatial targetObj) {
            this.target = targetObj.getWorldTranslation();
            return this;
        }

        public Builder setDir(Vector3f dir) {
            this.dir = dir;
            return this;
        }

        public Builder setLineWidth(float lineWidth) {
            this.lineWidth = lineWidth;            
            return this;
        }

        public Builder setTarget(Vector3f target) {
            this.target = target;
            return this;
        }
        
        public Builder setArrowExtent(Vector3f arrowExtent){
            this.arrowExtent=arrowExtent;
            return this;
        }
        
        
    }

    public ArrowShape(Builder builder) {
        super(builder);
        this.dir = builder.dir;
        this.lineWidth=builder.lineWidth;
        this.arrowExtent=builder.arrowExtent;
        this.createShape(builder);
    }

    void createShape(Builder builder) {
        // SHAPE
        Arrow arrow=new Arrow(dir);
        arrow.setLineWidth(lineWidth);
        //arrow.setArrowExtent(arrowExtent);
        Geometry newGeo = new Geometry(this.getName() + "Arrow", arrow);
        
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

        //System.out.print("Height" + height);

    }
    
}