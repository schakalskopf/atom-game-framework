package sg.atom.editor.managers;

import sg.atom.managex.api.function.FunctionSystem;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import java.util.HashMap;
import java.util.Properties;
import org.apache.bsf.util.Bean;
import sg.atom.core.lifecycle.IGameCycle.LifeCyclePhase;
import sg.atom.editor.InGameEditor;
import sg.atom.managex.api.presentation.Perspective;
import sg.atom.ui.GameGUIManager;

/**
 * EditorGUIManager for editing purpose. Current version support "one-screen"
 * only GUI. Similar to Blender or a single Swing JFrame; which different from
 * GameGUIManager.
 *
 * FIXME: Support perspective. Support multi-screen. Safe extends GameGUIManager
 * possible?
 *
 * FIXME: Support others GUI
 *
 * @author cuong.nguyenmanh2
 */
public abstract class EditorGUIManager extends GameGUIManager {

    // Application level
    protected SimpleApplication app;
    protected AssetManager assetManager;
    protected InputManager inputManager;
    protected InGameEditor editor;
    protected FlyByCamera flyCam;
    protected FunctionSystem functionSystem;
    protected String defaultIconPath = "Interface/icons/blenderbuttons3.png";
    protected HashMap<String, JmeCursor> cursors = new HashMap<String, JmeCursor>();
    private String perspectiveLayout;
    //ArrayList<>
    private Perspective currentPerspective;

    public EditorGUIManager(InGameEditor editor) {
        this.editor = editor;
        this.app = editor.getApp();
        this.assetManager = app.getAssetManager();
        this.inputManager = app.getInputManager();
        this.flyCam = app.getFlyByCamera();

    }

    public void startUp() {
        functionSystem = editor.getFunctionSystem();
        initCurrors();
    }

    public void initCurrors() {
        cursors.put("normal", (JmeCursor) assetManager.loadAsset("Textures/Cursors/TRONnormal.ani"));
        inputManager.setMouseCursor(cursors.get("normal"));
        inputManager.setCursorVisible(true);
        //flyCam.setEnabled(false);
        flyCam.setMoveSpeed(40f);
        flyCam.setDragToRotate(true);
    }

    // Handle inputs ----------------------------------------------------------- 
    public abstract void setupInput();

    public abstract void doFunction(String functionName);
    //Perspectives -----------------------------------------------------------

    public void loadAllPerspectives() {
    }

    public void selectPerspective(Perspective perspective) {
        this.currentPerspective.setEnabled(false);
        this.currentPerspective = perspective;
        this.currentPerspective.setEnabled(true);
    }
    //Management -----------------------------------------------------------

    /**
     * Build up metaGUI for this single bean.
     *
     * @param singleBean
     */
    public void meta(Bean singleBean) {
    }

    public void onSelected(Object object) {
    }
    //Cycle -----------------------------------------------------------

    public void init() {
        startUp();
    }

    public void load() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void config(Properties props) {
        //this.noXML = (Boolean) props.get("NoXML");
    }

    public void update(float tpf) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void finish() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public LifeCyclePhase getCurrentPhase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public float getProgressPercent(LifeCyclePhase aPhrase) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // Setter & Getter
    //
    public InGameEditor getEditor() {
        return editor;
    }

    public String getDefaultIconPath() {
        return defaultIconPath;
    }

    public <T> T getComponent(Class<T> aClass) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
