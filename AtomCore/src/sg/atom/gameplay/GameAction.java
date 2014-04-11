/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

import com.jme3.app.Application;
import sg.atom.core.AbstractManager;
import sg.atom.core.execution.AtomicAction;

/**
 * GameAction is a unit of execution for the GameStage level context.
 *
 * <p>This implementation work upon existed JME3 architecture of GameLoop and
 * open the possibilites to modern Actor architecture.
 *
 * <p>After registered with GameStageManager, this action are managed by the
 * specific Actor. The dataflow that the action cause run through Manager
 * pipeline.
 *
 * <p>The connection between Actor and Action going to be clarify after doing
 * better research about Actor framework such as AKKA!
 *
 *
 * FIXME: Move GameAction to AKKA.
 *
 * @author atomix
 */
public abstract class GameAction implements AtomicAction {

    public abstract void init(Application app, AbstractManager... managers);

    public abstract void actionStart();

    public abstract void actionEnd();

    public void register(AbstractManager... managers) {
    }

    public void notifyAction() {
    }
}
