/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.stage.actor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicAction are indexed Runnable with all atomic attributes. It can be run
 * freely in Java programming language. AtomicAtion are different from a
 * runnable also Closure. Also this in Actor level not in Entity level. It also
 * should be distinguish with AtomTask, in which it stick to Actor not Managers.
 *
 * <p>Note that implementation such as GameAction can violent the design of
 * AtomicAction, but in "critial" aspect, it follow this design.
 *
 * @author cuong.nguyenmanh2
 */
public interface AtomicAction {

    public AtomicInteger getIndex();
}
