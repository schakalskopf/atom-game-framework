package sg.atom.stage;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.geomipmap.lodcalc.DistanceLodCalculator;
import sg.atom.core.AtomMain;
import sg.atom.gameplay.GameLevel;
import sg.atom.gameplay.GameScene;
import sg.atom.world.DayNightTimeManager;
import sg.atom.world.EnviromentManager;
import sg.atom.world.ForesterManager;
import sg.atom.world.LightShadowManager;
import sg.atom.world.MaterialManager;
import sg.atom.world.SceneGraphHelper;
import sg.atom.world.TerrainManager;
import sg.atom.world.WaterManager;
import sg.atom.world.WeatherManager;
import sg.atom.world.WorldSettings;
import sg.atom.world.lod.WorldLODManager;

/**
 * Base game entity managing class, stores and loads the entities, used on
 * server and on client. Automatically sends changes via network when running on
 * server, used to apply network data on client and server.
 *
 * @author normenhansen
 */
public class WorldManager {

    protected AtomMain app;
    protected AssetManager assetManager;
    protected StageManager stageManager;
    // GAME LEVEL 
    protected Node worldNode;
    protected GameLevel currentLevel;
    Node levelNode;
    protected Camera cam;
    //Terrain
    protected TerrainQuad terrain;
    RigidBodyControl terrainPhysicsNode;
    //Physic
    protected BulletAppState bulletAppState;
    protected PhysicsSpace space;
    // The sub managers
    protected LightShadowManager lightShadowManager;
    protected SoundManager soundManager;
    protected DayNightTimeManager dayNightTimeManager;
    protected WaterManager waterManager;
    protected EnviromentManager enviromentManager;
    protected WeatherManager weatherManager;
    protected WorldLODManager worldLODManager;
    protected AppStateManager stateManager;
    protected ForesterManager foresterManager;
    protected MaterialManager materialManager;
    protected WorldSettings worldSettings;
    protected TerrainManager terrainManager;
    protected final Node rootNode;

    public WorldManager(AtomMain app, Node worldNode) {
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
        // Create and Init all the Sub-Manager

        if (this.worldSettings.useDayLight) {
            lightShadowManager = new LightShadowManager(this, stageManager);
            dayNightTimeManager = new DayNightTimeManager();
        }
        if (this.worldSettings.useWater) {
            waterManager = new WaterManager(stageManager, this);
        }
        if (this.worldSettings.useEnviroment) {
            enviromentManager = new EnviromentManager();
        }
        if (this.worldSettings.useWeather) {
            weatherManager = new WeatherManager();
        }
        worldLODManager = new WorldLODManager();
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
    void initLevel(GameLevel level) {
        currentLevel = level;
    }

    // ============= LOAD WORLD ===================
    public void loadWorld() {

        currentProgressName = "Level";
        currentProgressPercent = 0.1f;
        if (this.worldSettings.useLevel) {
            loadLevel(currentLevel);
        }
        currentProgressName = "Forester";
        currentProgressPercent = 0.6f;
        if (this.worldSettings.useForestor) {
            foresterManager.loadForesterAsset();
        }

        currentProgressName = "Water";
        currentProgressPercent = 0.8f;
        if (this.worldSettings.useWater) {
            waterManager.loadWaterAsset();
        }

    }

    public void loadLevel(GameLevel level) {
        level.loadLevel();
        //level.postLoadLevel();
        levelNode = level.getLevelNode();

        //currentLevel = level;
    }

    void loadScene(GameScene currentScene) {
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
            terrain = SceneGraphHelper.findTerrain(levelNode);
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
    // ======== PHYSIC STUFF ===============

    void createPhysic() {
        // Physic
        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);

        if (worldSettings.useTerrain) {
            createTerrainPhysic();
        }

    }

    private void createTerrainPhysic() {
        // Terrain
        terrain = SceneGraphHelper.findTerrain(levelNode);
        if (terrain == null) {
            throw new RuntimeException("Can not find Terrain in this Level !");
        }
        if (terrain.getControl(RigidBodyControl.class) == null) {
            // Remove the other one
            System.out.println(" Create terrain physic!");
            terrainPhysicsNode = new RigidBodyControl(CollisionShapeFactory.createMeshShape(terrain), 0);
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
     * detaches the level and clears the cache
     */
    public void closeLevel() {
        //entities.clear();
        //space.removeAll(worldRoot);
/*
         for (Iterator<PlayerData> it = PlayerData.getPlayers().iterator(); it.hasNext();) {
         PlayerData playerData = it.next();
         playerData.setData("entity_id", -1l);
         }
         if (isServer()) {
         for (Iterator<PlayerData> it = PlayerData.getAIPlayers().iterator(); it.hasNext();) {
         PlayerData playerData = it.next();
         removePlayer(playerData.getId());
         }
         }
         for (Iterator<Long> et = new LinkedList(entities.keySet()).iterator(); et.hasNext();) {
         Long entry = et.next();
         syncManager.removeObject(entry);
         }
         syncManager.clearObjects();
         entities.clear();
         newId = 0;
         space.removeAll(worldRoot);
         rootNode.detachChild(worldRoot);
         ((DesktopAssetManager) assetManager).clearCache();          
        
         */
        //rootNode.detachChild(worldRoot);
        //((DesktopAssetManager) assetManager).clearCache();
    }
    // UTIL

    /**
     * gets the spatial belonging to a PhysicsCollisionObject
     *
     * @param object
     * @return
     */
    public Spatial getSpatial(PhysicsCollisionObject object) {
        Object obj = object.getUserObject();
        if (obj instanceof Spatial) {
            Spatial spatial = (Spatial) obj;

        }
        return null;
    }
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

    /**
     * @return the weatherManager
     */
    public WeatherManager getWeatherManager() {
        return weatherManager;
    }

    /**
     * @return the worldLODManager
     */
    public WorldLODManager getWorldLODManager() {
        return worldLODManager;
    }

    public MaterialManager getMaterialManager() {
        return this.materialManager;
    }

    public TerrainQuad getTerrain() {
        return terrain;
    }

    public void setTerrain(TerrainQuad terrain) {
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
}
