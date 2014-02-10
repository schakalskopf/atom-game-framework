package sg.atom.core.event;

@Deprecated
/**
 *
 * @author atomix 
 * 
 * For more common case, Trigger provide more methods and
 * attribute.
 */
public abstract class GameEvent<L> {

    public abstract void notify(L listener);
}
