/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.action;

import com.jme3.app.Application;
import sg.atom.core.AbstractManager;
import sg.atom.gameplay.GameAction;

/**
 *
 * @author atomix
 */
public abstract class ExternalAction extends GameAction {
    private Application app;
    @Override
    public void init(Application app, AbstractManager... managers) {
        this.app = app;
    }
    public abstract void actionContact(Object obj);

    public abstract float canAffect(Object obj);
    
    public abstract void react(Object obj);
}
