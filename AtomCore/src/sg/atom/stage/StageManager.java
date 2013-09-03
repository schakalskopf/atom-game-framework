package sg.atom.stage;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.atom.core.AtomMain;
import sg.atom.ui.GameGUIManager;
import sg.atom.core.lifecycle.ProcessInfo;
import sg.atom.entity.EntityManager;
import sg.atom.fx.GameEffectManager;
import sg.atom.fx.ScreenEffectManager;
import sg.atom.gameplay.GameLevel;
import sg.atom.gameplay.GamePlayManager;
import sg.atom.stage.SelectManager;
import sg.atom.stage.WorldManager;

/**
 * <i>Stage is the place where Actors do the performance.</i><br> StageManager in charge for all aspects of the game
 * <br>(1) Input, action, logic and stragegy of the game<br> (2)Control the camera and cinematic , also manage game
 * data,gameplay and players.<br>
 *
 * <p>It's a high level manager and doesn't directly do render and display jobs. Of course, it has sub-Manager(s) to
 * help the job done in specific aspects <ul> <li> initStage : prepare all the sub-Manager(s)</li> <li> loadStage : load
 * the World so it's ready to be attached to the display</li> <li> configStage : extract infos from Level and entities
 * and the links between them</li> <li> attachStage : simple Attach things finishStage : finish touch</li> <li>
 * updateStage : update call of AppState and update all sub-Manager</li> <li> destroyStage : destroy and free everything
 * needed</li> </ul></p>
 */
public class StageManager {

    // short cut link
    protected AtomMain app;
    protected AssetManager assetManager;
    protected InputManager inputManager;
    protected WorldManager worldManager;
    protected GameGUIManager gameGUIManager;
    protected RenderManager renderManager;
    protected Node rootNode;
    protected Node guiNode;
    protected GameEffectManager effectManager;
    protected ViewPort viewPort;
    protected ArrayList<GameLevel> gameLevelList = new ArrayList<GameLevel>();
    protected GameLevel currentLevel;
    protected GamePlayManager gamePlayManager;
    protected boolean initedStatus;
    protected boolean finished;
    protected boolean gamePaused;
    protected boolean resume;
    protected ScreenEffectManager screenEffectManager;
    protected EntityManager entityManager;
    protected ProcessInfo currentProcess = new ProcessInfo("None");
    protected SelectManager selectManager;
    protected float time = 0;
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

    public ProcessInfo getProcessInfo() {
        return currentProcess;
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
    }
    // ======== LOAD STAGE=============

    public void loadSettings() {
    }

    public void loadData() {
    }

    public void loadProfile() {
    }

    public void loadStage() {
        currentProcess.currentProgressName = "Settings";
        currentProcess.currentProgressPercent = 0.1f;
        loadSettings();
        currentProcess.currentProgressName = "Data";
        currentProcess.currentProgressPercent = 0.2f;
        loadData();
        currentProcess.currentProgressName = "Profile";
        currentProcess.currentProgressPercent = 0.3f;
        loadProfile();
        currentProcess.currentProgressName = "World";
        currentProcess.currentProgressPercent = 0.4f;
        worldManager.loadWorld();
        currentProcess.currentProgressName = "Gameplay";
        currentProcess.currentProgressPercent = 0.8f;
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

    void configFlyCam() {
        //this.app.getFlyByCamera().setMoveSpeed(50f);
        //inputManager.setCursorVisible(false);
        //flyCam.setDragToRotate(false);
    }

    void configViewPort() {
        //viewPort.setBackgroundColor(ColorRGBA.Blue);
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

    public boolean isReadyToPlay() {
        return finished;
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
    
}
