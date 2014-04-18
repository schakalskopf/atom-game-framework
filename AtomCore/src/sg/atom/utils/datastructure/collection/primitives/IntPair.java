package sg.atom.utils.datastructure.collection.primitives;

import sg.atom.utils.datastructure.collection.Pair;

/**
 * High performance pair.
 *
 * @author cuong.nguyenmanh2
 */
@Deprecated
public class IntPair {

    public int x, y;

    public IntPair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object obj) {
        if (obj instanceof IntPair) {
            return equals((IntPair) obj);
        }
        return false;
    }

    public boolean equals(IntPair p) {
        if (x == p.x && y == p.y) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public Pair<Integer, Integer> asGeneric() {
        return new Pair<Integer, Integer>(x, y);
    }
}
