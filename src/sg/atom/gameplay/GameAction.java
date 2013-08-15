/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay;

import sg.atom.gameplay.actor.Actor;

/**
 *
 * @author hungcuong
 */
public abstract class GameAction {

    public abstract boolean canDo(Actor who);

    public abstract void actionStart();

    public abstract void actionEnd();

    public abstract void performAction();
}
