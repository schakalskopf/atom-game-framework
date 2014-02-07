/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.repository;

import java.util.Collection;
import sg.atom.utils.factory.IAtomFactory;

/**
 *
 * @author CuongNguyen
 */
public interface IRepository<K, V> {
    
    public IAtomFactory getFactory(Class clazz);
    
    public V query(Object... params);
    
    public Collection<V> search(Object... params);
    
    public Collection<V> getAllEntries();
    
    public void store(K key,V value);
}
