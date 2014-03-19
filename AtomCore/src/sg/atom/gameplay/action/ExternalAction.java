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
 * Actor affect other Actors. Messaging are included, broadcast by default.
 *
 * @author atomix
 */
public abstract class ExternalAction extends GameAction {

    private Application app;

    @Override
    public AtomicInteger getIndex() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void init(Application app, AbstractManager... managers) {
        this.app = app;
    }

    public abstract void actionContact(Object obj);

    public abstract float canAffect(Object obj);

    public abstract void react(Object obj);
}
