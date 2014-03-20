package sg.atom.core;

import com.google.inject.Inject;
import com.jme3.app.state.AppState;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jme3.app.state.AppStateManager;
import java.util.Properties;
import java.util.Set;
import org.customsoft.stateless4j.StateMachine;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.state.HelloWarningAppState;
import sg.atom.state.IngameAppState;
import sg.atom.state.LoadingAppState;
import sg.atom.state.MainMenuAppState;
import sg.atom.structure.state.IObjectiveStateManager;

/**
 * GameStateManager is a warper for various game state function. State is one
 * important and common paradigm which use in Game programming. In JME3, State
 * are also one of the main pattern use here and there. The normal AppStates are
 * managed by the StateManager and "rolled" by regular update cycle of
 * Application.
 *
 * <p>GameStateManager can be consider helper for StateManager which provide
 * further managements for State; to trigger approriate GameEvent while the
 * State changing; Mode to State mapping... Beside of that,( thank to the fine
 * abstraction of AppState) one can possible update the AppState concurrently
 * outside of logic cycle of normal Application. <b>Note</b> that this
 * GameStateManager provide "non synchonized" States management but incompatible
 * with the old AppStateManager. Once you decide to use GameStateManager 100%,
 * you should save all your AppState into GameStateManager repository! My most
 * personal disapoint in StateManager is not just the "synchonize" routines but
 * the final link to Application which forced Application type, and some other
 * final, which StateManager can not be extended. The GameStateManager try to
 * fill this gap without breaking the contract with underlying JME3 official
 * codes... In my personal repository, i reimplemented StateManager to unite the
 * two!</p>
 *
 * <p>Embed a FunctionSystem which is a Conccurent-Hierachical-
 * FiniteStateMachine. So it can be configed for state changing dynamicly.</p>
 *
 * <p>Can direct StageManager and GUIManager doing in-state update and
 * transistion between states. For ex: fading, cleaning, release memory.. etc.
 * It's also have handful utilities for FSM creating and debuging, other can
 * use. This implementation also support "no contract" State implementation
 * which Atom data structure mentioned.
 *
 * <p>State can be consider unit (Module) of an self contain execution system.
 * That's why this Manager also help State to be injected, or dynamic load from
 * class loaders. State can be consider as an Identifcation, or Key in a Map.
 * This StateManager also support a high concurrent map (repository) for
 * associated object of State.
 *
 * <p>State "can" has dependencies in others, they can also have imcompatible
 * with exits enabled states in the repository or cycle have to be resolve with
 * piority. This impl also support a "simple" mechnism to support those
 * particular features. <b>Note</b> that dependecies or incompatible in states
 * are violent the "architecture" and "academic" definition of state, but since
 * they are useful (similar to fuzzy state), they are supported!
 *
 * <p>Currently dependency and mini-plugin architecture are supported with
 * Guice!
 *
 * @author atomix
 */
public class GameStateManager implements IGameCycle, IObjectiveStateManager<AppState> {

// short cut link
    protected AtomMain app;
    protected AppStateManager stateManager;
    protected static final Logger logger = Logger.getLogger(GameStateManager.class.getName());
    //Startup
    @Inject
    private Class<? extends AppState> startupClass;
    private AppState startUpState;
    //Function
    public String currentMode;
    public Set<String> modes;
    // FSM. FIXME: Use AtomStateMachine instead.
    public boolean useFSM;
    private StateMachine<AppState, Object> fsm;

    //Dependencies and imcompatibles
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

    public void loadStateDefinition() {
    }

    // FIXME: Remove. Simple implementation of "common" game routines handler!
    // Change it to dependency resolving instead.
    @Deprecated
    public void _common_enterGameMenu() {
        AppState needDetachState = stateManager.getState(HelloWarningAppState.class);
        if (needDetachState != null) {
            stateManager.detach(needDetachState);
        }
        stateManager.attach(new MainMenuAppState());
    }

    @Deprecated
    public void _common_enterInGame() {
        AppState needDetachState = stateManager.getState(LoadingAppState.class);
        if (needDetachState != null) {
            stateManager.detach(needDetachState);
        }
        stateManager.attach(new IngameAppState());
    }

    @Deprecated
    public void _common_pauseGame() {
        //app.setTimer(null);
    }

    @Deprecated
    public void _common_loadGame() {
        stateManager.attach(new LoadingAppState());
    }

    @Deprecated
    public void _common_loadGameFinished() {
        _common_enterInGame();
    }

    @Deprecated
    public void _common_quickMode() {
        //for testing purpose
    }

    @Deprecated
    public void _common_runGame() {
        //gamePlayManager.runGame();
    }

    @Deprecated
    public void _common_endGame() {
        //Credit
    }

    public void quitGame() {
        //stateManager.getState()
        app.quit();
    }

    /**
     * Wrap the AbstractManager with automatic generated AppState.
     *
     * @param abstractManager
     */
    public static AppState wrap(AbstractManager abstractManager) {
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    // State Management methods
    public void loadState(AppState state) {
        //ensure the class-level dependencies are loaded.
    }

    public boolean attachState(AppState state) {
        // ensure the state-level dependencies are loaded and configed succesfully. otherwise throw exception.
        // ensure all incompatible state are detached properly.
        return stateManager.attach(state);
    }

    public boolean detachState(AppState state) {
        return stateManager.detach(state);
    }
    // Cycle methods

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
