package sg.atom.utils.datastructure.state;

public interface Transition<S, C> {

    S getTo();

    boolean isSatisfied(C context);
}
