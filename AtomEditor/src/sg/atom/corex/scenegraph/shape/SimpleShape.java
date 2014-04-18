/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.scenegraph.shape;

import sg.atom.corex.scenegraph.shape.ShapeUtils;
import sg.atom.corex.scenegraph.shape.Shape;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hungcuong
 */
public class SimpleShape extends Shape {
    protected Node root;
    protected Vector3f pos = new Vector3f(0, 0, 0);
    protected Vector3f rot = new Vector3f(0, 0, 0);
    protected Vector3f scale = new Vector3f(1, 1, 1);
    protected ColorRGBA color = ColorRGBA.Gray;
    protected Material material;
    protected float opacity = 1;
    protected ShapeUtils shapeUtil;
    protected Bucket bucket=Bucket.Opaque;
    public List<Geometry> geoList = new LinkedList<Geometry>();
        
    protected float width = 1f, height = 1f, length = 1f;
    protected Vector3f allSize = new Vector3f(width, height, length);
    protected boolean wired=false;

    public static class Builder extends DefaultShapeBuilder {

        protected float width = 1f, height = 1f, length = 1f;
        protected Vector3f allSize = new Vector3f(width, height, length);

        public Builder(String name, Node root, ShapeUtils shapeUtil) {
            super(name, root, shapeUtil);
        }

        @Override
        public Shape build() {
            return new SimpleShape(this);
        }
    }

    public SimpleShape(Builder builder) {
        super(builder);
        
        this.shapeUtil=builder.shapeUtil;
        
        // BASIC MATERIAL
        material=builder.getMaterial();
        color=builder.getColor();
        bucket=builder.getBucket();
        opacity=builder.getOpacity();
        wired=builder.getWired();
        
        material.setColor("Color", color);
        material.setFloat("Opacity", opacity);
        if (opacity != 1) {
            material.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
            bucket = Bucket.Transparent;
        } else {
            bucket = Bucket.Transparent;
        }
        
        material.getAdditionalRenderState().setWireframe(wired);
        // BASIC SIZE
        this.width = builder.width;
        this.height = builder.height;
        this.length = builder.length;
        this.allSize = builder.allSize.clone();
        // POSTION
        this.pos=builder.getPos();
        this.scale=builder.getScale();
        this.root=builder.getRoot();
        
        if (builder.getAttachType()==Shape.GEO){
            
        } else if (builder.getAttachType()==Shape.SHAPE){
            root.attachChild(this);
            this.setLocalTranslation(pos);
            this.setLocalScale(scale);         
        }
        
        this.setLocalTranslation(pos);
        this.setLocalScale(scale);
    }
    
    public List<Geometry> getGeoList(){
        return geoList;
    }
}
