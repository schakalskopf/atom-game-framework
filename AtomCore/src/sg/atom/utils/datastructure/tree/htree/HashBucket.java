
package sg.atom.utils.datastructure.tree.htree;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.ArrayList;

/**
 * A bucket is a placeholder for multiple (key, value) pairs.  Buckets
 * are used to store collisions (same hash value) at all levels of an
 * H*tree.
 *
 * There are two types of buckets: leaf and non-leaf.
 *
 * Non-leaf buckets are buckets which hold collisions which happen
 * when the H*tree is not fully expanded.   Keys in a non-leaf buckets
 * can have different hash codes.  Non-leaf buckets are limited to an
 * arbitrary size.  When this limit is reached, the H*tree should create
 * a new Directory page and distribute keys of the non-leaf buckets into
 * the newly created Directory.
 *
 * A leaf bucket is a bucket which contains keys which all have
 * the same <code>hashCode()</code>.  Leaf buckets stand at the
 * bottom of an H*tree because the hashing algorithm cannot further
 * discriminate between different keys based on their hash code.
 *
 *  @author <a href="mailto:boisvert@intalio.com">Alex Boisvert</a>
 *  @version $Id: HashBucket.java,v 1.2 2005/06/25 23:12:32 doomdark Exp $
 */
final class HashBucket
    extends HashNode
    implements Externalizable
{

    final static long serialVersionUID = 1L;

    /**
     * The maximum number of elements (key, value) a non-leaf bucket
     * can contain.
     */
    public static final int OVERFLOW_SIZE = 8;


    /**
     * Depth of this bucket.
     */
    private int _depth;


    /**
     * Keys in this bucket.  Keys are ordered to match their respective
     * value in <code>_values</code>.
     */
    private ArrayList _keys;


    /**
     * Values in this bucket.  Values are ordered to match their respective
     * key in <code>_keys</code>.
     */
    private ArrayList _values;


    /**
     * Public constructor for serialization.
     */
    public HashBucket() {
        // empty
    }


    /**
     * Construct a bucket with a given depth level.  Depth level is the
     * number of <code>HashDirectory</code> above this bucket.
     */
    public HashBucket( int level )
    {
        if ( level > HashDirectory.MAX_DEPTH+1 ) {
            throw new IllegalArgumentException(
                            "Cannot create bucket with depth > MAX_DEPTH+1. "
                            + "Depth=" + level );
        }
        _depth = level;
        _keys = new ArrayList( OVERFLOW_SIZE );
        _values = new ArrayList( OVERFLOW_SIZE );
    }


    /**
     * Returns the number of elements contained in this bucket.
     */
    public int getElementCount()
    {
        return _keys.size();
    }


    /**
     * Returns whether or not this bucket is a "leaf bucket".
     */
    public boolean isLeaf()
    {
        return ( _depth > HashDirectory.MAX_DEPTH );
    }


    /**
     * Returns true if bucket can accept at least one more element.
     */
    public boolean hasRoom()
    {
        if ( isLeaf() ) {
            return true;  // leaf buckets are never full
        } else {
            // non-leaf bucket
            return ( _keys.size() < OVERFLOW_SIZE );
        }
    }


    /**
     * Add an element (key, value) to this bucket.  If an existing element
     * has the same key, it is replaced silently.
     *
     * @return Object which was previously associated with the given key
     *          or <code>null</code> if no association existed.
     */
    public Object addElement( Object key, Object value )
    {
        int existing = _keys.indexOf(key);
        if ( existing != -1 ) {
            // replace existing element
            Object before = _values.get( existing );
            _values.set( existing, value );
            return before;
        } else {
            // add new (key, value) pair
            _keys.add( key );
            _values.add( value );
            return null;
        }
    }


    /**
     * Remove an element, given a specific key.
     *
     * @param key Key of the element to remove
     *
     * @return Removed element value, or <code>null</code> if not found
     */
    public Object removeElement( Object key )
    {
        int existing = _keys.indexOf(key);
        if ( existing != -1 ) {
            Object obj = _values.get( existing );
            _keys.remove( existing );
            _values.remove( existing );
            return obj;
        } else {
            // not found
            return null;
        }
    }


    /**
     * Returns the value associated with a given key.  If the given key
     * is not found in this bucket, returns <code>null</code>.
     */
    public Object getValue( Object key )
    {
        int existing = _keys.indexOf(key);
        if ( existing != -1 ) {
            return _values.get( existing );
        } else {
            // key not found
            return null;
        }
    }


    /**
     * Obtain keys contained in this buckets.  Keys are ordered to match
     * their values, which be be obtained by calling <code>getValues()</code>.
     *
     * As an optimization, the Vector returned is the instance member
     * of this class.  Please don't modify outside the scope of this class.
     */
    ArrayList getKeys()
    {
        return this._keys;
    }


    /**
     * Obtain values contained in this buckets.  Values are ordered to match
     * their keys, which be be obtained by calling <code>getKeys()</code>.
     *
     * As an optimization, the Vector returned is the instance member
     * of this class.  Please don't modify outside the scope of this class.
     */
    ArrayList getValues()
    {
        return this._values;
    }


    /**
     * Implement Externalizable interface.
     */
    public void writeExternal( ObjectOutput out )
        throws IOException
    {
        out.writeInt( _depth );

        int entries = _keys.size();
        out.writeInt( entries );

        // write keys
        for (int i=0; i<entries; i++) {
            out.writeObject( _keys.get( i ) );
        }
        // write values
        for (int i=0; i<entries; i++) {
            out.writeObject( _values.get( i ) );
        }
    }


    /**
     * Implement Externalizable interface.
     */
    public void readExternal(ObjectInput in)
    throws IOException, ClassNotFoundException {
        _depth = in.readInt();

        int entries = in.readInt();

        // prepare array lists
        int size = Math.max( entries, OVERFLOW_SIZE );
        _keys = new ArrayList( size );
        _values = new ArrayList( size );

        // read keys
        for ( int i=0; i<entries; i++ ) {
            _keys.add( in.readObject() );
        }
        // read values
        for ( int i=0; i<entries; i++ ) {
            _values.add( in.readObject() );
        }
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("HashBucket {depth=");
        buf.append(_depth);
        buf.append(", keys=");
        buf.append(_keys);
        buf.append(", values=");
        buf.append(_values);
        buf.append("}");
        return buf.toString();
    }
}
