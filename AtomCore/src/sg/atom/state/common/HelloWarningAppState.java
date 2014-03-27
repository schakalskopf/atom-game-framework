package sg.atom.state.common;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import sg.atom.ui.GameGUIManager;
import sg.atom.core.AtomMain;
import sg.atom.stage.cine.AtomCinematic;

/**
 * HelloWarning is the common state of games which usually referred as Welcome
 * screen.
 *
 * <p>This implementation features: <ul> <li>Using
 * Texture,Image,AssetKey,NiftyScreen,CommonScreen as the flashScreen
 *
 * <li>Handle all "non-GUILib" interaction. Intercept GUI interaction. Call back
 * when finish! That means if you use NiftyScreen for example, you handle GUI
 * interaction by your self, but this AppState can intercept the interaction.
 *
 * <li>Ready for out-game cinemetic. </ul>
 *
 * @author atomix
 */
public class HelloWarningAppState extends AbstractAppState {

    private AtomMain app;
    private GameGUIManager gameGUIManager;
    private boolean outGameCinematic;
    private String flashScreenName;
    public static String defaultFlashScreenName = "_WelcomeFlashscreen";
    private AtomCinematic cine;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (AtomMain) app; // can cast Application to something more specific
        if (this.app.getStageManager() == null) {
            this.app.initGUI();
        }
        this.gameGUIManager = this.app.getGameGUIManager();
        setEnabled(true);
    }

    @Override
    public void setEnabled(boolean enabled) {
        // Pause and unpause
        super.setEnabled(enabled);
        if (enabled) {
            if (flashScreenName == null) {
                flashScreenName = defaultFlashScreenName;
            }
            gameGUIManager.loadAndGotoScreen(flashScreenName);
        } else {
        }
    }

    public void setFlashScreenName(String flashScreenName) {
        this.flashScreenName = flashScreenName;
    }

    public void setOutGameCinematic(boolean outGameCinematic, AtomCinematic cine) {
        this.outGameCinematic = outGameCinematic;
        this.cine = cine;
    }
}
