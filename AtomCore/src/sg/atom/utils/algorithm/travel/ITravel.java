/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.algorithm.travel;

import com.google.common.base.Function;

/**
 * Interface for travelling arround in sequential order, in list, tree or graph.
 *
 * <p>It can be seen as a single irteration in collection but promise result.
 * This walk is different from TreeTraverser.
 *
 * <p>Is this a function? A Stopable function? An iterator.
 *
 * @author cuong.nguyenmanh2
 */
public interface ITravel<T, R> extends Function<T, R>, Iterable<T> {
    
    public R visit(T node);
    
    public void from(T start, T destination);
    
    public R endTravelling();
}
