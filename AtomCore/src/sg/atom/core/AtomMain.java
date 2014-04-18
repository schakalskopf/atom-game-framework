package sg.atom.core;

import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ExecutionList;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.system.AppSettings;
import java.io.InputStream;
import java.util.Properties;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.core.timing.GameTimer;
import sg.atom.stage.SoundManager;
import sg.atom.stage.StageManager;
import sg.atom.state.common.LoadingAppState;
import sg.atom.ui.GameGUIManager;

/**
 * AtomMain is an extended version of
 * <code>SimpleApplication</code> to support more Game-related functions and
 * features for the basic JME3 Application. This is the central of Atom
 * framework.
 *
 * <p> The examples show the power of this architect, learnt from a lot of
 * others (big to small, successed and failed).
 *
 * <p> The re-work features: A few differencies has to be distinguish between
 * AtomMain and the normal SimpleApplicaion: <ul>
 *
 * <li>It's not only rely on AppTask, to enable more complex concurent
 * algorimth.</li>
 *
 * <li>Its not just rely on ContextListener timing; Its also attend in another
 * cycle; Its also can ignore completely the underlying game timer with
 * Monitoring magic.</li>
 *
 * <li>Its accepts other Renderers, has another StateManager, AssetManager
 * specification.</li>
 *
 * <li>Its accepts various kind of input in event style.</li> </ul>
 *
 * <p> The unique features:
 *
 * <ul><li>Support generic-ports to hook in the underlying mechanics</li>
 *
 * <li>Stage and Actor paradigm, inspired by Cinematograpy, in which modern
 * video games rely on. Actor also refered as Agent/ManagedEntity and Stage as
 * Enviroment/Context/Domain in other implementation and papers.</li>
 *
 * <li>Support (optional) Entity - Manager mechanic</li>
 *
 * <li>Support (optional) State pattern</li>
 *
 * <li>Support easy-optional Network and Multiplayer</li>
 *
 * <li>Support monitoring and testing</li></ul>
 *
 *
 *
 * <p>Design decision:<ul> <li>Use Guava and Guice heavily!</li>
 *
 * <li>Maximizing usage of design pattern! [But not eliminate creativity]</li>
 * </ul></p>
 *
 * <p><b>Note:</b>To get deeper into low level, use AtomPlatform instead. It is
 * an Application which redefined the tasks, the states and the game cycles of
 * JME3 context! </p>
 *
 * @author Atomix
 */
public class AtomMain extends SimpleApplication implements IGameCycle {

    //FIXME: Open the usage of "classical" Singleton (optional). Should use non-block negative Singleton or Guice to instantiate this instead!
    @Inject
    public static AtomMain defaultInstance = null;
    //Shortcut
    @Inject
    protected GameGUIManager gameGUIManager;
    @Inject
    protected StageManager stageManager;
    @Inject
    protected GameStateManager gameStateManager;
    @Inject
    protected SoundManager soundManager;
    // Management and monitoring. 
    protected GameTimer internalGameTimer;
    protected Properties properties;
    // Guava&Guice Era!
    protected EventBus eventBus;
    protected Guice guice;
    /**
     * ExecutionList. Tend to replace the app.queue which is a poor
     * implementation of concurrent.
     */
    protected ExecutionList executionList;

    /**
     * Enable "classical" simple singleton!
     */
    protected AtomMain() {
    }

    public AtomMain(GameTimer internalGameTimer, Properties properties) {
        this.internalGameTimer = internalGameTimer;
        this.properties = properties;
    }

    @Override
    public void simpleInitApp() {
        init();
    }

    public void startup() {
        gameStateManager.setStartupState(LoadingAppState.class);
        gameStateManager.startUp();
        eventBus = new EventBus("Atom framework EventBus");
        config(properties);
    }

    public void initGameStateManager() {
        if (gameStateManager == null) {
            gameStateManager = new GameStateManager(this);
        }
        gameStateManager.initState();
        // NOTE: Can also call gameStateManager.init();
    }

    /**
     * Quick mode is the mode for development phase
     */
    public void initQuickStart() {
        inputManager.addMapping("quickStart", new KeyTrigger(KeyInput.KEY_F12));
        inputManager.addListener(quickStartManager, "quickStart");
    }
    private ActionListener quickStartManager = new ActionListener() {
        boolean quickStart = false;

        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("quickStart")) {
                if (!quickStart) {
                    quickStart = true;
                    //gameStateManager.enterInGame();
                }
            }
        }
    };

    public void initGUI() {
        if (gameGUIManager == null) {
            gameGUIManager = new GameGUIManager(this);
        }
        gameGUIManager.init();
    }

    public void initStage() {
        if (stageManager == null) {
            stageManager = new StageManager(this);
        }
        stageManager.init();
        // NOTE: Can also call stageManager.init();
    }

    public void initSound() {
        this.soundManager = new SoundManager(this);
        soundManager.init();
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO:: add update code
        // Empty implementation!
    }

    //============ CONFIGS =======================
    public void applySettings(Properties props) {
        // Empty implementation!
    }
//
//    public void applySettings(Object rawSetting) {
//    }
//
//    public void applySettings(InputStream externalStream) {
//    }
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
        this.stop();
    }

    public void quitGame() {
        this.stop();
    }

    /**
     * NOTE: FAKE SINGLETON PATTERN.
     */
    public AtomMain getDefaultInstance() {
        return defaultInstance;
    }

    @Override
    public void init() {
        // Init Globals singleton
        
        // Init GameStateManager
        initGameStateManager();
        // Init all first class Managers
        
        // Finally startup
        startup();
        
    }

    @Override
    public void load() {
        // If bootstrap load?8
        
        // If network load?
        
        // If pre-stage/world load?
        
        // If editor load?
        
        // If reload or streaming continue?
        
        // If networks extra load?
        
        // If monitors ask for workload done, or budget exceeded?
    }

    @Override
    public void config(Properties props) {
        
        // Load default configs
        
        // If auto config mode
        
        // If in development mode?
        
        // If enable ingame editor?
        
        // If enable hot asset reload?
        
        // Finish configs and try to load
        
        
    }

    @Override
    public void update(float tpf) {
        //
    }

    /**
     * This may be call to destroy all the managers and resources depend on the game
     */
    @Override
    public void finish() {
        // Release all managers
        
        // Release all resources
    }

    @Override
    public LifeCyclePhase getCurrentPhase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float getProgressPercent(LifeCyclePhase aPhrase) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
