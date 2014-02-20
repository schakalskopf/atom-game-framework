/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.editor;

import sg.atom.managex.api.function.AtomFunction;
import sg.atom.managex.api.function.FunctionSystem;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.system.AppSettings;

/**
 * InGameEditor is an AppState dedicated to all in-game editing purpose
 * @author cuong.nguyenmanh2
 */
public class InGameEditor extends AbstractAppState {

    private FunctionSystem functionSystem;
    private EditorGUIManager guiManager;
    private EditorSceneHelper sceneHelper;
    private SimpleApplication app;
    //Shortcut
    public Camera cam;

    public InGameEditor() {
    }

    public InGameEditor(SimpleApplication app) {
        this.app = app;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        if (app instanceof SimpleApplication) {
            if (this.app != null) {
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
        //makeTonegodToolbar();
        guiManager = new EditorGUIManager(this);
        sceneHelper = new EditorSceneHelper(this);
        sceneHelper.startUp();
        guiManager.startUp(false);

        setupInput();
        setupCamera();
    }

    void setupFunctions() {
        functionSystem = new FunctionSystem(this.app);
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
                inGameEditor.simpleInitApp();
            }
        };
        inGameEditor.setApp(app);

        AppSettings cfg = new AppSettings(true);
        
        cfg.setResolution(1024, 768);
        System.out.println("mnuFile".matches("mnu.*"));

        cfg.setTitle("TestActionSystem");
        app.setSettings(cfg);
        app.setShowSettings(false);
        app.setDisplayStatView(false);
        app.setDisplayFps(false);
        app.start();

    }

    private void setupCamera() {
        this.cam = app.getCamera();
        cam.setLocation(new Vector3f(-10, 10, -10));
        cam.lookAt(Vector3f.ZERO.clone(), Vector3f.UNIT_Y.clone());
    }

    public FunctionSystem getFunctionSystem() {
        return functionSystem;
    }

    private void proxySetup() {
    }

    public void setApp(SimpleApplication app) {
        this.app = app;
    }

    public SimpleApplication getApp() {
        return app;
    }
}
