package sg.atom.logic.trigger;

/**
 * Represent a piece of logic.
 *
 * Trigger is a simple "2 states basis" action. It works as "on-off" and cause
 * something happen.
 *
 * @author atomix
 */
public interface Trigger {

    public abstract void actived();

    public abstract void deactived();

    public abstract void setEnable(boolean enable);

    public boolean isEnabled();
}
