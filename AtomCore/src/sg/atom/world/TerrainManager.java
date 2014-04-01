/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.world;

import sg.atom.world.spatial.SceneGraphUtils;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.HillHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import java.util.ArrayList;
import java.util.List;
import sg.atom.stage.WorldManager;
import sg.atom.utils.factory.IAtomFactory;
import sg.atom.world.terrain.FlatTerrain;
import sg.atom.world.terrain.GenericTerrain;
import sg.atom.world.terrain.TerrainQuadAdapter;

/**
 * TerrainManager, Factory, Wrapper for useful functions for terrain.
 *
 * @author atomix
 */
public class TerrainManager implements IAtomFactory<GenericTerrain> {

    protected WorldManager worldManager;
    protected AssetManager assetManager;
    protected Node rootNode;
    protected GenericTerrain terrain;
    //Material FIXME: Should be more generic!
    protected Material matTerrain;
    protected Material matWire;
    // Display
    protected boolean wireframe = false;
    protected boolean triPlanar = false;
    //Manipulating
    public boolean raiseTerrain = false;
    public boolean lowerTerrain = false;

    public TerrainManager(WorldManager worldManager) {
        this.worldManager = worldManager;
        this.rootNode = worldManager.getRootNode();
        this.assetManager = worldManager.getAssetManager();
    }
    /* Creation */

    public void createTerrain(Object... params) {
    }

    public Material createSampleMaterial() {
        // Scaling
        float grassScale = 64;
        float dirtScale = 16;
        float rockScale = 128;

        // TERRAIN TEXTURE material
        Material matTerrain = new Material(assetManager, "Common/MatDefs/Terrain/Terrain.j3md");
        matTerrain.setBoolean("useTriPlanarMapping", false);

        // ALPHA map (for splat textures)
        matTerrain.setTexture("Alpha", assetManager.loadTexture("Textures/Terrain/splat/alphamap.png"));

        // GRASS texture
        Texture grassTex = assetManager.loadTexture("Textures/Terrain/splat/grass.jpg");
        grassTex.setWrap(WrapMode.Repeat);
        matTerrain.setTexture("Tex1", grassTex);
        matTerrain.setFloat("Tex1Scale", grassScale);

        // DIRT texture
        Texture dirtTex = assetManager.loadTexture("Textures/Terrain/splat/dirt.jpg");
        dirtTex.setWrap(WrapMode.Repeat);
        matTerrain.setTexture("Tex2", dirtTex);
        matTerrain.setFloat("Tex2Scale", dirtScale);

        // ROCK texture
        Texture rockTex = assetManager.loadTexture("Textures/Terrain/splat/road.jpg");
        rockTex.setWrap(WrapMode.Repeat);
        matTerrain.setTexture("Tex3", rockTex);
        matTerrain.setFloat("Tex3Scale", rockScale);

        return matTerrain;
    }

    public AbstractHeightMap createSampleHeightMap(String type) {
        AbstractHeightMap heightmap = null;
        try {
            if (type != null) {
                heightmap = new HillHeightMap(1025, 1000, 50, 100, (byte) 3);
            } else {
                // HEIGHTMAP image (for the terrain heightmap)
                Texture heightMapImage = assetManager.loadTexture("Textures/Terrain/splat/mountains512.png");
                // CREATE HEIGHTMAP
                heightmap = new ImageBasedHeightMap(heightMapImage.getImage(), 1f);
                heightmap.load();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return heightmap;
    }

    public void createFlatTerrain(int size) {
        terrain = new FlatTerrain(size, worldManager);
    }

    public void createSampleTerrain(int patchSize, int totalSize) {
        // First, we load up our textures and the heightmap texture for the terrain
        this.matTerrain = createSampleMaterial();
        AbstractHeightMap heightmap = createSampleHeightMap(null);
        matWire = MaterialManager.getDefaultInstance(assetManager).getWireFrameMat();

        /*
         * Here we create the actual terrain. The tiles will be 65x65, and the total size of the
         * terrain will be 513x513. It uses the heightmap we created to generate the height values.
         */
        /**
         * Optimal terrain patch size is 65 (64x64). The total size is up to
         * you. At 1025 it ran fine for me (200+FPS), however at size=2049, it
         * got really slow. But that is a jump from 2 million to 8 million
         * triangles...
         */
        terrain = new TerrainQuadAdapter("terrain", patchSize, totalSize, heightmap.getHeightMap());

        terrain.setMaterial(matTerrain);
        //terrain.setLocalTranslation(0, -100, 0);
        //terrain.setLocalScale(2f, 1f, 2f);
        terrain.setShadowMode(ShadowMode.Receive);
    }

    public void loadTerrain() {
        // Terrain
        terrain = SceneGraphUtils.findGenericTerrain(worldManager.getLevelNode());
        if (terrain == null) {
            throw new RuntimeException("Can not find Terrain in this Level !");
        }
    }

    public void configTerrain() {
        Camera cam = worldManager.getStageManager().getCurrentActiveCamera();

        // Add new TerrainLodControl
        if (terrain.getControl(TerrainLodControl.class) != null) {
            /*
             TerrainLodControl control = new TerrainLodControl(terrain, cam);
             control.setLodCalculator(new DistanceLodCalculator(65, 2.7f)); // patch size, and a multiplier
             terrain.addControl(control);
             */
        }
    }

    public void attachTerrain() {
        rootNode.attachChild(terrain);
    }

    public void setupKeys(InputManager inputManager) {
        inputManager.addMapping("wireframe", new KeyTrigger(KeyInput.KEY_T));
        inputManager.addListener(actionListener, "wireframe");
        inputManager.addMapping("Raise", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(actionListener, "Raise");
        inputManager.addMapping("Lower", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addListener(actionListener, "Lower");
    }
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean pressed, float tpf) {
            if (name.equals("wireframe") && !pressed) {
                wireframe = !wireframe;
                if (!wireframe) {
                    terrain.setMaterial(matWire);
                } else {
                    terrain.setMaterial(matTerrain);
                }
            } else if (name.equals("Raise")) {
                raiseTerrain = pressed;
            } else if (name.equals("Lower")) {
                lowerTerrain = pressed;
            }
        }
    };

    public float getHeight(Vector2f pos2f) {
        return terrain.getHeight(pos2f);
    }

    public GenericTerrain getTerrain() {
        return terrain;
    }

    public void adjustHeight(Vector3f loc, float radius, float height) {

        // offset it by radius because in the loop we iterate through 2 radii
        int radiusStepsX = (int) (radius / terrain.getLocalScale().x);
        int radiusStepsZ = (int) (radius / terrain.getLocalScale().z);

        float xStepAmount = terrain.getLocalScale().x;
        float zStepAmount = terrain.getLocalScale().z;
        long start = System.currentTimeMillis();
        List<Vector2f> locs = new ArrayList<Vector2f>();
        List<Float> heights = new ArrayList<Float>();

        for (int z = -radiusStepsZ; z < radiusStepsZ; z++) {
            for (int x = -radiusStepsX; x < radiusStepsX; x++) {

                float locX = loc.x + (x * xStepAmount);
                float locZ = loc.z + (z * zStepAmount);

                if (isInRadius(locX - loc.x, locZ - loc.z, radius)) {
                    // see if it is in the radius of the tool
                    float h = calculateHeight(radius, height, locX - loc.x, locZ - loc.z);
                    locs.add(new Vector2f(locX, locZ));
                    heights.add(h);
                }
            }
        }

        terrain.adjustHeight(locs, heights);
        //System.out.println("Modified "+locs.size()+" points, took: " + (System.currentTimeMillis() - start)+" ms");
        terrain.updateModelBound();
    }

    public void adjustLevelHeight(Vector3f loc, float radius, float height) {

        // offset it by radius because in the loop we iterate through 2 radii
        int radiusStepsX = (int) (radius / terrain.getLocalScale().x);
        int radiusStepsZ = (int) (radius / terrain.getLocalScale().z);

        float xStepAmount = terrain.getLocalScale().x;
        float zStepAmount = terrain.getLocalScale().z;
        long start = System.currentTimeMillis();
        List<Vector2f> locs = new ArrayList<Vector2f>();
        List<Float> heights = new ArrayList<Float>();

        for (int z = -radiusStepsZ; z < radiusStepsZ; z++) {
            for (int x = -radiusStepsX; x < radiusStepsX; x++) {

                float locX = loc.x + (x * xStepAmount);
                float locZ = loc.z + (z * zStepAmount);

                // see if it is in the radius of the tool
                float h = height;
                locs.add(new Vector2f(locX, locZ));
                heights.add(h);
            }
        }

        terrain.setHeight(locs, heights);
        //System.out.println("Modified "+locs.size()+" points, took: " + (System.currentTimeMillis() - start)+" ms");
        terrain.updateModelBound();
    }

    private boolean isInRadius(float x, float y, float radius) {
        Vector2f point = new Vector2f(x, y);
        // return true if the distance is less than equal to the radius
        return point.length() <= radius;
    }

    private float calculateHeight(float radius, float heightFactor, float x, float z) {
        // find percentage for each 'unit' in radius
        Vector2f point = new Vector2f(x, z);
        float val = point.length() / radius;
        val = 1 - val;
        if (val <= 0) {
            val = 0;
        }
        return heightFactor * val;
    }

    @Override
    public GenericTerrain create(Object param) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GenericTerrain create(Object... params) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GenericTerrain cloneObject(GenericTerrain orginal) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setTerrain(GenericTerrain terrain) {
        this.terrain = terrain;
    }
}
