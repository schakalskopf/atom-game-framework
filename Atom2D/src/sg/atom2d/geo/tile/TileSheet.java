/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.geo.tile;

import com.jme3.math.Vector2f;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import sg.atom.utils.datastructure.collection.Pair;
import sg.atom2d.geo.map.Map2D;

/**
 * A template of tiles which can perform lookup like a lookup table, with index
 * and range.
 *
 * <p>A TileSheet is a "common" data structure to store "orginal" Tile. Custom
 * TileSheet can extends this class to provide more functions. By nature,
 * TileSheet usually require non-frequent memory allocation but frequent memory
 * access, that's why this implementation use Guava Cache to implement it.
 *
 * <p>Different between TileSheet and GridMapTile is in their fundaments about
 * space and dimenisons. TileSheet dont aware there is a Grid exits. They work
 * as a naive Map2D.
 *
 * <p>TileSheet provide view and transformation of their children, that's the
 * lookup aspects. Like look through a lens or window to see objects. This also
 * fundamental different with GridMap, which guaranty to return the same
 * objects.
 *
 * <p>TileSheet don't have as much partitioning functions as GridMapTile but
 * usually also have partitioning functions, the pf are call through
 * split(function), this enable features like to generate TextureAtlas.
 *
 * FIXME: Intergrate with Repository pattern.
 *
 * @author cuong.nguyenmanh2
 */
public class TileSheet implements Map2D<Tilable> {
    // Attributes. FIXME: Non-grid version plz!

    protected int width;
    protected int height;
    protected float scaleX;
    protected float scaleY;
    protected int cols;
    protected int rows;
    // FIXME: Replace with Cache.
    protected ArrayList<Tile2D> tileList;

    public TileSheet(int width, int height) {
        this.width = width;
        this.height = height;
        this.cols = width;
        this.rows = height;
        splitTile();
    }

    public void splitTile() {
        if (tileList != null) {
            tileList.clear();
        } else {
            tileList = new ArrayList<Tile2D>(width * height);
        }
        evenSplit();
    }

    public void evenSplit() {
        float sx = 1f / width;
        float sy = 1f / height;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Tile2D newTile = new Tile2D();
                newTile.topLeft = new Vector2f(x * sx, y * sy);
                newTile.topRight = new Vector2f((x + 1) * sx, y * sy);
                newTile.bottomLeft = new Vector2f(sx * x, (y + 1) * sy);
                newTile.bottomRight = new Vector2f((x + 1) * sx, (y + 1) * sy);
                tileList.add(newTile);
            }
        }
    }

    public Tile2D get(int index) {
        return tileList.get(index);
    }

    public List<Map2D> getChildren() {
        return null;
    }

    public boolean isInside(Pair position) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Tilable getAt(Pair position) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Tilable putAt(Pair position, Tilable tile) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Set<Pair> coveredPosition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
