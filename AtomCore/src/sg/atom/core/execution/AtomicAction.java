/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.execution;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicAction are context based - indexed Runnable Callable with all atomic
 * attributes. It can be run freely in Java programming language. AtomicAtion
 * are different from a runnable also Closure. Also this in Actor level not in
 * Entity level. It also should be distinguish with AtomTask, in which it stick
 * to Actor not Managers.
 *
 * <p>TODO:Experimental concept
 *
 * <p>TODO: Relationship to Runable, Context, Thread, ThreadLocal and Atomicity?
 *
 * <p>Note that implementation such as GameAction can violent the design of
 * AtomicAction, but in "critial" aspect, it follow this design.
 *
 * @author cuong.nguyenmanh2
 */
public interface AtomicAction extends Callable, Runnable {

    public AtomicBoolean isContexted();

    public AtomicInteger getIndex();
}
