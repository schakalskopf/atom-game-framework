package sg.atom.logic.trigger;

/**
 *
 * @author cuong.nguyenmanh2
 */
public interface TriggerListener<T extends Trigger> {
    public void enter(T trigger);
    public void exit(T trigger);
}
