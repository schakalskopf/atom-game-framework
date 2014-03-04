/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.repository;

import java.util.Collection;
import sg.atom.utils.factory.IAtomFactory;

/**
 * <p>AtomRepository is the first class implement IRepository, with maximize the
 * usage of Guava and Guice to implement an "in-memory" mini datacenter. It have
 * pools, gates, caches and suitable for concurrent access.</p>
 *
 * @author CuongNguyen
 */
public class AtomRepository implements IRepository<Object, Object> {
    
    @Override
    public IAtomFactory getFactory(Class clazz) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Object query(Object... params) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Collection<Object> search(Object... params) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Collection<Object> getAllEntries() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void store(Object key, Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
