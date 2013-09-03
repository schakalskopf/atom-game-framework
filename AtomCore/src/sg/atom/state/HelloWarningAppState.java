package sg.atom.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import sg.atom.ui.GameGUIManager;
import sg.atom.core.AtomMain;

/**
 *
 * @author hungcuong
 */
public class HelloWarningAppState extends AbstractAppState {

    private AtomMain app;
    private GameGUIManager gameGUIManager;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (AtomMain) app; // can cast Application to something more specific
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
            gameGUIManager.loadAndGotoScreen("helloWarning");
            //System.out.println("Call me!");
        } else {
        }
    }
}
