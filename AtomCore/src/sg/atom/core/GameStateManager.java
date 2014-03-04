package sg.atom.core;

import com.jme3.app.state.AppState;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jme3.app.state.AppStateManager;
import java.util.Properties;
import java.util.Set;
import org.customsoft.stateless4j.StateMachine;
import sg.atom.core.lifecycle.IGameCycle;

/**
 * GameStateManager is a warper for specific game state function.
 *
 * Can be consider helper for StateManager which provide further managements for
 * State, trigger approriate GameEvent while the State changing, Mode to State
 * mapping.
 *
 * Embed a FunctionSystem which is a FiniteStateMachine. So it can be config for
 * state changing dynamicly
 *
 * @author atomix
 */
public class GameStateManager implements IGameCycle{   // short cut link

    protected AtomMain app;
    protected AppStateManager stateManager;
    //Startup
    private Class<? extends AppState> startupClass;
    private AppState startUpState;
    //Function
    public String currentMode;
    public Set<String> modes;
    public boolean useFSM;
    // FSM
    private StateMachine<AppState, Object> fsm;

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
    /* FIXME: Remove. Simple implementation of "common" game routines handler! */
/*
    public void enterGameMenu() {
        AppState needDetachState = stateManager.getState(HelloWarningAppState.class);
        if (needDetachState != null) {
            stateManager.detach(needDetachState);
        }
        stateManager.attach(new MainMenuAppState());
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
        stateManager.attach(new LoadingAppState());
    }

    public void loadGameFinished() {
        enterInGame();
    }
    public void runGame() {
        //FIXME: Enable QuickMode
        //gameGUIManager.doQuickMode();
        //gamePlayManager.runGame();
        //System.out.println("Start game! ");
    }
    */
    
    public void quitGame() {
        //stateManager.getState()
        app.quit();
    }


    // Management methods

    @Override
    public void init() {
        initState();
    }

    @Override
    public void load() {
        
    }

    @Override
    public void config(Properties props) {
        
    }

    @Override
    public void update(float tpf) {
        
    }

    @Override
    public void finish() {
        
    }

    @Override
    public LifeCyclePhase getCurrentPhase() {
        return null;
    }

    @Override
    public float getProgressPercent(LifeCyclePhase aPhrase) {
        return 0;
    }
}
