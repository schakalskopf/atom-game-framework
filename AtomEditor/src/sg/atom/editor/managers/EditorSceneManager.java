/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.editor.managers;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Line;
import com.jme3.shadow.PssmShadowRenderer;
import java.util.Properties;
import sg.atom.core.AbstractManager;
import sg.atom.corex.scenegraph.spatial.EditorSpatialInfo;
import sg.atom.editor.InGameEditor;

/**
 * Extension of WorldTestHelper. but use Atom Shape instead of JME3 Shape and
 * other wrapers.
 *
 * <p>
 * 
 * @author cuong.nguyenmanh2
 */
public class EditorSceneManager extends AbstractManager {

    private SimpleApplication app;
    private final InGameEditor editor;
    private AssetManager assetManager;
    private ViewPort viewPort;
    //The scene split into three Nodes. ----------------------------------------
    private Node rootNode;
    private Node gizmo = new Node("Gizmo");
    private Node toolNode = new Node("ToolNode");

    public EditorSceneManager(InGameEditor editor) {
        this.editor = editor;
        this.app = editor.getApp();
        this.rootNode = app.getRootNode();
        this.assetManager = app.getAssetManager();
        this.viewPort = app.getViewPort();

    }

    public void startUp() {
        createLight();
        createGizmo();
        createGrid(12, 12);
        rootNode.attachChild(toolNode);
    }

    public void createGrid(int gw, int gh) {
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.DarkGray);

        Grid grid = new Grid(gw - 1, gh - 1, 1);
        Geometry gridGeo = new Geometry("Grid", grid);
        gridGeo.setMaterial(mat);
        gridGeo.setLocalTranslation(-gw / 2 + 1, 0f, -gh / 2 + 1);
        Node gridNode = new Node("GridNode");

        Line xline = new Line(new Vector3f(gw / 2, 0.01f, 0), new Vector3f(-gw / 2, 0.01f, 0));
        Geometry xlineGeo = new Geometry("XLine", xline);
        Material redMat = mat.clone();
        redMat.setColor("Color", ColorRGBA.Red);
        xlineGeo.setMaterial(redMat);
        //
        Line zline = new Line(new Vector3f(0, 0.01f, gh / 2), new Vector3f(0, 0.01f, -gh / 2));
        Material blueMat = mat.clone();
        blueMat.setColor("Color", ColorRGBA.Blue);
        Geometry zlineGeo = new Geometry("ZLine", zline);
        zlineGeo.setMaterial(blueMat);

        gridNode.attachChild(gridGeo);
        gridNode.attachChild(xlineGeo);
        gridNode.attachChild(zlineGeo);
        toolNode.attachChild(gridNode);
    }

    public void createLight() {
        /**
         * A white, spot light source.
         */
        PointLight lamp = new PointLight();
        lamp.setPosition(Vector3f.ZERO);
        lamp.setColor(ColorRGBA.White);
        rootNode.addLight(lamp);
        /**
         * Advanced shadows for uneven surfaces
         */
        PssmShadowRenderer pssm = new PssmShadowRenderer(assetManager, 1024, 3);
        pssm.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
        viewPort.addProcessor(pssm);

    }

    public Geometry putShape(Node node, Mesh shape, ColorRGBA color) {
        Geometry g = new Geometry("shape", shape);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("Color", color);
        g.setMaterial(mat);
        node.attachChild(g);
        return g;
    }

    public void putArrow(Vector3f pos, Vector3f dir, ColorRGBA color) {
        Arrow arrow = new Arrow(dir);
        arrow.setLineWidth(4); // make arrow thicker

        putShape(gizmo, arrow, color).setLocalTranslation(pos);
        toolNode.attachChild(gizmo);
        gizmo.scale(1);
    }

    public void createGizmo() {
        putArrow(Vector3f.ZERO, Vector3f.UNIT_X, ColorRGBA.Red);
        putArrow(Vector3f.ZERO, Vector3f.UNIT_Y, ColorRGBA.Green);
        putArrow(Vector3f.ZERO, Vector3f.UNIT_Z, ColorRGBA.Blue);
    }

    public void setView(String viewPreset) {
    }

    public void isolateObject(EditorSpatialInfo spatial) {
    }

    public void setupWindows(String windowPreset) {
    }

    public void init() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void load() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void config(Properties props) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void update(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void finish() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public LifeCyclePhase getCurrentPhase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public float getProgressPercent(LifeCyclePhase aPhrase) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
