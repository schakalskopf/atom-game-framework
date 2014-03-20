/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.algorimth.travel;

/**
 * Interface for travelling arround in sequential order, in list, tree or graph.
 *
 * <p>It can be seen as a single irteration in collection but promise result.
 * This walk is different from TreeTraverser.
 *
 * @author cuong.nguyenmanh2
 */
public interface ITravel<T, R> {

    public R visit(T node);
}
