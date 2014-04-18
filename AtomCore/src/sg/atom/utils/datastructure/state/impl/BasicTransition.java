package sg.atom.utils.datastructure.state.impl;

import sg.atom.utils.datastructure.state.Action;
import sg.atom.utils.datastructure.state.Condition;
import sg.atom.utils.datastructure.state.StateMachine;
import sg.atom.utils.datastructure.state.Transition;
import sg.atom.utils.datastructure.state.util.GuavaReplacement;

import java.util.Collection;

public class BasicTransition<S, E, C> implements Transition<S, C> {
	private final S to;
	private final Condition<C> condition;
	private final Collection<Action<S, E, C>> actions = GuavaReplacement.newArrayList();

	public BasicTransition(S to, Condition<C> condition, Collection<Action<S, E, C>> actions) {
		this.to = to;
		this.condition = condition;
		this.actions.addAll(actions);
	}

	@Override
	public S getTo() {
		return to;
	}

	@Override
	public boolean isSatisfied(C context) {
		return condition.isSatisfied(context);
	}

	public Condition<C> getCondition() {
		return condition;
	}

	public void onTransition(S from, S to, E event, C context, StateMachine<S, E, C> statemachine) {
		for (Action<S, E, C> action : actions) {
			action.onTransition(from, to, event, context, statemachine);
		}
	}
}
