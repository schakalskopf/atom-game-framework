package sg.atom.logic.trigger;

import sg.atom.core.bean.Behavioral;

/**
 * Represent a piece of game logic. (Distinguish with low level JME's input
 * Trigger). Mixed Behavioral patterns: Decorator, Chain, Command, Mediator,
 * Observer, Stragegy, Template.
 *
 * <p>Trigger is a simple "2 states basis" action. It works as "on-off" and
 * cause something happen.
 *
 * <p>By default Trigger is Behavioral of GameAction: <ul>
 *
 * <li>Decorator: Trigger can also be decorated to GameAction with Guice or
 * created normally. </ul>
 *
 * <li>Chain: Trigger can be chained. Chain of Responsibility Pattern
 *
 * The chain of responsibility pattern is based on the same principle as written
 * above. It decouples the sender of the request to the receiver. The only link
 * between sender and the receiver is the request which is sent. Based on the
 * request data sent, the receiver is picked. This is called “data-driven”. In
 * most of the behavioral patterns, the data-driven concepts are used to have a
 * loose coupling.
 *
 * <li>Command Pattern: Trigger can be translated to a Command.
 *
 * This is another of the data-driven pattern. The client invokes a particular
 * module using a command. The client passes a request, this request gets
 * propagated as a command. The command request maps to particular modules.
 * According to the command, a module is invoked.
 *
 * This pattern is different from the Chain of Responsibility in a way that, in
 * the earlier one, the request passes through each of the classes before
 * finding an object that can take the responsibility. The command pattern
 * however finds the particular object according to the command and invokes only
 * that one.
 *
 *
 * @author atomix
 */
@Behavioral
public interface Trigger {

    public abstract void actived();

    public abstract void deactived();

    public abstract void setEnable(boolean enable);

    public boolean isEnabled();
}
