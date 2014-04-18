package sg.atom.utils.datastructure.state.builder;

public interface From<S, E, C> {
	To<S, E, C> to(S toState);
}
