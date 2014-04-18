package sg.atom.utils.datastructure.state.builder;

public interface Transition<S, E, C> {
	From<S, E, C> from(S fromState);

	From<S, E, C> fromAll();
}
