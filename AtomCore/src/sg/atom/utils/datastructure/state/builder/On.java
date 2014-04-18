package sg.atom.utils.datastructure.state.builder;

import sg.atom.utils.datastructure.state.Condition;

public interface On<S, E, C> extends When<S, E, C> {

	When<S, E, C> when(Condition<C> condition);
}
