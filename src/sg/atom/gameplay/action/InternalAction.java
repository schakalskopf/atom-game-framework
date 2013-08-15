/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.action;

import sg.atom.gameplay.GameAction;

/**
 *
 * @author hungcuong
 */
public abstract class InternalAction extends GameAction {

    public void performAction() {
        actionStart();
        actionEnd();
    }
}
