package sg.atom.logic.conditions;

/**
 * Condition is primitive logic brick of a Game framework. It's simply a warper
 * of Boolean, and boolean check.
 *
 * <br> Use Predicate instead!
 *
 * @author atomix
 */ @Deprecated
public abstract class Condition implements Conditional{

    public boolean status;

}
