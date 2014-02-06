package sg.atom.logic.trigger;

/**
 * Represent a piece of logic
 * @author atomix
 */
public interface Trigger {
    public abstract void actived();
    public abstract void deactived();
    public abstract void setEnable(boolean enable);
    public boolean isEnabled();
}
