/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.execution;

import com.google.common.util.concurrent.Service;
import com.jme3.app.AppTask;
import com.jme3.app.state.AppState;
import sg.atom.core.AbstractManager;
import sg.atom.core.monitor.ProgressInfo;
import sg.atom.gameplay.GameAction;
import sg.atom.stage.actor.AtomicAction;

/**
 * Hierachy named task ultilities.
 *
 * <p>Expanded ultimate version of JME3's AppTask! This kind of Task stick with
 * AbstractManager not the Application level. Shouldn't extends This class
 * because its have complex linking mechanism underlying!
 *
 * <p>Task dispatching is the foundation of execution of the JVM. Read:
 * http://spring.io/blog/2013/05/13/reactor-a-foundation-for-asynchronous-applications-on-the-jvm
 * <ul>
 *
 * <li>That's why to make a game perfomance superiorthis AtomTask is a strict
 * morphable-monitored-threadsafe-eventbased-selfcontained runnable. It can wrap
 * around GameAction, AppState, Runnable, Closure, ProgressInfo, IProgress...
 *
 * <li>Embed in a load balance and advance execution/concurency enviroment.
 *
 * <li>Hook to GPar Task, Concurent MultiThread, Guava Service architecture,
 * Reactor consumer...</ul> </p>
 *
 * <p><b>Current code borrowed from Jumi actors. In the end if this cause too
 * much confusion between Service - Task, and Task - Actor, Actor - Agent this
 * will be removed completely!</b>
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
