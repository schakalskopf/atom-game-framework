/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.game2d.graphics.anim.spine;

import sg.atom.utils.collection.Array;
import sg.atom2d.game2d.graphics.anim.spine.event.Event;
import sg.atom2d.game2d.graphics.anim.spine.skeleton.Skeleton;

/**
 *
 * @author cuong.nguyenmanh2
 */
public interface Timeline {

    /**
     * Sets the value(s) for the specified time.
     */
    public void apply(Skeleton skeleton, float lastTime, float time, Array<Event> events, float alpha);
    
}
