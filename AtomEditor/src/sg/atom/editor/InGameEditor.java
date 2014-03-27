/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.editor;

import sg.atom.managex.api.function.AtomFunction;
import sg.atom.managex.api.function.FunctionSystem;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import sg.atom.corex.common.CommonTool;
import sg.atom.managex.api.project.AtomEditorProject;

/**
 * InGameEditor is an AppState dedicated to all in-game editing purpose. It can
 * be use as stand alone SimpleApplication or an embed AppState.
 *
 * <p>It's different from EmbedApplication in Testbed framework as its
 * contracted with the JME3 system to run in SimpleApplication level but not
 * lower. If you want to use full-fleged editor just use AtomSDK, desktop
 * editing that what is for. Experimental NanoGameEditor (lower level) is
 * unstable and also lack of purposes right now!
 *
 * <p>To run this application from command line.
 *
 * <p>To config its to run with Jackrabbit content repository.
 *
 * <p>Better documentation: atomframework.wiki
 *
 * @author cuong.nguyenmanh2
 */
public class InGameEditor extends SimpleApplication implements AppState {

    private SimpleApplication wrapedApplication;
    private AtomEditorProject project;
    private FunctionSystem functionSystem;
//    private EditorGUIManager guiManager;
//    private EditorSceneManager sceneHelper;
//    private EditorSelectionManager selectionManager;
//    private HelperManager helperManager;
    //Shortcut
    public Camera cam;
    private CommonTool commonTool;
    private boolean enableStatus;
    private boolean initialized;
    public boolean runAsApplication = true;

    public InGameEditor() {
    }

    public InGameEditor(SimpleApplication app) {
        this.wrapedApplication = app;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {

        if (app instanceof SimpleApplication) {
            if (this.wrapedApplication != null) {
                setApp((SimpleApplication) app);
            }
        } else {
            throw new IllegalArgumentException("Application should be a SimpleApplication!");
        }
        this.simpleInitApp();
    }

    public void simpleInitApp() {
        proxySetup();
        setupFunctions();
        setupManagers();
        setupInput();
        setupCamera();
    }

    protected void setupManagers() {
        this.commonTool = CommonTool.getDefault(wrapedApplication);
        
//        this.guiManager = new EditorGUIManager(this);
//        this.sceneHelper = new EditorSceneManager(this);
//        this.selectionManager = new EditorSelectionManager();
//        this.helperManager = new HelperManager();
    }

    void setupFunctions() {
        //FIXME: Dynamicly load the functions and chain from outside maybe?

        functionSystem = new FunctionSystem(this.wrapedApplication);
        functionSystem.addFunction(new AtomFunction("Move", "Move", "sprite:20,23,0"));
        functionSystem.addFunction(new AtomFunction("Rotate", "Rotate", "sprite:20,23,1"));
        functionSystem.addFunction(new AtomFunction("Scale", "Scale", "sprite:20,23,2"));
        functionSystem.addFunction(new AtomFunction("Select", "Select", "sprite:20,23,46"));
    }

    void setupInput() {
        /*
         inputManager.addMapping("PressH",
         new KeyTrigger(KeyInput.KEY_SPACE));
         inputManager.addListener(functionSystem, "PressH");
         */
    }

    public static void main(String[] args) {
        final InGameEditor inGameEditor = new InGameEditor();

        // Run as wraper
        SimpleApplication app = new SimpleApplication() {
            @Override
            public void simpleInitApp() {
                inGameEditor.setApp(this);
                inGameEditor.simpleInitApp();
            }
        };

        AppSettings cfg = new AppSettings(true);

        cfg.setResolution(1024, 768);
        cfg.setTitle("InGameEditor");
        app.setSettings(cfg);
        app.setShowSettings(false);
        app.setDisplayStatView(false);
        app.setDisplayFps(false);
        app.start();

    }

    private void setupCamera() {
        this.cam = wrapedApplication.getCamera();
        cam.setLocation(new Vector3f(-10, 10, -10));
        cam.lookAt(Vector3f.ZERO.clone(), Vector3f.UNIT_Y.clone());
    }

    public FunctionSystem getFunctionSystem() {
        return functionSystem;
    }

    private void proxySetup() {
    }

    public void setApp(SimpleApplication app) {
        this.wrapedApplication = app;
    }

    public SimpleApplication getApp() {
        return wrapedApplication;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setEnabled(boolean active) {
        this.enableStatus = active;
    }

    public boolean isEnabled() {
        return this.enableStatus;
    }

    public void stateAttached(AppStateManager stateManager) {
    }

    public void stateDetached(AppStateManager stateManager) {
    }

    public void update(float tpf) {
    }

    public void render(RenderManager rm) {
    }

    public void postRender() {
    }

    public void cleanup() {
    }
}
