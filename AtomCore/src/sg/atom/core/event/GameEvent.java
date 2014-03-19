package sg.atom.core.event;

@Deprecated
/**
 * For more common case, Trigger provide more methods and attributes.
 *
 * @author atomix
 */
public abstract class GameEvent<L> {

    public abstract void notify(L listener);
}
