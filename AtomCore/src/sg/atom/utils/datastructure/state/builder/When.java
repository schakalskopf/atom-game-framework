package sg.atom.utils.datastructure.state.builder;

import sg.atom.utils.datastructure.state.Action;

import java.util.List;

public interface When<S, E, C> {
	void perform(Action<S, E, C> action);

	void perform(List<Action<S, E, C>> actions);
}
