/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.state.common;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import sg.atom.ui.GameGUIManager;
import sg.atom.core.AtomMain;

/**
 *
 * @author atomix
 */
public class MainMenuAppState extends AbstractAppState {

    private AtomMain app;
    private Node rootNode;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private ViewPort viewPort;
    private GameGUIManager gameGUIManager;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (AtomMain) app; // can cast Application to something more specific
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        this.viewPort = this.app.getViewPort();
        if (this.app.getStageManager() == null) {
            this.app.initGUI();
        }
        this.gameGUIManager = this.app.getGameGUIManager();
        //System.out.println("initialize!");
        setEnabled(true);
    }

    @Override
    public void setEnabled(boolean enabled) {
        // Pause and unpause
        super.setEnabled(enabled);
        if (enabled) {
            // pause the whole client game and tell server to pause
            // show nifty main menu screen
            // let user config if they want
            gameGUIManager.loadAndGotoScreen("mainMenuScreen");
            //System.out.println("Call me!");
        } else {
            // take away everything not needed while this state is PAUSED
            //...
        }
    }
}
