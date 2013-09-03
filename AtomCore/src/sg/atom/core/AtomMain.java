package sg.atom.core;

import sg.atom.ui.GameGUIManager;
import sg.atom.stage.SoundManager;
import sg.atom.stage.StageManager;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.system.AppSettings;
import sg.atom.state.LoadingAppState;

/**
 * @author Atomix
 */
/**
 * This class is an extended version of <code>SimpleApplication</code> to support more Game-related functions and features for the basic JME3 Application.<br>
 * The examples show the power of this architect, learnt from a lot of others (big to small, successed and failed). <br>
 * The features:<br>
 * <ul>
 * <li>Support has some generic-ports to hook in the under mechanic</li>
 * <li>Support (optional) Entity - Manager mechanic</li>
 * <li>Support (optional) State pattern</li>
 * <li>Support easy-optional Network and Multiplayer</li>
 * </ul>
 */
public class AtomMain extends SimpleApplication {

    protected static AtomMain app;
    protected GameGUIManager gameGUIManager;
    protected StageManager stageManager;
    protected GameStateManager gameStateManager;
    protected SoundManager soundManager;

    @Override
    public void simpleInitApp() {
        renderManager.setAlphaToCoverage(true);
        initGameStateManager();
        startup();
    }

    public void startup() {
        gameStateManager.setStartupState(LoadingAppState.class);
        gameStateManager.startUp();
    }

    public void initGameStateManager() {
        gameStateManager = new GameStateManager(this);
        gameStateManager.initState();
    }

    void initQuickStart() {
        inputManager.addMapping("quickStart", new KeyTrigger(KeyInput.KEY_F1));
        inputManager.addListener(quickStartManager, "quickStart");
    }
    private ActionListener quickStartManager = new ActionListener() {
        boolean quickStart = false;

        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("quickStart")) {
                if (!quickStart) {
                    quickStart = true;
                    gameStateManager.enterInGame();
                }
            }
        }
    };

    public void initGUI() {
        gameGUIManager = new GameGUIManager(this) {};
        gameGUIManager.initGUI();
    }

    public void initStage() {
        stageManager = new StageManager(this);
        stageManager.initStage();
    }

    public void initSound() {
        this.soundManager = new SoundManager(this);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    // =========== GETTER & SETTER ===============
    public AppSettings getSettings() {
        return settings;
    }

    public GameGUIManager getGameGUIManager() {
        return gameGUIManager;
    }

    public StageManager getStageManager() {
        return this.stageManager;
    }

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }    
    
    public SoundManager getSoundManager() {
        return soundManager;
    }
    // =========== SHORT CUT FUNCTION ( for console command) ==============

    public void quit() {
        app.stop();
    }

    public void quitGame() {
        app.stop();
    }
}
