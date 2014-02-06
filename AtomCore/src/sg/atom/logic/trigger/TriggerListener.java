package sg.atom.logic.trigger;

/**
 *
@author atomix
 */
@Deprecated
public interface TriggerListener<T extends Trigger> {
    public void enter(T trigger);
    public void exit(T trigger);
}
