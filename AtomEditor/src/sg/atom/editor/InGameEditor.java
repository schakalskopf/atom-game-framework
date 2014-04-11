/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.editor;

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
import sg.atom.editor.system.AtomEditorFunctionSystem;
import sg.atom.managex.api.config.EditorConfiguration;
import sg.atom.managex.api.project.AtomEditorProject;
import sg.atom.managex.api.system.EditorSystem;

/**
 * InGameEditor is an AppState dedicated to all in-game editing purpose. It can
 * be use as stand alone SimpleApplication or an embed AppState.
 *
 * <p>It's different from EmbedApplication in Testbed framework as its
 * contracted with the JME3 system to run in SimpleApplication level but not
 * lower. This editor also integrated better if you use AtomMain as its
 * application! If you want to use full-fleged editor just use AtomSDK, desktop
 * editing that what is for. <s>Experimental NanoGameEditor (lower level) is
 * unstable and also lack of purposes right now!</s>
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
//Shortcut -------------------------------------------------------------

    private SimpleApplication wrapedApplication;
    // Managers ------------------------------------------------------------
//    private EditorGUIManager guiManager;
//    private EditorSceneManager sceneHelper;
//    private EditorSelectionManager selectionManager;
//    private HelperManager helperManager;
    private AtomEditorFunctionSystem functionSystem;
    private CommonTool commonTool;
    // Stage
    private Camera managedCam;
    //Status
    EditorConfiguration editorConfiguration;
    private boolean enableStatus;
    private boolean initialized;
    private boolean runAsApplication = true;
    //Project related
    private AtomEditorProject currentProject;
    String tempPath;
    String userPath;
    String dataPath;
    String projectPath;
    // Plugins
    EditorSystem editorSystem;

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
        setupSystems();
        setupManagers();
    }

    protected void setupSystems() {
        editorSystem.bootStrap();
        setupFunctions();
        setupProject();
        setupCamera();
    }

    protected void setupManagers() {
        this.commonTool = CommonTool.getDefault(wrapedApplication);

//        this.guiManager = new EditorGUIManager(this);
//        this.sceneHelper = new EditorSceneManager(this);
//        this.selectionManager = new EditorSelectionManager();
//        this.helperManager = new HelperManager();
    }

    protected void setupFunctions() {
        //FIXME: Dynamicly load the functions and chain from outside maybe?

        functionSystem = new AtomEditorFunctionSystem(this.wrapedApplication);
        functionSystem.setupFunctions();

    }

    private void setupCamera() {
        this.managedCam = wrapedApplication.getCamera();
        managedCam.setLocation(new Vector3f(-10, 10, -10));
        managedCam.lookAt(Vector3f.ZERO.clone(), Vector3f.UNIT_Y.clone());
    }

    public void setupProject() {
        //FIXME: Project loading method
        //Check if there any project save in session?

        //Open in directory?
        currentProject = new AtomEditorProject();
    }

    private void proxySetup() {
    }

    public void commandLine() {
    }

    // State management -----------------------------------------------------------
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
// Setter & getter -----------------------------------------------------------

    public void setApp(SimpleApplication app) {
        this.wrapedApplication = app;
    }

    public SimpleApplication getApp() {
        return wrapedApplication;
    }

    public FunctionSystem getFunctionSystem() {
        return functionSystem;
    }

    public EditorConfiguration getEditorConfiguration() {
        return editorConfiguration;
    }

    public AtomEditorProject getCurrentProject() {
        return currentProject;
    }

    // Standalone application -----------------------------------------------
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
}
