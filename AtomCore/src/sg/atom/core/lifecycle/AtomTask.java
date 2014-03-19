/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.lifecycle;

import com.google.common.util.concurrent.Service;
import com.jme3.app.AppTask;
import com.jme3.app.state.AppState;
import groovy.lang.Closure;
import sg.atom.core.AbstractManager;
import sg.atom.core.monitor.ProgressInfo;
import sg.atom.gameplay.GameAction;
import sg.atom.stage.actor.AtomicAction;

/**
 * Hierachy named task ultilities.
 *
 * <p>Expanded version of JME3's AppTask! This kind of Task stick with
 * AbstractManager not the Application level. Shouldn't extends This class
 * because its have complex linking mechanism underlying!<ul>
 *
 * <li>It can wrap around GameAction, AppState, Runnable, Closure, ProgressInfo,
 * IProgress
 *
 * <li>Embed in a load balance and advance execution/concurency enviroment.
 *
 * <li>Hook to GPar Task and also Concurent MultiThread, Guava Service
 * architecture.</ul> </p>
 *
 * @author atomix
 */
public final class AtomTask {

    private Object wrapedTask;
    private AbstractManager manager;

    public AtomTask(Object wrapedTask) {
        this.wrapedTask = wrapedTask;
    }

    public void wrap(Service service) {
    }

    public void wrap(Closure closure) {
    }

    public void wrap(AtomicAction atomicAction) {
    }

    public void wrap(GameAction gameAction) {
    }

    public void wrap(Runnable runnable) {
    }

    public void wrap(AppTask task) {
    }

    public void wrap(AppState state) {
    }

    /* Execution methods */
    public void startAs(Class clazz) {
    }

    public AppState startAsAppState() {
        return null;
    }

    public Service startAsService() {
        return null;
    }

    public AppTask startAsTask() {
        return null;
    }

    public void startAsThread() {
    }

    public void startInThread() {
    }

    public void start() {
    }

    public void stop() {
    }

    public ProgressInfo notifyProgress() {
        return null;

    }

    public void interupted() {
    }

    public void failed() throws Exception {
    }
}
