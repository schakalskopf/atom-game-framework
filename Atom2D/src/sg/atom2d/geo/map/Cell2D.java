/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.geo.map;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import sg.atom2d.geometry.BoxInt;
import sg.atom.utils.collection.Pair;
import sg.atom2d.geo.tile.Tilable;

/**
 * A Simple 2D Cell implemented to be element of primary GridMap of the game.
 *
 * <p>Cell contain internal data of a type. Cell can be nested but are not
 * contracted to be aligned. It's different from the GridMap. Beside of the
 * primary data, a Cell also can stored arbitrary data as decorators.
 *
 * <p>One example for this decorated attributes is the mark of adjacencies cells
 * like in a tiling system. Remember that the structure of this arbitrary data
 * is unclear. That mean you have a Map of properties and have to cast it to
 * something meaningful by your self. For high performance using of this Cell
 * class, please use typed data or built-in data. As said, this implementation
 * of Cell natively support: geographics, spatial indexing, path finding.
 *
 * <p>Built-in data:<ul>
 *
 * <li>For gridmap and tiling: 4 integer for 4 adjacencies cells
 *
 * <li>For indexing: 16 bytes for hash
 *
 * <li>For path finding: 5 integers and 5 booleans. </ul>
 *
 * @author cuong.nguyenmanh2
 */
public class Cell2D<T> implements Map2D, Tilable {

    protected int _x, _y;
    //Pathfinding 
    public int type;
    public int height; // and to 3d.
    public int texType;
    public int status;
    public int group;
    public boolean visited, checked, actived, walkable, visible;
    // adjacencies
    public int adjLeft, adjRight, adjTop, adjBottom;
    public int index;
    //indexing
    protected byte[] _hashCode;
    //real data
    T data;
    // nested cells.
    ArrayList<Cell2D> children;
    //geometry
    BoxInt bound;

    public Vector2f getLocation2D() {
        return new Vector2f();
    }

    public Vector3f getLocation3D() {
        return new Vector3f();
    }

    public T getData() {
        return data;
    }

    public List getChildren() {
        return children;
    }

    public boolean isInside(Pair position) {
        return bound.isInside((Integer) position.getFirst(), (Integer) position.getSecond());
    }

    public boolean isInside(int x, int y) {
        return bound.isInside(x, y);
    }

    public Object getAt(Pair position) {
        ArrayList<Cell2D> result = new ArrayList<Cell2D>();
        for (Cell2D c : children) {
            if (c.isInside(position)) {
                result.add(c);
            }
        }
        return result;
    }

    public Set coveredPosition() {
        HashSet result = new HashSet();
        result.add(bound);
        return result;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isDirty() {
        return false;
    }

    public Object putAt(Pair position, Object tile) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getPosition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getRelative(Object otherPosition) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void align(Object position) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setRelative(Tilable otherTile, Object otherPosition) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
