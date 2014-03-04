/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

import com.jme3.app.Application;
import sg.atom.core.AbstractManager;

/**
 * GameAction is a unit of execution for the GameStage level.
 *
 * <p>This implementation work upon existed JME3 architecture of GameLoop and
 * open the possibilites to modern Actor architecture.</p>
 *
 * <p>The connection between Actor and Action going to be clarify after doing
 * better research about Actor framework such as AKKA!</p>
 *
 * FIXME: Move GameAction to AKKA
 *
 * @author atomix
 */
public abstract class GameAction {

    public abstract void init(Application app, AbstractManager... managers);

    public abstract void actionStart();

    public abstract void actionEnd();
}
