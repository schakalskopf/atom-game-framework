package sg.atom.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.atom.ui.GameGUIManager;
import sg.atom.core.AtomMain;
import sg.atom.stage.StageManager;
import sg.atom.ui.common.UILoadingScreenController;

/**
 * <code>LoadingAppState</code> is the common State that load the game assets
 * and do some config jobs parallel with the orginal thread. This implementation
 * demonstrate the architecture of the Atom framework with Phase, beside of
 * State. It's also extendable and easy for your mechanic hook in.
 *
 * <p>Basicly the State run through below phases overide when needed: <ul>
 * <li><b>loadPhase()</b> Init the components</li>
 *
 * <li><b>loadPhase()</b> Load all the assets</li>
 *
 * <li><b>configPhase()</b> Config after loads </li>
 *
 * <li><b>finishPhase()</b> Attach to the rootNode (must be in the AppState's
 * thread - render Thread)</li> </ul>
 *
 * <li><b>watchTask()</b> can also be overide to include the progress watch or
 * something
 *
 * <li><b>nextState()</b> can also be overide change to the next State. This can
 * be unset if this State are running underlying, parallel with other state.
 *
 * <p>FIXME: Use Guava Monitor instead.
 *
 * <p>FIXME: Replace loadComplete boolean, retyNum with Atomic* classes!
 *
 * @author atomix
 */
public class LoadingAppState extends AbstractAppState {

    protected AtomMain app;
    protected GameGUIManager gameGUIManager;
    protected StageManager stageManager;
    UILoadingScreenController guiController;
    // Manage the concurrency loading via thread. FIXME: Change to multi Task with GPars
    protected boolean threadStarted;
    protected float oldPercent = -1f;
    protected boolean loadComplete = false;
    /* This constructor creates a new executor with a core pool size of 4. */
    protected ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(4);
    //FIXME: The future that is used to check the execution status. Change to ListenableFuture!
    protected Future loadStageTask = null;
    protected Future updateProgressBarTask = null;
    public static int retryNum = 0;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (AtomMain) app; // can cast Application to something more specific

    }

    @Override
    public void setEnabled(boolean enabled) {
        // Pause and unpause
        super.setEnabled(enabled);
        if (enabled) {
            initPhase();
        } else {
            if (loadComplete) {
            }

        }
    }

    protected void initPhase() {
    }

    protected void loadPhase() {
    }

    protected void finishPhase() {
    }

    protected void nextState() {
    }

    protected void watchTask() {
    }

    protected void submitLoadTask() {
        //Thread starts!
        loadStageTask = executor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                loadPhase();
                return true;
            }
        });
        threadStarted = true;
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        // FIXME: Change to Guava Listenable Future
        if (isEnabled()) {

            // If not load yet
            if (!threadStarted) {
                // Load now
                submitLoadTask();
            } else {
                if (!loadComplete) {
                    // Check if done?

                    if (loadStageTask.isDone()) {
                        try {
                            Boolean result = (Boolean) loadStageTask.get();
                            if (result.booleanValue()) {
                                // Done loading succesfully! then go ingame
                                finishPhase();
                                loadComplete = true;
                                nextState();
                                //updateProgressBarTask.cancel(true);
                                Logger.getLogger(this.getClass().getName()).log(Level.INFO, " Load all succesfully !");
                            }
                        } catch (InterruptedException ex) {
                            //String msg = "Interupted! Something bad happen: ";//+ ex.getMessage();
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "The loading state is interupted! Something bad happen! Retry (1) is made... Please wait!", ex);
                            //updateProgressBar(true, msg);

                        } catch (ExecutionException ex) {
                            //String msg = "Something bad happen !";// + ex.getMessage();
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "ExecutionException happen! Retry (1) is made", ex);
                            //updateProgressBar(true, msg);
                        } finally {
                            //updateProgressBarTask.cancel(false);
                            executor.shutdown();
                            setEnabled(false);
                            //loadStageTask = null;
                            //updateProgressBarTask = null;
                        }
                    } else if (loadStageTask.isCancelled()) {
                        //Set future to null. Maybe we succeed next time...
                        loadStageTask = null;
                        //submitLoadTask();
                    }
                    // Not , then wait in the mean time, update the watch !
                    watchTask();
                }

            }
        }

    }
}
