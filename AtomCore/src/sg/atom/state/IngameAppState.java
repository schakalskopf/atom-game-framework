package sg.atom.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import sg.atom.ui.GameGUIManager;
import sg.atom.core.AtomMain;
import sg.atom.stage.StageManager;

/**
 *
 * @author hungcuong
 */
public class IngameAppState extends AbstractAppState {

    private AtomMain app;
    private Node rootNode;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private ViewPort viewPort;
    private GameGUIManager gameGUIManager;
    private StageManager stageManager;

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
        if (this.app.getStageManager() == null) {
            this.app.initStage();
        }
        this.stageManager = this.app.getStageManager();

        setEnabled(true);
    }

    @Override
    public void setEnabled(boolean enabled) {
        // Pause and unpause
        super.setEnabled(enabled);
        if (enabled) {

            gameGUIManager.loadAndGotoScreen("ingameScreen");

        } else {
        }
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        if (!stageManager.isReadyToPlay()) {
            stageManager.doReadyToPlay();
        } else {
            stageManager.updateStage(tpf);
        }
    }
}
