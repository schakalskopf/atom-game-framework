/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.factory;

/**
 * This class is the migration to maximize usage of the Factory pattern in the
 * Atom framework.
 *
 * Note that this solution belonged to the era before Dependency injection.
 *
 * @author cuong.nguyenmanh2
 */
public interface AtomFactory<T> {

    public T create(Object param);

    public T create(Object... params);

    public T cloneObject(T orginal);
}