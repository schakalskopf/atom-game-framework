package sg.atom.utils.collection;

/**
 * High performance pair.
 * 
 * @author cuong.nguyenmanh2
 */
@Deprecated
public class IntPair {

    public IntPair(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int x, y;

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
}
