package sg.atom.world;

import com.google.inject.Inject;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.asset.DesktopAssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.lodcalc.DistanceLodCalculator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.atom.core.AbstractManager;
import sg.atom.core.AtomMain;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.gameplay.GameLevel;
import sg.atom.stage.GameScene;
import sg.atom.stage.SoundManager;
import sg.atom.stage.StageManager;
import sg.atom.world.gen.WorldGenerator;
import sg.atom.world.lod.DefaultLODManager;
import sg.atom.world.lod.WorldLODManager;
import sg.atom.world.spatial.SceneGraphUtils;
import sg.atom.world.spatial.SpatialInfo;
import sg.atom.world.terrain.GenericTerrain;
import sg.atom.world.visibility.DefaultWorldVisibilityManager;
import sg.atom.world.visibility.WorldVisibilityManager;

/**
 * WorldManager Manage Spatial and all scene graph operations.
 *
 * <p>Base game entity->spatial managing class, stores and loads the entities,
 * used on server and on client.
 *
 * Automatically sends changes via network when running on server, used to apply
 * network data on client and server.
 *
 * <p>Different Cache/Pools mechanism beside of AssetManager.
 *
 * <p>Utitlities to handle threadsafe Spatial mapping for one-two steps indirect
 * access.
 *
 * <p>Work together with SpawningManager, SoundManager, SelectManager based in
 * GameScene and GameLevel.
 *
 */
public class WorldManager extends AbstractManager implements IGameCycle {

    protected static final Logger logger = Logger.getLogger(WorldManager.class.getName());
    protected AtomMain app;
    protected AssetManager assetManager;
    protected StageManager stageManager;
    protected Node rootNode;
    // GAME LEVEL 
    protected Node worldNode;
    protected GameLevel currentLevel;
    protected Node levelNode;
    protected Camera cam;
    //Terrain
    protected GenericTerrain terrain;
    protected RigidBodyControl terrainPhysicsNode;
    //Physic
    protected BulletAppState bulletAppState;
    protected PhysicsSpace space;
    //FIXME: The sub managers
    protected LightShadowManager lightShadowManager;
    protected SoundManager soundManager;
    protected DayNightTimeManager dayNightTimeManager;
    protected WaterManager waterManager;
    protected EnviromentManager enviromentManager;
    protected WorldLODManager worldLODManager;
    protected WorldVisibilityManager worldVisibilityManager;
    protected AppStateManager stateManager;
    protected ForesterManager foresterManager;
    protected MaterialManager materialManager;
    protected TerrainManager terrainManager;
    @Inject
    protected WorldSettings worldSettings;
    // Generator - use in generative content games.
    @Inject
    protected WorldGenerator worldGenerator;
    // Spatial Managing 
    //FIXME: Use Guava Cache instead of HashMap
    protected HashMap<Spatial, SpatialInfo> spatialInfoList;

    protected WorldManager() {
        //For use in singleton;
    }

    public WorldManager(AtomMain app, Node worldNode) {
        this.app = app;
        this.rootNode = app.getRootNode();
        this.worldNode = worldNode;
        this.cam = app.getCamera();
        this.assetManager = app.getAssetManager();
        this.stateManager = app.getStateManager();
        this.stageManager = app.getStageManager();
    }

    public void lazyInit(AtomMain app, Node worldNode) {
        this.app = app;
        this.rootNode = app.getRootNode();
        this.worldNode = worldNode;
        this.cam = app.getCamera();
        this.assetManager = app.getAssetManager();
        this.stateManager = app.getStateManager();
        this.stageManager = app.getStageManager();
    }

    /* WorldManager 
     * mostly in charge for creating , placing models and their physics
     * It has sub-Manager to help the job done in several aspects
     * 
     * initWorld : prepare all the sub-Manager
     * loadWorld : load the World so it's ready to be attached to the display
     * configWorld : extract infos from Level and entities and the links between them
     * attachWorld : simple Attach things
     * finishWorld : finish touch
     * updateWorld : update call of AppState and update all sub-Manager
     * destroyWorld : destroy and free everything needed
     * 
     * 
     * initWorld :
     * create all the sub-Manager and tell them to init themself
     * 
     * loadWorld :
     * Load all the models
     * + Level: terrain, level models (house and props)
     * + Simple Lighting (pre-create)
     * + Forester: load tree models, mat...
     * + Water: make a water plane
     * 
     * 
     * configWorld :
     * + Level infos, startPos
     * + Physics ,Models Terrain Physic
     * + Enviroment, DayNight Lighting
     * + Sound
     * 
     * attachWorld:
     * Just attach all into rootNode
     * 
     * finishWorld :
     * Tell the stage Manager all things done!
     */
    // =========== INIT WORLD ====================
    public void initWorld(GameLevel level, WorldSettings worldSettings) {
        if (worldSettings == null) {
            this.worldSettings = new WorldSettings();
        } else {
            this.worldSettings = worldSettings;
        }

        spatialInfoList = new LinkedHashMap<Spatial, SpatialInfo>();
        // Create and Init all the Sub-Manager


        if (this.worldSettings.useDayLight) {
            lightShadowManager = new LightShadowManager(this, stageManager);
            dayNightTimeManager = new DayNightTimeManager();
        }
        if (this.worldSettings.useWater) {
            waterManager = new WaterManager(stageManager, this);
        }
        if (this.worldSettings.useEnviroment) {
            enviromentManager = new EnviromentManager(stageManager, this);
        }
//        if (this.worldSettings.useWeather) {
//            weatherManager = new WeatherManager();
//        }

        //FIXME:
        worldLODManager = new DefaultLODManager(stageManager, this);
        worldVisibilityManager = new DefaultWorldVisibilityManager(stageManager, this);

        if (this.worldSettings.useForestor) {
            foresterManager = new ForesterManager(this, stageManager);
        }
        materialManager = new MaterialManager(this);
        if (this.worldSettings.useLevel && level != null) {
            initLevel(level);
        }

        if (this.worldSettings.useWater) {
            waterManager.initWater();
        }

    }

    public void initWorld(GameLevel level) {
        initWorld(level, null);
    }

    public void initWorld() {
        initWorld(null, null);
    }

    /**
     * loads the specified level node
     *
     * @param name
     */
    public void initLevel(GameLevel level) {
        currentLevel = level;
    }

    // ============= LOAD WORLD ===================
    public void loadWorld() {
        if (this.worldSettings.useLevel) {
            currentProgressName = "Level";
            currentProgressPercent = 0.1f;
            loadLevel(currentLevel);
        }

        if (this.worldSettings.useForestor) {
            currentProgressName = "Forester";
            currentProgressPercent = 0.6f;
            foresterManager.loadForesterAsset();
        }


        if (this.worldSettings.useWater) {
            currentProgressName = "Water";
            currentProgressPercent = 0.8f;
            waterManager.loadWaterAsset();
        }

    }

    public void loadLevel(GameLevel level) {
        level.loadLevel();
        levelNode = level.getLevelNode();
        //currentLevel = level;
    }

    public void loadScene(GameScene currentScene) {
        //cam = currentScene.currentCamera;
    }

    /**
     * preloads the models with the given names
     *
     * @param modelNames
     */
    public void preloadModels(String[] modelNames) {
        for (int i = 0; i < modelNames.length; i++) {
            String string = modelNames[i];
            assetManager.loadModel(string);
        }
    }
    float currentProgressPercent;
    String currentProgressName;

    public float getCurrentProgressPercent() {
        return currentProgressPercent;
    }

    public String getCurrentProgressName() {
        return currentProgressName;
    }
    // ======== CONFIG WORLD ===============

    public void configWorld() {
        if (worldSettings.useTerrainLOD) {
            // Terrain
            terrain = SceneGraphUtils.findGenericTerrain(levelNode);
            if (terrain == null) {
                throw new RuntimeException("Can not find Terrain in this Level !");
            }

            // Add new TerrainLodControl
            if (terrain.getControl(TerrainLodControl.class) != null) {
                TerrainLodControl control = new TerrainLodControl(terrain, stageManager.getCurrentActiveCamera());
                control.setLodCalculator(new DistanceLodCalculator(32, 1.7f)); // patch size, and a multiplier
                terrain.addControl(control);
            }

        }

        if (worldSettings.usePhysics) {
            // create physic for Terrain
            createPhysic();
        }
        if (worldSettings.useForestor && worldSettings.usePhysics) {
            // Forestor
            foresterManager.initForester(terrain);
            foresterManager.setupForester();
        }
        // Water 
        if (worldSettings.useWater) {
            waterManager.configWater(levelNode);
        }
        configWorldCustom();
    }

    protected void configWorldCustom() {
        if (this.worldSettings.useDayLight) {
            lightShadowManager.createLights();
        }

    }
    // ========== ATTACH WORLD ===============================

    /**
     * attaches the level node to the rootnode
     */
    public void attachLevel() {
        worldNode.attachChild(levelNode);
    }

    public void attachWorld() {

        if (worldSettings.useLevel) {
            attachLevel();
        }
        if (worldSettings.useWater) {
            waterManager.attachWater();
        }
        if (worldSettings.usePhysics) {
            stateManager.attach(bulletAppState);

            if (worldSettings.useLevel) {
                getPhysicsSpace().addAll(levelNode);
            }
            if (worldSettings.useTerrain) {
                getPhysicsSpace().add(terrainPhysicsNode);
            }
        }
        rootNode.attachChild(worldNode);
        if (worldSettings.useDayLight) {
            lightShadowManager.attachLights();
        }

    }

    /**
     * Ultilities to attach Spatials.
     *
     * @param modelNode
     */
    public void attachSpatial(Node modelNode) {
        if (getLevelNode() != null) {
            getLevelNode().attachChild(modelNode);
        } else {
            getWorldNode().attachChild(modelNode);
        }
    }

    public void attachSpatialDelayed(Node modelNode, float time) {
    }
    // ======== PHYSIC STUFF ===============

    public void createPhysic() {
        // Physic
        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);

        if (worldSettings.useTerrain) {
            createTerrainPhysic();
        }

    }

    private void createTerrainPhysic() {
        // Terrain
        terrain = SceneGraphUtils.findGenericTerrain(levelNode);
        if (terrain == null) {
            throw new RuntimeException("Can not find Terrain in this Level !");
        }
        if (terrain.getControl(RigidBodyControl.class) == null) {
            // Remove the other one
            System.out.println(" Create terrain physic!");
            terrainPhysicsNode = new RigidBodyControl(CollisionShapeFactory.createMeshShape(terrain.getGeometricData()), 0);
            terrain.addControl(terrainPhysicsNode);
        }
    }
    // ========= FINISH WORLD =================

    public void finishWorld() {
        //this.space = bulletAppState.getPhysicsSpace();
        //space.setAccuracy(Globals.PHYSICS_FPS);
    }
    // ========= UPDATE WORLD =================

    public void simpleUpdate(float tpf) {
        if (worldSettings.useForestor) {
            foresterManager.update(tpf);
        }
        if (worldSettings.useLevel) {
            currentLevel.simpleUpdate(tpf);
        }
    }
    // =========== DESTROY WORLD ===============

    void destroyWorld() {
    }

    /**
     * Detaches the level and clears the cache
     */
    public void closeLevel() {
        rootNode.detachChild(worldNode);
        ((DesktopAssetManager) assetManager).clearCache();
    }
    // UTIL

    /**
     * Remove the spatial from parenet but save into a cache with parent's space
     * info to restore later
     *
     * @param sp
     */
    public void safeRemove(Spatial sp) {
        spatialInfoList.put(sp, generateSpatialInfo(sp));
    }

    /**
     * Restore the spatial from a cache with parent's space info.
     *
     * @param sp
     */
    public void safeRestore(Spatial sp) {
        SpatialInfo si = spatialInfoList.get(sp);
        if (si != null) {
            si.parent.attachChild(sp);
            sp.setLocalTransform(si.getLocalTransform().clone());
        } else {
            Logger.getLogger(WorldManager.class.getName()).log(Level.WARNING, "Cant not find the spatial!");
        }
    }

    public SpatialInfo generateSpatialInfo(Spatial sp) {
        SpatialInfo spatialInfo = new SpatialInfo();
        spatialInfo.parent = sp.getParent();
        spatialInfo.setSpatial(sp);
        spatialInfo.setLocalTransform(sp.getLocalTransform().clone());
        return spatialInfo;
    }

    /**
     * gets the spatial belonging to a PhysicsCollisionObject
     *
     * @param object
     * @return
     */
    public Spatial getSpatial(PhysicsCollisionObject object) {
        Object userObj = object.getUserObject();
        if (userObj instanceof Spatial) {
            Spatial spatial = (Spatial) userObj;
            return spatial;
        }
        return null;
    }

    // Streaming world----------------------------------------------------------
    /*
     *3D world streaming 
     generally involves the following steps: 
     1.  Partition the world geographically into independently renderable pieces. 
     2.  Prefetch the right pieces of world at the right time ahead of when the avatar will 
     interact with the pieces. 
     3.  Send the pieces from server to client. 
     4.  Re-integrate the pieces and render them at the client side.  
     * 
     */
    // ======== SETTER & GETTER =============
    public StageManager getStageManager() {
        return stageManager;
    }

    /**
     * get the world root node (not necessarily the application rootNode!)
     *
     * @return
     */
    public Node getWorldNode() {
        return worldNode;
    }

    public PhysicsSpace getPhysicsSpace() {
        return bulletAppState.getPhysicsSpace();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public Node getRootNode() {
        return app.getRootNode();
    }

    /**
     * @return the lightShadowManager
     */
    public LightShadowManager getLightShadowManager() {
        return lightShadowManager;
    }

    /**
     * @return the soundManager
     */
    public SoundManager getSoundManager() {
        return soundManager;
    }

    /**
     * @return the dayNightTimeManager
     */
    public DayNightTimeManager getDayNightTimeManager() {
        return dayNightTimeManager;
    }

    /**
     * @return the waterManager
     */
    public WaterManager getWaterManager() {
        return waterManager;
    }

    /**
     * @return the enviromentManager
     */
    public EnviromentManager getEnviromentManager() {
        return enviromentManager;
    }

//    /**
//     * @return the weatherManager
//     */
//    public WeatherManager getWeatherManager() {
//        return weatherManager;
//    }
    /**
     * @return the worldLODManager
     */
    public WorldLODManager getWorldLODManager() {
        return worldLODManager;
    }

    public MaterialManager getMaterialManager() {
        return this.materialManager;
    }

    public GenericTerrain getTerrain() {
        return terrain;
    }

    public void setTerrain(GenericTerrain terrain) {
        this.terrain = terrain;
    }

    public WorldSettings getWorldSettings() {
        return worldSettings;
    }

    public void setWorldSettings(WorldSettings worldSettings) {
        this.worldSettings = worldSettings;
    }

    public Camera getCamera() {
        return app.getCamera();
    }

    public void detachWorld() {
        rootNode.detachChild(worldNode);
    }

    public void enablePhysicDebug(boolean act) {
        bulletAppState.setDebugEnabled(act);
    }

    public void tooglePhysicDebug() {
        bulletAppState.setDebugEnabled(!bulletAppState.isDebugEnabled());
    }

    public Node getLevelNode() {
        return levelNode;
    }
    //===CYCLE=====

    @Override
    public void init() {
        initWorld();
    }

    @Override
    public void load() {
        loadWorld();
    }

    @Override
    public void config(Properties props) {
    }

    @Override
    public void update(float tpf) {
        simpleUpdate(tpf);
    }

    @Override
    public void finish() {
        finishWorld();
    }

    @Override
    public LifeCyclePhase getCurrentPhase() {
        return null;
    }

    @Override
    public float getProgressPercent(LifeCyclePhase aPhrase) {
        return 0;
    }
}
