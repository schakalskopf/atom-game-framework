/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.repository;

import java.util.Collection;
import sg.atom.utils.factory.IAtomFactory;

/**
 * IRepository implements Repository pattern. In a Repository, a database for
 * example, we can store and query object in different ways.
 *
 * <p>Extension of Factory and Transformer pattern with search feature: A
 * repository also associated with Factories who generally create new data for
 * it or for user. Repository is a special kind of Map (query) and List(sequence
 * and indexing) together with search feature. A Repository provide views for
 * the user, via query, via search or a full view (thread scope or jvm scope).
 * So chances for the application to specify sessions and cache function
 * internal.</p>
 *
 * <p>AtomRepository is the first class implement IRepository,"in-memory" mini
 * database maximize the usage of Guava and Guice. It have pools, gates, caches
 * and suitable for concurrent access.</p>
 *
 *
 * <p>AtomDB has expansion of in-memory Repository supported MapDB, H2 etc. For
 * JDBC Repository use another implementation (such as SamSkivert Repository) in
 * AtomDB.
 *
 * @author CuongNguyen
 */
@Deprecated
public interface IRepository<K, V> {

    public IAtomFactory getFactory(Class clazz);

    public V query(Object... params);

    public Collection<V> search(Object... params);

    public Collection<V> getAllEntries();

    public void store(K key, V value);

    public V get(K key);
}
