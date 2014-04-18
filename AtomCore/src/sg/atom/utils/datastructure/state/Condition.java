package sg.atom.utils.datastructure.state;

public interface Condition<C> {

    boolean isSatisfied(C context);
}
