package sg.atom.core;

import com.jme3.app.state.AppState;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jme3.app.state.AppStateManager;
import sg.atom.state.HelloWarningAppState;
import sg.atom.state.IngameAppState;
import sg.atom.state.LoadingAppState;
import sg.atom.state.MainMenuAppState;

/**
 *
 * @author hungcuong
 */
public class GameStateManager {   // short cut link

    protected AtomMain app;
    protected AppStateManager stateManager;
    private Class<? extends AppState> startupClass;
    private AppState startUpState;

    public GameStateManager(AtomMain app) {
        this.app = app;
        this.stateManager = app.getStateManager();

    }

    public void initState() {
    }

    public void startUp() {
        try {
            //stateManager.attach(new HelloWarningAppState());
            if (startUpState == null) {
                this.startUpState = startupClass.newInstance();
            }
            stateManager.attach(startUpState);
        } catch (InstantiationException ex) {
            Logger.getLogger(GameStateManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GameStateManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setStartupState(Class<? extends AppState> clazz) {
        this.startupClass = clazz;
    }

    public void setStartupState(AppState state) {
        this.startUpState = state;
    }

    public void enterGameMenu() {
        AppState needDetachState = stateManager.getState(HelloWarningAppState.class);
        if (needDetachState != null) {
            stateManager.detach(needDetachState);
        }
        stateManager.attach(new MainMenuAppState());
    }

    public void singleGame() {
    }

    public void enterInGame() {
        AppState needDetachState = stateManager.getState(LoadingAppState.class);
        if (needDetachState != null) {
            stateManager.detach(needDetachState);
        }
        stateManager.attach(new IngameAppState());
    }

    public void pauseGame() {
    }

    public void loadGame() {
        /*
         if (app.getStageManager() == null) {
         app.initStage();
         }
         this.stageManager = app.getStageManager();
         */
        stateManager.attach(new LoadingAppState());
    }

    public void loadGameFinished() {
        enterInGame();
    }

    public void quitGame() {
        //stateManager.getState()

        app.quit();
    }

    public void runGame() {
        //gameGUIManager.doQuickMode();
        //gamePlayManager.runGame();
        //System.out.println("Start game! ");
    }
}
