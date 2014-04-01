package sg.atom.core.event;


/**
 * For more common case, Trigger provide more methods and attributes.
 *
 * @author atomix
 */@Deprecated
public abstract class GameEvent<L> {

    public abstract void notify(L listener);
}
