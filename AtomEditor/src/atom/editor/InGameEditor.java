/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atom.editor;

import atom.managex.action.AtomFunction;
import atom.managex.action.FunctionSystem;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class InGameEditor extends SimpleApplication {

    private FunctionSystem functionSystem;
    private EditorGUIManager guiManager;
    private EditorSceneHelper sceneHelper;

    @Override
    public void simpleInitApp() {
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
        functionSystem = new FunctionSystem(this);
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
        InGameEditor app = new InGameEditor();

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
        cam.setLocation(new Vector3f(-10, 10, -10));
        cam.lookAt(Vector3f.ZERO.clone(), Vector3f.UNIT_Y.clone());
    }

    public FunctionSystem getFunctionSystem() {
        return functionSystem;
    }
}
