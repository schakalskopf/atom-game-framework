/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.utils.repository;

import java.util.Collection;
import sg.atom.utils.factory.IAtomFactory;

/**
 * <p>AtomRepository is the first implementation of IRepository, with maximize
 * the usage of Guava and Guice to implement an "in-memory" mini datacenter. It
 * have pools, gates, caches and suitable for concurrent access. One can compare
 * and choose between this in memory datacenter and a TupleSpace with agents or
 * Topology in the flow package. This one this simplier compare to TupleSpace
 * and more complex than Flow's Topology; and use only few well-defined query to
 * query upon the data:
 *
 * <p><b>Linda and TupleSpace:</b>The original Linda model requires four
 * operations that individual workers perform on the tuples and the tuplespace:
 *
 * in atomically reads and removes—consumes—a tuple from tuplespace; rd
 * non-destructively reads a tuplespace; out produces a tuple, writing it into
 * tuplespace; eval creates new processes to evaluate tuples, writing the result
 * into tuplespace.
 *
 * <p><b>AtomRepository</b> only offer there operations: insert/ remove, then
 * accumulate changes with a ChangeQueue by default. It can be config to use
 * more efficient (non-blocking) methods using gpars. AtomRepository is a kind
 * of MVCC database.
 *
 * <p><b>AtomFlowTopology</b> only offer two operations: insert/ remove; Changes
 * affect directly to the model.
 *
 * <p>It can lock write operation and allow batch tasks, usually reffered as
 * MapReduce in it data. Whenever it locked, queries can query and read from it
 * really quick.
 *
 * <p>It opens ports as Agent to work upon its data. <p>Going to extend with
 * Guava supplier.
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
