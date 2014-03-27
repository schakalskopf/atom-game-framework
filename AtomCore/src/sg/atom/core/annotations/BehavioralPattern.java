/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.annotations;

import com.jme3.app.state.AppState;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark for an object which embed as a behavior of its container or underlied
 * subject.
 *
 * <p>Concepts from Behavioral pattern:
 * http://en.wikipedia.org/wiki/Behavioral_pattern
 *
 * <ul> <li>Chain of responsibility pattern: Command objects are handled or
 * passed on to other objects by logic-containing processing objects
 *
 * <li>Command pattern: Command objects encapsulate an action and its parameters
 *
 * <li>"Externalize the Stack": Turn a recursive function into an iterative one
 * that uses a stack[1]
 *
 * <li>Hierarchical visitor pattern: Provide a way to visit every node in a
 * hierarchical data structure such as a tree
 *
 * <li>Interpreter pattern: Implement a specialized computer language to rapidly
 * solve a specific set of problems
 *
 * <li>Iterator pattern: Iterators are used to access the elements of an
 * aggregate object sequentially without exposing its underlying representation
 *
 * <li>Mediator pattern: Provides a unified interface to a set of interfaces in
 * a subsystem
 *
 * <li>Memento pattern: Provides the ability to restore an object to its
 * previous state (rollback)
 *
 * <li>Null Object pattern: Designed to act as a default value of an object
 *
 * <li>Observer pattern: aka Publish/Subscribe or Event Listener. Objects
 * register to observe an event that may be raised by another object Weak
 * reference pattern: De-couple an observer from an observable[2]
 *
 * <li>Protocol stack: Communications are handled by multiple layers, which form
 * an encapsulation hierarchy[3]
 *
 * <li>Scheduled-task pattern: A task is scheduled to be performed at a
 * particular interval or clock time (used in real-time computing)
 *
 * <li>Single-serving visitor pattern: Optimise the implementation of a visitor
 * that is allocated, used only once, and then deleted
 *
 * <li>Specification pattern: Recombinable business logic in a boolean fashion
 *
 * <li>State pattern: A clean way for an object to partially change its type at
 * runtime
 *
 * <li>Strategy pattern: Algorithms can be selected on the fly
 *
 * <li>Template method pattern: Describes the program skeleton of a program
 * <li>Visitor pattern: A way to separate an algorithm from an object </ul>
 *
 * @author CuongNguyen
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface BehavioralPattern {

    public static String PATTERN_CHAIN = "Chain";
    public static String PATTERN_COMMAND = "Command";
    public static String PATTERN_VISITOR = "Visitor";
    public static String PATTERN_INTEPRETER = "Intepreter";
    public static String PATTERN_ITERATOR = "Iterator";
    public static String PATTERN_MEDIATOR = "Mediator";
    public static String PATTERN_MEMENTO = "Memento";
    public static String PATTERN_NULL = "Null";
    public static String PATTERN_OBSERVER = "Observer";
    public static String PATTERN_PROTOCOL = "Protocol";
    public static String PATTERN_SCHEDULER = "Scheduler";
    public static String PATTERN_SPECIFICATION = "Specification";
    public static String PATTERN_STATE = "State";
    public static String PATTERN_STRATEGY = "Strategy";
    public static String PATTERN_TEMPLATE_METHOD = "TemplateMethod";
    public static String PATTERN_HIERARCHICAL_VISITOR = "HierarchicalVisitor";

    String patternName() default PATTERN_STATE;

    Class target() default AppState.class;
}
