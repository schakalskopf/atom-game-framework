package sg.atom.utils.datastructure.state.builder;

public interface To<S, E, C> {
	On<S, E, C> on(E event);
}
