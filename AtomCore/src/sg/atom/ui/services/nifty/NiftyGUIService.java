/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ui.services.nifty;

import com.google.common.collect.TreeTraverser;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.google.inject.Inject;
import com.jme3.asset.AssetManager;
import com.jme3.asset.DesktopAssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import com.jme3.texture.Texture;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ElementRenderer;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.layout.Box;
import de.lessvoid.nifty.layout.LayoutPart;
import de.lessvoid.nifty.loaderv2.types.RegisterEffectType;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.atom.ui.GameGUIManager;
import sg.atom.ui.systems.GUIElement;
import sg.atom.ui.systems.GUIInteraction;
import sg.atom.ui.systems.GUIScreenInfo;
import sg.atom.ui.systems.GUISystemService;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class NiftyGUIService extends AbstractExecutionThreadService implements GUISystemService<Nifty, Element> {
    // FIXME: Nifty only.

    protected NiftyJmeDisplay niftyDisplay;
    protected Nifty nifty;
    protected boolean niftyStarted = false;
    private InputManager inputManager;
    private AudioRenderer audioRenderer;
    private ViewPort guiViewPort;
    private AssetManager assetManager;

    public NiftyGUIService(GameGUIManager guiManager) {
        this.inputManager = guiManager.getInputManager();
        this.audioRenderer = guiManager.getAudioRenderer();
        this.guiViewPort = guiManager.getGuiViewPort();
        this.assetManager = guiManager.getAssetManager();
    }

    @Override
    protected void run() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // ===Ultilites that Nifty should have!============================================
    /**
     * Ultility method to create a NiftyImage from a JME3's Texture
     *
     * @param tex
     * @param textureName
     * @param iR
     */
    public void setNiftyImage(Texture tex, String textureName, ImageRenderer iR) {
        ((DesktopAssetManager) assetManager).addToCache(new TextureKey(textureName), tex);
        NiftyImage img = nifty.getRenderEngine().createImage(null, textureName, false);
        iR.setImage(img);
    }

    /**
     * Ultility to safety setText in Nifty Elements
     */
    public static void setText(Element el, String text) {
        el.getRenderer(TextRenderer.class).setText(text);
    }

    public void setText(String elId, String text) {
        nifty.getCurrentScreen().findElementByName(elId).getRenderer(TextRenderer.class).setText(text);
    }

    public static void setImage(Element el, NiftyImage image) {
        el.getRenderer(ImageRenderer.class).setImage(image);
    }

    /**
     * Ultility to safety navigate through screens.
     *
     * It can be intercept for tesing purpose.
     *
     * @param screenName
     */
    public void goToScreen(String screenName) {
        //FIXME: Intercept goto

        if (nifty.getScreen(screenName) != null) {
            nifty.gotoScreen(screenName);
            Logger.getLogger(GameGUIManager.class.getName()).log(Level.INFO, "Go to {0}", screenName);
        } else {
            //loadAndGotoScreen(screenName);
        }
    }

    @Override
    public Nifty getGUISystemInstance(GameGUIManager manager) {
        return nifty;
    }

    public void setupCommonEffects() {
        nifty.registerEffect(new RegisterEffectType("imageOverlayPulsateBlend", "sg.atom.ui.common.effects.ImageOverlayPulsateBlend"));
        nifty.registerEffect(new RegisterEffectType("motionAxis", "sg.atom.ui.common.effects.MotionAxis"));
    }

    public void setupCommonStyles() {
    }

    public void setupCommonScreens() {
    }

    @Inject
    public void initGUI(AssetManager assetManager,
            InputManager inputManager,
            AudioRenderer audioRenderer,
            ViewPort guiViewPort) {

        niftyDisplay = new NiftyJmeDisplay(assetManager,
                inputManager,
                audioRenderer,
                guiViewPort);
        nifty = niftyDisplay.getNifty();
        this.inputManager = inputManager;
        this.audioRenderer = audioRenderer;
        this.guiViewPort = guiViewPort;
        this.assetManager = assetManager;
        attachNifty();
    }
    /* Nifty functions */

    public void attachNifty() {
        // attach the nifty display to the gui view port as a processor
        if (!GameGUIManager.isEnabled()) {
            guiViewPort.addProcessor(niftyDisplay);
            //enabled = true;
        }
    }

    public void detachNifty() {
        if (!GameGUIManager.isEnabled()) {
            guiViewPort.removeProcessor(niftyDisplay);
            //enabled = false;
        }
    }

    public <T extends ScreenController> T getCurrentScreenController(String screenName, Class<T> clazz) {
        Screen currentScreen = nifty.getCurrentScreen();
        if (currentScreen.getScreenId().equals(screenName)) {
            return (T) currentScreen.getScreenController();
        } else {
            return null;
        }
    }

    public ScreenController getCurrentScreenController() {
        return nifty.getCurrentScreen().getScreenController();
    }

    public <T extends ScreenController> T getCurrentScreenController(Class<T> clazz) {
        return (T) getCurrentScreenController();
    }

    public <T extends Controller> T getQuickUIController(String elementId, Class<T> clazz) {
        return nifty.getCurrentScreen().findControl(elementId, clazz);
    }

    /**
     * Null Safe Controller getter.
     *
     * @param <T>
     * @param clazz
     * @param path
     * @return
     */
    public <T extends Controller> T getController(Class<T> clazz, String path) {
        String[] array = path.split("/");

        if (array.length < 2) {
            throw (new IllegalArgumentException(" This path is wrong : " + path));
        }
        Element thisElement = getElementByPath(path);
        T result = null;

        result = thisElement.getControl(clazz);
        if (result != null) {
            return result;
        } else {
            if (!GameGUIManager.isNullSafe()) {
                throw new IllegalArgumentException("Can not find Controller in element" + path);
            } else {
                return null;
            }
        }


    }

    /**
     * Null Safe Renderer getter.
     *
     * @param <T>
     * @param aClass
     * @param path
     * @return
     * @throws IllegalArgumentException
     */
    public <T extends ElementRenderer> T getRenderer(Class<T> aClass, String path)
            throws IllegalArgumentException {
        String[] array = path.split("/");

        if (array.length < 2) {
            throw (new IllegalArgumentException(" This path is wrong : " + path));
        }
        Element thisElement = getElementByPath(path);
        T result = null;

        result = thisElement.getRenderer(aClass);
        if (result != null) {
            return result;
        } else {
            if (!GameGUIManager.isNullSafe()) {
                throw new IllegalArgumentException("Can not find Renderer in element" + path);
            } else {
                return null;
            }
        }

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

    private void disableLogger() {
        Logger.getLogger("de.lessvoid.nifty").setLevel(Level.WARNING);
        Logger.getLogger("NiftyInputEventHandlingLog").setLevel(Level.WARNING);
        Logger.getLogger("NiftyEventBusLog").setLevel(Level.WARNING);
        Logger.getLogger("NiftyImageManager").setLevel(Level.WARNING);
    }

    @Override
    public void reportInteraction(GUIInteraction interaction) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void ignoreInteraction(GUIInteraction interaction) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void crossInteraction(GUIInteraction interaction, GUIInteraction interaction2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void muted() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TypeToken getScreenType() {
        return TypeToken.of(Nifty.class);
    }

    @Override
    public void refreshChanges() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void init() {
        initGUI(assetManager, inputManager, audioRenderer, guiViewPort);
    }

    @Override
    public void load() {
        setupCommonEffects();
        setupCommonStyles();
        setupCommonScreens();
    }

    @Override
    public void config(Properties props) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void finish() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LifeCyclePhase getCurrentPhase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float getProgressPercent(LifeCyclePhase aPhrase) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TreeTraverser<Nifty> getElementTraverser() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Element getRootElement(GUIScreenInfo screen) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void activeScreen(GUIScreenInfo<? extends GUISystemService, ?> screen) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deactiveScreen(GUIScreenInfo<? extends GUISystemService, ?> screen) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void loadScreen(GUIScreenInfo<? extends GUISystemService, ?> screen) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void refreshScreen(GUIScreenInfo<? extends GUISystemService, ?> screen) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void doInteraction(GUIInteraction interaction) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void interceptInteraction(GUIInteraction interaction) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void reportInteraction(GUIInteraction interaction, GUIElement<? extends GUISystemService, ?> element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void loadPath(String filePath) {
        nifty.addXml(filePath);
    }

    @Override
    public TypeToken getBoundaryModel() {
        return TypeToken.of(Box.class);
    }

    @Override
    public TypeToken getLayoutModel() {
        return TypeToken.of(LayoutPart.class);
    }
}
