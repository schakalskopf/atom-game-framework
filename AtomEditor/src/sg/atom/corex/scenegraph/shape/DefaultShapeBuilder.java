/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.scenegraph.shape;

import sg.atom.corex.scenegraph.shape.ShapeUtils;
import sg.atom.corex.scenegraph.shape.ShapeUtilBuilder;
import sg.atom.corex.scenegraph.shape.Shape;
import sg.atom.corex.material.MaterialUtils;
import sg.atom.corex.scenegraph.shape.Shape;
import sg.atom.corex.scenegraph.shape.ShapeUtils;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;

/**
 *
 * @author hungcuong
 */
public class DefaultShapeBuilder implements ShapeUtilBuilder<Shape> {

    private String name = "";
    private Node root;
    private Vector3f pos = new Vector3f(0, 0, 0);
    private Vector3f rot = new Vector3f(0, 0, 0);
    private Vector3f scale = new Vector3f(1, 1, 1);
    private ColorRGBA color = ColorRGBA.Gray;
    private Material material;
    private float opacity = 1;
    private Bucket bucket = Bucket.Opaque;
    private boolean wired = false;
    int attachType = Shape.SHAPE;
    private MaterialUtils matUtil;
    
    public ShapeUtils shapeUtil;
    
    public DefaultShapeBuilder(String name, Node root, ShapeUtils shapeUtil) {
        this.name = name;
        this.root = root;
        this.shapeUtil = shapeUtil;
        this.material = matUtil.getDefaultMaterial();
    }

    public Shape build() {

        return new Shape(this);
    }

    // GETTER
    public String getName() {
        return name;
    }

    public Node getRoot() {
        return root;
    }

    public ColorRGBA getColor() {
        return color;
    }

    public Vector3f getPos() {
        return pos;
    }

    public Vector3f getRot() {
        return rot;
    }

    public Vector3f getScale() {
        return scale;
    }

    public Material getMaterial() {
        return material;
    }

    public Bucket getBucket() {
        return bucket;
    }

    public float getOpacity() {
        return opacity;
    }

    public boolean getWired() {
        return wired;
    }
    // SETTER

    public DefaultShapeBuilder setColor(ColorRGBA color) {
        this.color = color;
        return this;
    }

    public DefaultShapeBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public DefaultShapeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public DefaultShapeBuilder setOpacity(float opacity) {
        this.opacity = opacity;
        return this;
    }

    public DefaultShapeBuilder setPos(Vector3f pos) {
        this.pos = pos;
        return this;
    }

    public DefaultShapeBuilder setPos(float posx, float posy, float posz) {
        this.pos = new Vector3f(posx, posy, posz);
        return this;
    }

    public DefaultShapeBuilder setRoot(Node root) {
        this.root = root;
        return this;
    }

    public DefaultShapeBuilder setRot(Vector3f rot) {
        this.rot = rot;
        return this;
    }
    public DefaultShapeBuilder setRot(float rotx, float roty, float rotz) {
        this.rot = new Vector3f(rotx, roty, rotz);
        return this;
    }
    
    public DefaultShapeBuilder setScale(Vector3f scale) {
        this.scale = scale;
        return this;
    }

    public DefaultShapeBuilder setBucket(Bucket bucket) {
        this.bucket = bucket;
        return this;
    }

    public DefaultShapeBuilder setWired(boolean wired) {
        this.wired = wired;
        return this;
    }
   
        public int getAttachType() {
        return attachType;
    }

    public DefaultShapeBuilder setAttachType(int attachType) {
        this.attachType = attachType;
        return this;
    }
}