package sg.atom.utils.datastructure.state.automata;

import java.util.Set;

/**
 * @author Dan Klein (klein@cs.stanford.edu)
 */
public interface Block<E> {

  public Set<E> getMembers();

}
