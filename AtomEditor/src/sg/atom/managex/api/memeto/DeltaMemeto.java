/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.managex.api.memeto;

import sg.atom.utils.datastructure.delta.Delta;

/**
 * Delta memeto just save the changes from the object through an edit. It has to
 * corporate tightly with the {@link IMemetoEdit} and {@link Delta}.
 *
 * @author cuong.nguyenmanh2
 */
public abstract class DeltaMemeto<T> implements IObjectiveMemeto<T> {
    Delta<T> delta;
}
