/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.gameplay.action.common;

import sg.atom.utils.algorimth.optimization.Optimizable;
import sg.atom.core.annotations.Atom;
import sg.atom.gameplay.action.InternalAction;

/**
 * Directional and transform action.
 *
 * <p>This action affect directly to the scenegraph. This predecated class is
 * example of how Internal action real work. It's also marked as Atom,
 * Optimizable by default.
 *
 * <p>Used for test spatial transformation and behaviours, such as AI steering
 * of Actor(agent) ... etc
 *
 * @author atomix
 */
@Atom
@Optimizable
public class MoveAction extends InternalAction {
    // Movement

    public boolean forward = false, backward = false, left = false, right = false;
    public float speed = .05f;
    public boolean jump = false;

    public boolean isWalking() {
        return forward || backward || left || right;
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
