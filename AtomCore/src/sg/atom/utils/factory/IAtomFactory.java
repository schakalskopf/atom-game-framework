/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.factory;

import com.google.common.base.Supplier;

/**
 * This class is the migration to maximize usage of the Factory pattern in the
 * Atom framework.
 *
 * <p>Note that this solution belonged to the era "before" Dependency injection.
 *
 * <p>TODO: Going to merge with Guava Supplier.
 * 
 * <p>Features:<ul>
 * 
 * </ul>
 *
 * @author cuong.nguyenmanh2
 */
@Deprecated
public interface IAtomFactory<T> extends Supplier<T> {

    public T create(Object param);

    public T create(Object... params);

    public T cloneObject(T orginal);
}
