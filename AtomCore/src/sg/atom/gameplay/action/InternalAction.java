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
public abstract class InternalAction extends GameAction {
    private Application app;
    @Override
    public void init(Application app, AbstractManager... managers) {
        this.app = app;
    }
    public void performAction() {
        actionStart();
        actionEnd();
    }
}
