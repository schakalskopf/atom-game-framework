package sg.atom.ui;

import sg.atom.stage.SoundManager;
import sg.atom.stage.StageManager;
import com.jme3.asset.AssetManager;
import com.jme3.asset.DesktopAssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.audio.AudioRenderer;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ElementRenderer;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.atom.core.AtomMain;

/**
 *
 * @author hungcuong
 */
public abstract class GameGUIManager {

    protected AtomMain app;
    protected boolean enabled = false;
    boolean niftyStarted = false;
    protected StageManager stageManager;
    protected BitmapFont guiFont;
    protected Node guiNode;
    protected InputManager inputManager;
    protected AudioRenderer audioRenderer;
    protected ViewPort guiViewPort;
    protected AssetManager assetManager;
    protected NiftyJmeDisplay niftyDisplay;
    protected Nifty nifty;
    protected HashMap<String, NiftyScreenPath> commonScreens = new HashMap<String, NiftyScreenPath>();

    public class NiftyScreenPath {

        String filePath;
        boolean loaded = false;

        public NiftyScreenPath(String filePath) {
            this.filePath = filePath;
        }
    }

    public GameGUIManager(AtomMain app) {
        //Logger.getLogger("").setLevel(Level.OFF);
        this.app = app;
        this.guiNode = app.getGuiNode();
        this.inputManager = app.getInputManager();
        this.audioRenderer = app.getAudioRenderer();
        this.guiViewPort = app.getGuiViewPort();
        this.assetManager = app.getAssetManager();
        this.stageManager = app.getStageManager();
    }

    public void initGUI() {
        initJMEGUI();
        initNiftyGUI();
        disableLogger();
        setupCommonScreens();
        initCursors();
    }

    private void disableLogger() {
        Logger.getLogger("de.lessvoid.nifty").setLevel(Level.WARNING);
        Logger.getLogger("NiftyInputEventHandlingLog").setLevel(Level.WARNING);
        Logger.getLogger("NiftyEventBusLog").setLevel(Level.WARNING);
        Logger.getLogger("NiftyImageManager").setLevel(Level.WARNING);
    }

    public void setupCommonScreens() {
    }

    public void initCursors() {
    }

    void initNiftyGUI() {
        niftyDisplay = new NiftyJmeDisplay(assetManager,
                inputManager,
                audioRenderer,
                guiViewPort);
        nifty = niftyDisplay.getNifty();

        attachNifty();
    }

    public void attachNifty() {
        // attach the nifty display to the gui view port as a processor
        if (!enabled) {
            guiViewPort.addProcessor(niftyDisplay);
            enabled = true;
        }
    }

    public void detachNifty() {
        if (enabled) {
            guiViewPort.removeProcessor(niftyDisplay);
            enabled = false;
        }
    }

    void initJMEGUI() {
        inputManager.setCursorVisible(true);
        initStat();
        // disable the fly cam
        //flyCam.setEnabled(true);
//        flyCam.setDragToRotate(true);
        //inputManager.setCursorVisible(true);
    }

    void initStat() {
        //loadStatsView();
        //loadFPSText();
        //app.setDisplayStatView(true);
        //app.setDisplayFps(true);
    }

    public void startNifty() {
    }

    protected void initCrossHairs() {
        AppSettings settings = app.getSettings();
        guiFont = assetManager.loadFont("aurulent-sans-16.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // crosshairs
        ch.setLocalTranslation( // center
                settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
                settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);
    }

    public <T extends Controller> T getQuickUIController(String elementId, Class<T> clazz) {
        return nifty.getCurrentScreen().findControl(elementId, clazz);
    }

    public <T extends ElementRenderer> T getRenderer(Class<T> aClass, String path)
            throws IllegalArgumentException {
        String[] array = path.split("/");

        if (array.length < 2) {
            throw (new IllegalArgumentException(" This path is wrong : " + path));
        }
        Element thisElement, parent;
        Screen thisScene = nifty.getScreen(array[0]);
        thisElement = thisScene.findElementByName(array[1]);

        for (int i = 1; i < array.length; i++) {
            String thisName = array[i];
            parent = thisElement;
            thisElement = parent.findElementByName(thisName);

        }
        return thisElement.getRenderer(aClass);
    }

    public Element getElementByPath(String path)
            throws IllegalArgumentException {
        String[] array = path.split("/");

        if (array.length < 2) {
            throw (new IllegalArgumentException(" This path is wrong : " + path));
        }
        Element thisElement, parent;
        Screen thisScene = nifty.getScreen(array[0]);
        thisElement = thisScene.findElementByName(array[1]);

        for (int i = 1; i < array.length; i++) {
            String thisName = array[i];
            parent = thisElement;
            thisElement = parent.findElementByName(thisName);

        }
        return thisElement;
    }

    // Navigation functions
    public void loadAndGotoScreen(String screenId) {
        loadScreen(screenId);
        goToScreen(screenId);
    }

    public void loadScreen(String screenId) {
        NiftyScreenPath path = (NiftyScreenPath) commonScreens.get(screenId);

        if (path == null) {
            throw new IllegalArgumentException("No screen got the ID :" + screenId);
        }
        loadPath(path);
    }

    public void loadPath(NiftyScreenPath path) {
        if (!path.loaded) {
            String filePath = path.filePath;
            nifty.addXml(filePath);
            path.loaded = true;
        }
    }

    public void setNiftyImage(Texture tex, String textureName, ImageRenderer iR) {
        ((DesktopAssetManager) assetManager).addToCache(new TextureKey(textureName), tex);
        NiftyImage img = nifty.getRenderEngine().createImage(null, textureName, false);
        iR.setImage(img);
    }

    public void goToScreen(String screenName) {
        if (nifty.getScreen(screenName) != null) {
            nifty.gotoScreen(screenName);
            Logger.getLogger(GameGUIManager.class.getName()).log(Level.INFO, "Go to {0}", screenName);
        } else {
            loadAndGotoScreen(screenName);
        }
    }
    /*SETTER & GETTER*/

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public SoundManager getSoundManager() {
        return app.getSoundManager();
    }

    public AtomMain getApp() {
        return app;
    }

    public StageManager getStageManager() {
        return app.getStageManager();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public Node getGuiNode() {
        return guiNode;
    }

    public Nifty getNifty() {
        return nifty;
    }
}
