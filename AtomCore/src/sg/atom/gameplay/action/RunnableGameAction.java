/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.action;

import com.jme3.app.Application;
import sg.atom.core.AbstractManager;
import sg.atom.gameplay.GameAction;

/**
 * Ultilize Runnable to use like piece of code to execute.
 * @author cuong.nguyenmanh2
 */
public class RunnableGameAction extends GameAction {

    public Runnable startAction;
    public Runnable endAction;
    public Runnable updateAction;
    private Application app;

    @Override
    public void actionStart() {
        startAction.run();
    }

    @Override
    public void actionEnd() {
        endAction.run();
    }

    public void performAction() {
        updateAction.run();
    }

    @Override
    public void init(Application app, AbstractManager... managers) {
        this.app = app;
    }
}
