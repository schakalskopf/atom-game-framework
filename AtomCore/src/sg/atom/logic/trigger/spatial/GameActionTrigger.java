/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.logic.trigger.spatial;

import sg.atom.logic.trigger.ConditionalTrigger;
import sg.atom.gameplay.GameAction;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class GameActionTrigger extends ConditionalTrigger {

    GameAction action;

    @Override
    public void actived() {
        action.actionStart();
    }

    @Override
    public void deactived() {
        action.actionEnd();
    }
}
