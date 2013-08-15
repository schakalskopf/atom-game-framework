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
public abstract class ExternalAction extends GameAction {

    public abstract void actionContact(Object obj);

    public abstract float canAffect(Object obj);
    
    public abstract void react(Object obj);
}
