/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.terrain.Terrain;
import com.jme3.texture.Texture;
import forester.Forester;
import forester.grass.GrassLayer;
import forester.grass.GrassLayer.MeshType;
import forester.grass.GrassLoader;
import forester.grass.algorithms.GPAUniform;
import forester.grass.datagrids.MapGrid;
import forester.image.FormatReader.Channel;
import forester.trees.TreeLayer;
import forester.trees.TreeLoader;
import sg.atom.stage.StageManager;

/**
 * Facilities to use Forester and a-like in Atom enviroment and terrain.
 *
 * <p>Support forestor till androlo drop it. Going to pick another libs soon!
 *
 * @author atomix
 */
@Deprecated
public class ForesterManager {

    WorldManager worldManager;
    Camera cam;
    Terrain terrain;
    Node rootNode;
    StageManager stageManager;
    AssetManager assetManager;
    private boolean readyToUpdate = false;
    private Material grassMat2;
    // FORESTER
    Material matRock;
    PointLight pl;
    Geometry lightMdl;
    private float grassScale = 64;
    private float dirtScale = 16;
    private float rockScale = 128;
    private Forester forester;
    Spatial treeModel;
    Spatial rockModel;
    Material grassMat;
    Texture densityTexture;

    public ForesterManager(WorldManager worldManager, StageManager stageManager) {
        this.rootNode = worldManager.getRootNode();
        this.assetManager = worldManager.getAssetManager();
        this.stageManager = stageManager;
        this.worldManager = worldManager;
    }

    public void initForester(Terrain terrain) {
        this.cam = stageManager.getCurrentActiveCamera();
        this.terrain = terrain;
        // Step 1 - set up the forester. The forester is a singleton class that
        // can be accessed statically from anywhere, but we use a reference
        // variable here.
        forester = Forester.getInstance();

        forester.initialize(rootNode, cam, terrain, stageManager.getApp());


    }

    public void loadForesterAsset() {
        densityTexture = assetManager.loadTexture("Textures/Terrain/splat/alphamap.png");

        // Model
        treeModel = assetManager.loadModel("Models/Background/Tree2/Tree.mesh.j3o");
        rockModel = assetManager.loadModel("Models/Background/Tree2/stone_felsite2.j3o");
        grassMat = assetManager.loadMaterial("Materials/Grass/Grass.j3m");
        grassMat2 = assetManager.loadMaterial("Materials/Grass/DaisyBillboarded.j3m");

        //treeModel = assetManager.loadModel("Models/Spruce/SpruceMediumPoly.j3o");

    }
    TreeLoader treeLoader;
    GrassLoader grassLoader;

    public void setupForester() {
        // Step 2 - set up the treeloader. Page size is the same size as the
        // scaled terrain. Resolution is 4, meaning there are 4x4 = 16 blocks
        // of tree geometry per page (16 batches).
        // 
        // Far viewing range is set to 800, so that you can see the tree-blocks
        // being added and removed. Increase it by 100 or so and the trees will 
        // be added/removed seamlessly (no popping).

        treeLoader = forester.createTreeLoader(256, 8, 800f, 800f);
        createTrees();
        createRocks();
        grassLoader = forester.createGrassLoader(256, 4, 50f, 20f);
        createGrasses();
        readyToUpdate = true;
    }

    public void createTrees() {



        // Just creating a physics collision shape from the trunk, and
        // adding to the tree. The treeloader will automatically detect
        // the collision-shape and store it.
        Geometry geom = (Geometry) ((Node) treeModel).getChild(0);
        CollisionShape treeShape = CollisionShapeFactory.createMeshShape(geom);
        RigidBodyControl ts = new RigidBodyControl(treeShape, 0);
        geom.addControl(ts);

        // Step 3 - set up the datagrid.
        forester.trees.datagrids.MapGrid mapGrid = treeLoader.createMapGrid();
        mapGrid.addDensityMap(densityTexture, 0, 0, 0);
        //mapGrid.setThreshold(0.4f);

        // Step 4 - set up a tree layer

        // Create a tree-layer and configure it. The density texture data and
        // density multiplier works as described in SimpleGrassTest.
        TreeLayer treeLayer = treeLoader.addTreeLayer(treeModel, true);
        treeLayer.setDensityTextureData(0, Channel.Red);
        treeLayer.setDensityMultiplier(1.5f);

        treeLayer.setMaximumScale(4f);
        treeLayer.setMinimumScale(2f);
    }

    public void createGrasses() {
        //Adding some grass as well.

        MapGrid grid = grassLoader.createMapGrid();

        grid.addDensityMap(densityTexture, 0, 0, 0);


        GrassLayer layer = grassLoader.addLayer(grassMat, MeshType.CROSSQUADS);

        layer.setDensityTextureData(0, Channel.Red);

        layer.setDensityMultiplier(4.5f);

        layer.setMaxHeight(1.4f);
        layer.setMinHeight(1.f);

        layer.setMaxWidth(1.4f);
        layer.setMinWidth(1.f);

        ((GPAUniform) layer.getPlantingAlgorithm()).setThreshold(0.4f);


        // Adding another grasslayer.

        // Using billboards. Different material base but pretty much the same
        // parameters.
        GrassLayer layer2 = grassLoader.addLayer(grassMat2, MeshType.BILLBOARDS);

        // Using the same densitymap and channel as the grass.
        layer2.setDensityTextureData(0, Channel.Red);
        layer2.setDensityMultiplier(0.5f);
        //TODO:: Big bug !!


        layer2.setMaxHeight(1.6f);
        layer2.setMinHeight(1.4f);

        layer2.setMaxWidth(1.6f);
        layer2.setMinWidth(1.4f);

        ((GPAUniform) layer2.getPlantingAlgorithm()).setThreshold(0.4f);
        grassLoader.setWind(new Vector2f(1, 0));

    }

    public void createRocks() {

        TreeLayer rockLayer = treeLoader.addTreeLayer(rockModel, true);
        rockLayer.setDensityTextureData(0, Channel.Green);
        rockLayer.setDensityMultiplier(2.5f);

        rockLayer.setMaximumScale(2.5f);
        rockLayer.setMinimumScale(1f);
    }

    public void createImpostor() {
    }

    public void createAnimals() {
    }

    public void update(float tpf) {
        if (readyToUpdate) {
            forester.update(tpf);
        }
    }
}
