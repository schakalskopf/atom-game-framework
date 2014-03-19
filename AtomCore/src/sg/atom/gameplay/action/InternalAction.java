/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.action;

import com.jme3.app.Application;
import java.util.concurrent.atomic.AtomicInteger;
import sg.atom.core.AbstractManager;
import sg.atom.gameplay.GameAction;

/**
 * Action only affect the internal of the Actor. Messaging are included. This
 * can be used in various usecase but not forced to use in every. This just a
 * warper for simplize dataflow and better monitoring.
 *
 * <p>
 *
 * @author atomix
 */
public abstract class InternalAction extends GameAction {

    private Application app;

    @Override
    public AtomicInteger getIndex() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void init(Application app, AbstractManager... managers) {
        this.app = app;
    }

    public void performAction() {
        actionStart();
        actionEnd();
    }
}
