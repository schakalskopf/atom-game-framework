package sg.atom.ui;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.MutableClassToInstanceMap;
import com.google.common.collect.TreeTraverser;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.ServiceManager;
import com.google.inject.Inject;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;
import sg.atom.core.AbstractManager;
import sg.atom.core.AtomMain;
import sg.atom.stage.SoundManager;
import sg.atom.stage.StageManager;
import sg.atom.ui.common.GUIScreenAction;
import sg.atom.ui.services.nifty.NiftyGUIService;
import sg.atom.ui.systems.GUIElement;
import sg.atom.ui.systems.GUIScreenInfo;
import sg.atom.ui.systems.GUIStyle;
import sg.atom.ui.systems.GUISystemService;

/**
 * GameGUIManager manage all GUI related stuffs like Screen, Currors, GUI Input.
 *
 * <p><b>General: Presumes of simplest version of any GUI out there:</b> <ul>
 *
 * <li>GUI behaviors should be "formal" that mean user input are translated by a
 * "formal system" to be understand by GUI and the reaction is also
 * "expectable".
 *
 * <li>GUI appearance begged to be be colorful and attrative for game
 * enviroments. That's why a generic cacade style system exits.
 *
 * <li>GUI interaction under a global mode/state/screen. That's it, they only
 * take meaningful interaction of visible-pioritized component at a specific
 * time.</ul>
 *
 * <p><b>Features:</b>
 *
 * <p>From 1.1, can also work with SimpleApplication with limited features.
 *
 * <p>Integrate with Stage, GameState, Gameplay systems. It's driven by Stage
 * and GameState mostly. Gameplay access it via Stage or Singlton.
 *
 * <p>Manage list of screen's path and its related resource such as images,
 * sounds...etc. Enable creating, loading, removing GUI resource properly.
 *
 * <p>Support automatic screen localization and input for multi language. Handle
 * own events. Enable bean binding and mapping or functional reactive programming?
 *
 * <p>Support cache other than AssetManager
 *
 * <p>Support effects, translate between 2D->3D, automatic transistion between
 * screens.
 *
 * <p>NEW: Intercept screen/controller interaction and transistion of underlying
 * GUI library (for debug for ex).
 *
 * <p>FIXME: Support better monitoring and events
 *
 * <p>FIXME: Support other GUI libs!
 *
 * <p><b>Note: Design choice </b>In the ideal world, all elements between
 * different GUI library can be display, interact together. This partial
 * supported via EventBus. The formal GUI interfaceses are not stable yet but
 * usable. However, Underlying OpenGL GUI is not much different (with other
 * types of data) with Spatial as their are also 3D objects. The different
 * caused by the implementation of the GUI system in JME3 mixed between native
 * and external (service). For ex: NiftyGUI vs TonegodGUI and Lemur... This is
 * the bridge between them with no assumption about styles or the way they
 * display. Trade off, NiftyGUI for example already builted with service
 * mechanism, add another layers is not a wise move in term of performance but
 * to make Atom a truely extensiable framework, this has to be done!
 *
 * @author atomix
 */
public class GameGUIManager extends AbstractManager {

    protected static final Logger logger = Logger.getLogger(GameGUIManager.class.getName());
    protected AtomMain app;
    protected SimpleApplication simpleApp;
    protected StageManager stageManager;
    protected InputManager inputManager;
    protected AudioRenderer audioRenderer;
    protected AssetManager assetManager;
    protected Node guiNode;
    protected ViewPort guiViewPort;
    //GUI services and implementations
    @Inject
    protected ServiceManager serviceManager;
    @Inject
    protected MutableClassToInstanceMap<GUISystemService> services;
    @Inject
    protected BiMap<TypeToken, GUISystemService> registeredGUIImpls;
    // State management & monitoring. Global states
    protected static boolean intercepted = true;
    protected static boolean enabled = false;
    protected static boolean guiStarted = false;
    protected static boolean nullSafe = true;
    protected static boolean debugMode = false;
    public String currentMode;
    protected Set<String> modes = new HashSet<String>();
    // Screen management
    //FIXME: Replace with Concurent map.
    protected HashMap<String, GUIScreenInfo> commonScreens = new HashMap<String, GUIScreenInfo>();
    protected HashMap<GUIScreenInfo, GUISystemService> commonScreensService = new HashMap<GUIScreenInfo, GUISystemService>();
    //private HashMap<String, JmeCursor> cursors = new HashMap<String, JmeCursor>();
    protected List<BitmapFont> managedFonts;
    protected List<GUIStyle> managedStyles;
    /**
     * The tree of the elements. This particular view is unnessary. Going to be
     * removed and use TreeTraverer instead.
     */
    //protected Tree<GUIElement> elementTree;
    protected TreeTraverser<GUIElement> treeTraverser;
    //Common stuffs
    protected BitmapFont guiFont;

    protected GameGUIManager() {
        // For use in singleton!
    }

    @Inject
    public GameGUIManager(SimpleApplication app) {
        //Logger.getLogger("").setLevel(Level.OFF);
        lazyInit(app);
    }

    @Inject
    public GameGUIManager(AtomMain app) {
        //Logger.getLogger("").setLevel(Level.OFF);
        lazyInit(app);
    }

    public final void lazyInit(SimpleApplication app) {
        this.guiNode = app.getGuiNode();
        this.inputManager = app.getInputManager();
        this.audioRenderer = app.getAudioRenderer();
        this.guiViewPort = app.getGuiViewPort();
        this.assetManager = app.getAssetManager();

        if (app instanceof AtomMain) {
            this.app = (AtomMain) app;
            this.stageManager = ((AtomMain) app).getStageManager();
        } else {
            this.simpleApp = app;
        }
    }

    @Override
    public void init() {
        initGUI();
        initCustom();
    }

    @Override
    public void load() {
        for (GUISystemService service : services.values()) {
            service.load();
        }
    }

    @Override
    public void config(Properties props) {
    }

    @Override
    public void update(float tpf) {
        //Update the action queue.
        //Update services
        for (GUISystemService service : services.values()) {
            service.update(tpf);
            //serviceManager.
        }
    }

    @Override
    public void finish() {
        for (GUISystemService service : services.values()) {
            service.finish();
            //serviceManager.
        }
    }

    @Override
    public LifeCyclePhase getCurrentPhase() {
        return null;
    }

    @Override
    public float getProgressPercent(LifeCyclePhase aPhrase) {
        return 0;
    }

    public void initGUI() {
        initJMEGUI();
        initGUIServices();
        disableLogger();
    }

    public void initGUIServices(GUISystemService... servicesArray) {
        //FIXME: If there is no GUI service registered. We going to use Nifty as Default GUI!
        if (servicesArray == null) {
        } else {
            servicesArray = new GUISystemService[]{new NiftyGUIService(this)};
        }
        services = MutableClassToInstanceMap.create();

        registeredGUIImpls = HashBiMap.create();
        //FIXME: Load all the GUISystemService
        for (GUISystemService service : servicesArray) {
            services.put(service.getClass(), service);
            service.init();
            registeredGUIImpls.put(service.getTypeToken(), service);
            //FIXME: Init service Manager;
        }


    }

    public void initCustom() {
    }

    public void applyCascadingStyles() {
        //travel the tree.
        //for one node. rule of styles in ruleset are check (RETE can help here). 
        //filtered result in true or false: match the rule or not?
        //if true apply to it and all its children (include ancestor) the style.
        //if false none of its children will be matched with some sepecific styles. (Pruning)
    }
    /* JME GUI functions */

    void initJMEGUI() {
        inputManager.setCursorVisible(true);
        guiFont = assetManager.loadFont("aurulent-sans-16.fnt");
        //initStat();
    }

    void initStat() {
        //loadStatsView();
        //loadFPSText();
        //app.setDisplayStatView(true);
        //app.setDisplayFps(true);
    }

    public void startNifty() {
    }
    /* Common task */

    public void disableFlyingCam() {
        // disable the fly cam
        //flyCam.setEnabled(false);
        //flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);
    }

    private void disableLogger() {
    }

    // Fonts and styles ========================================================
    public void registerFont(String fontName, String path) {
    }

    public Set<BitmapFont> getAvailableFonts() {
        return null;
    }

    public Set<String> getAvailableFontsName() {
        return null;
    }

    protected void initCrossHairs() {
        AppSettings settings = app.getSettings();

        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // crosshairs
        ch.setLocalTranslation( // center
                settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
                settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);
    }

//    public void enqueueActionWithScreenController(Class<? extends ScreenController> aClass, ScreenAction screenAction) {
//        // wait until valid.
//    }
    public void enqueueAction(GUIScreenAction screenAction) {
        // just remove from list.
    }
    // Screen management methods =====================================================================

//    public <T extends ScreenController> T getCurrentScreenController(String screenName, Class<T> clazz) {
//        Screen currentScreen = nifty.getCurrentScreen();
//        if (currentScreen.getScreenId().equals(screenName)) {
//            return (T) currentScreen.getScreenController();
//        } else {
//            return null;
//        }
//    }
//
//    public ScreenController getCurrentScreenController() {
//        return nifty.getCurrentScreen().getScreenController();
//    }
//
//    public <T extends ScreenController> T getCurrentScreenController(Class<T> clazz) {
//        return (T) getCurrentScreenController();
//    }
//
//    public <T extends Controller> T getQuickUIController(String elementId, Class<T> clazz) {
//        return nifty.getCurrentScreen().findControl(elementId, clazz);
//    }
    // Screen Navigation ---------------------------------------------------
    public void registerScreen(NiftyGUIService niftyService, String screenName, String screenPath) {
        registerScreen(niftyService, screenName, new GUIScreenInfo(screenPath));
    }

    public void registerScreen(GUISystemService registeredService, String screenName, GUIScreenInfo screenPath) {
        commonScreens.put(screenName, screenPath);
        commonScreensService.put(screenPath, registeredService);
    }

    public GUISystemService getRegisteredScreenService(GUIScreenInfo screenPath) {
        return commonScreensService.get(screenPath);
    }

    public void loadAndGotoScreen(String screenId) {
        loadScreen(screenId);
        goToScreen(screenId);
    }

    public void goToScreen(String screenName) {
        //FIXME: Intercept goto
//        if (nifty.getScreen(screenName) != null) {
//            nifty.gotoScreen(screenName);
//            Logger.getLogger(GameGUIManager.class.getName()).log(Level.INFO, "Go to {0}", screenName);
//        } else {
//            loadAndGotoScreen(screenName);
//        }
    }

    public void loadScreen(String screenId) {
        GUIScreenInfo path = (GUIScreenInfo) commonScreens.get(screenId);

        if (path == null) {
            throw new IllegalArgumentException("No screen got the ID :" + screenId);
        }
        loadPath(path);
    }

    public void loadPath(GUIScreenInfo path) {
        if (!path.loaded) {
            String filePath = path.filePath;
            getRegisteredScreenService(path).loadPath(filePath);
            path.loaded = true;
        }
    }

    /**
     * Assume there is an update beat!
     */
    public void refreshChanges() {
    }
    // Styles management methods =====================================================================

    /*SETTER & GETTER & Short cuts */
    public static void setEnabled(boolean enabled) {
        GameGUIManager.enabled = enabled;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public SoundManager getSoundManager() {
        return app.getSoundManager();
    }

    public AtomMain getApp() {
        return app;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public AudioRenderer getAudioRenderer() {
        return audioRenderer;
    }

    public BitmapFont getGuiFont() {
        return guiFont;
    }

    public ViewPort getGuiViewPort() {
        return guiViewPort;
    }

    public Node getGuiNode() {
        return guiNode;
    }

    public synchronized <T> T getGUI(Class<T> clazz) {// throws IllegalAccessException {

        TypeToken<T> token = TypeToken.of(clazz);
        GUISystemService matchedService = registeredGUIImpls.get(token);

        T result = (T) matchedService.getGUISystemInstance(this);
        if (result == null) {
            //throw new IllegalAccessException("You did not register this GUI system yet! Valid supported GUI systems are:");
        }

        return result;
    }

    public synchronized <T extends GUISystemService> T getGUIService(Class<T> clazz) {// throws IllegalAccessException {
        GUISystemService matchedService = services.get(clazz);

        T result = (T) matchedService;
        if (result == null) {
            //throw new IllegalAccessException("You did not register this GUI system yet! Valid supported GUI systems are:");
        }

        return result;
    }

    public ServiceManager getServiceManager() {
        return serviceManager;
    }

    public StageManager getStageManager() {
        if (app != null) {
            return app.getStageManager();
        } else {
            throw new RuntimeException("No valid link to StageManager. May be you use SimpleApplication instead of AtomMain?");
        }
    }

    public SimpleApplication getSimpleApplication() {
        if (simpleApp != null) {
            return simpleApp;
        } else {
            throw new RuntimeException("No valid link to SimpleApplication. May be you use AtomMain instead of SimpleApplication?");
        }
    }
    //Debug=====================================================================

    public static boolean isDebugMode() {
        return GameGUIManager.debugMode;
    }

    public static boolean isIntercepted() {
        return GameGUIManager.intercepted;
    }

    public static boolean isNullSafe() {
        return GameGUIManager.nullSafe;
    }
}
