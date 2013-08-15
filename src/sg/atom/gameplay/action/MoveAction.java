/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.action;

import sg.atom.gameplay.actor.Actor;

/**
 *
 * @author hungcuong
 */
public class MoveAction extends InternalAction {
    // Movement

    public boolean forward = false, backward = false, left = false, right = false;
    public float speed = .05f;
    public boolean jump = false;

    public boolean isWalking() {
        return forward || backward || left || right;
    }

    @Override
    public boolean canDo(Actor who) {
        return true;
    }

    @Override
    public void actionStart() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actionEnd() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
