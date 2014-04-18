/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils._beta.functional;

/**
 *
 * @author cuong.nguyenmanh2
 */
import java.util.Iterator;

/**
 * Interface used to select items within an iterator against a predicate.
 *
 * @author Xoppa
 */
@Deprecated
public interface Predicate<T> {

    /**
     * @return true if the item matches the criteria and should be included in
     * the iterator's items
     */
    boolean evaluate(T arg0);
}
