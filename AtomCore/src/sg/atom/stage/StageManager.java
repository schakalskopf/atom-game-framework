package sg.atom.stage;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.atom.core.AbstractManager;
import sg.atom.core.AtomMain;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.ui.GameGUIManager;
import sg.atom.core.lifecycle.ProgressInfo;
import sg.atom.core.timing.TimeProvider;
import sg.atom.entity.EntityManager;
import sg.atom.fx.GameEffectManager;
import sg.atom.fx.ScreenEffectManager;
import sg.atom.gameplay.GameLevel;
import sg.atom.gameplay.GamePlayManager;

/**
 * <i>Stage is the place where Actors do the performance.</i><br> StageManager
 * in charge for all aspects of the game
 *
 * </br>(1) Input, action, logic and stragegy of the game
 *
 * </br> (2)Control the camera and cinematic , also manage game data,gameplay
 * and players.</br>
 *
 * <p>It's a high level manager and doesn't directly do render and display jobs.
 * Of course, it has sub-Manager(s) to help the job done in specific aspects
 *
 * <ul> <li> initStage : prepare all the sub-Manager(s)</li>
 *
 * <li> loadStage : load the World so it's ready to be attached to the
 * display</li>
 *
 * <li> configStage : extract infos from Level and entities and the links
 * between them</li>
 *
 * <li> attachStage : simple Attach things finishStage : finish touch</li>
 *
 * <li> updateStage : update call of AppState and update all sub-Manager</li>
 *
 * <li> destroyStage : destroy and free everything needed</li> </ul></p>
 */
public class StageManager extends AbstractManager implements TimeProvider, IGameCycle {

    // Shortcut link to sub-managers
    protected AtomMain app;
    protected AssetManager assetManager;
    protected InputManager inputManager;
    protected WorldManager worldManager;
    protected GameGUIManager gameGUIManager;
    protected RenderManager renderManager;
    protected GameEffectManager effectManager;
    protected GamePlayManager gamePlayManager;
    protected ScreenEffectManager screenEffectManager;
    protected EntityManager entityManager;
    protected SelectManager selectManager;
    // Rendering
    protected Node rootNode;
    protected Node guiNode;
    protected ViewPort viewPort;
    // State and timming
    protected boolean initedStatus;
    protected boolean finished;
    protected boolean gamePaused;
    protected boolean resume;
    protected float time = 0;
    //Monitoring
    protected ProgressInfo currentProgress = new ProgressInfo("None");
    //Gameplay
    protected ArrayList<GameLevel> gameLevelList = new ArrayList<GameLevel>();
    protected GameLevel currentLevel;

    public StageManager() {
        //For use in singleton!
    }

    public StageManager(AtomMain app) {
        this.app = app;
        this.rootNode = app.getRootNode();
        this.guiNode = app.getGuiNode();
        this.assetManager = app.getAssetManager();

        this.inputManager = app.getInputManager();
        this.gameGUIManager = app.getGameGUIManager();
        this.renderManager = app.getRenderManager();
        this.viewPort = app.getViewPort();
    }

    public void lazyInit(AtomMain app) {
        this.app = app;
        this.rootNode = app.getRootNode();
        this.guiNode = app.getGuiNode();
        this.assetManager = app.getAssetManager();

        this.inputManager = app.getInputManager();
        this.gameGUIManager = app.getGameGUIManager();
        this.renderManager = app.getRenderManager();
        this.viewPort = app.getViewPort();
    }

    public ProgressInfo getProgressInfo() {
        return currentProgress;
    }
    // ======== INIT STAGE =============

    public void initStage() {
        // INIT SUB-MANAGERs
        this.effectManager = new GameEffectManager(this);
        this.screenEffectManager = new ScreenEffectManager(this);
        this.entityManager = new EntityManager(this);
        entityManager.init();

        initStageCustom();
        gamePaused = false;
        resume = false;
        initedStatus = true;
    }

    public void initStageCustom() {
        this.worldManager = new WorldManager(app, rootNode);
        this.gamePlayManager = new GamePlayManager(app);
        currentLevel = new GameLevel(gamePlayManager, worldManager, "Level_Default", null);
        worldManager.initWorld(currentLevel);
        selectManager = new SelectManager(this.gameGUIManager, this, getWorldManager());
        selectManager.init();
        addSubManager(worldManager);
        addSubManager(gamePlayManager);
        addSubManager(gameGUIManager);
    }
    // ======== LOAD STAGE=============

    /**
     * Common implementation of a game Stage loading
     */
    public void loadSettings() {
    }

    public void loadData() {
    }

    public void loadProfile() {
    }

    /**
     * Common implementation of a game Stage loading with monitoring
     */
    public void loadStage() {
        currentProgress.currentProgressName = "Settings";
        currentProgress.currentProgressPercent = 0.1f;
        loadSettings();
        currentProgress.currentProgressName = "Data";
        currentProgress.currentProgressPercent = 0.2f;
        loadData();
        currentProgress.currentProgressName = "Profile";
        currentProgress.currentProgressPercent = 0.3f;
        loadProfile();
        currentProgress.currentProgressName = "World";
        currentProgress.currentProgressPercent = 0.4f;
        worldManager.loadWorld();
        currentProgress.currentProgressName = "Gameplay";
        currentProgress.currentProgressPercent = 0.8f;
        gamePlayManager.loadGamePlay();

    }

    // ======== CONFIG & SETUP STAGE=============
    void setupKeys() {
    }

    public void configStage() {
        worldManager.configWorld();
        configStageCustom();
    }

    public void configStageCustom() {
        configFlyCam();
        configViewPort();
    }

    public void configFlyCam() {
    }

    public void configViewPort() {
    }
    // ======== ATTACH STAGE=============

    public void attachStage() {
        Logger.getLogger(StageManager.class.getName()).log(Level.INFO, "Atom: Attach Stage!");
        worldManager.attachWorld();
    }

    // ======== UPDATE STAGE=============
    public void updateStage(float tpf) {
        // Game paused then stop update the logic!
        if (!gamePaused) {
            time += tpf;
            worldManager.simpleUpdate(tpf);
            updateStageCustom(tpf);
        }
    }

    public void updateStageCustom(float tpf) {
        gamePlayManager.update(tpf);
    }
    // ======== FINISH STAGE=============

    public void finishStage() {

        worldManager.finishWorld();
        //screenEffectManager.setupFilter();
        //setupScreenEffectKeys();
        finishStageCustom();
        finished = true;

    }

    public void finishStageCustom() {
        gamePlayManager.configGamePlay();
    }

    public void doReadyToPlay() {
        if (!finished) {
            finishStage();
        }
        gamePlayManager.startLevel(currentLevel);
    }

    // MONITORING
    public boolean isReadyToPlay() {
        return finished;
    }

    public boolean isCompleted(String phase, String actionName) {
        return finished;
    }

    public boolean isCompleted(String phaseName) {
        return finished;
    }

    public float getProgressPercent(String phaseName) {
        return 0;
    }

    public ProgressInfo getCurrentProcess() {
        return currentProgress;
    }

    // ======== SETTER & GETTER =============
    public AtomMain getApp() {
        return this.app;
    }

    public Camera getCurrentActiveCamera() {
        return app.getCamera();
    }

    public ViewPort getCurrentActiveViewPort() {
        return viewPort;
    }

    public GameEffectManager getGameEffectManager() {
        return this.effectManager;
    }

    public WorldManager getWorldManager() {
        return this.worldManager;
    }

    public GamePlayManager getGamePlayManager() {
        return gamePlayManager;
    }

    public <T extends GamePlayManager> T getGamePlayManager(Class<T> clazz) {
        return (T) gamePlayManager;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public AudioRenderer getAudioRenderer() {
        return app.getAudioRenderer();
    }

    public Camera getCamera() {
        return app.getCamera();
    }

    public SelectManager getSelectManager() {
        return selectManager;
    }

    public boolean isInited() {
        return initedStatus;
    }

    public InputManager getInputManager() {

        return getApp().getInputManager();
    }

    public AssetManager getAssetManager() {
        return getApp().getAssetManager();
    }

    public Node getRootNode() {
        return getWorldManager().getRootNode();
    }

    public AppSettings getSettings() {
        return getApp().getSettings();
    }

    public GameGUIManager getGUIManager() {
        return gameGUIManager;
    }

    public float getTime() {
        return time;
    }
    //===CYCLE============

    @Override
    public void init() {
        initStage();
    }

    @Override
    public void load() {
        loadStage();
    }

    @Override
    public void config(Properties props) {
        configStage();
    }

    @Override
    public void update(float tpf) {
        updateStage(tpf);
    }

    @Override
    public void finish() {
        finishStage();
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
