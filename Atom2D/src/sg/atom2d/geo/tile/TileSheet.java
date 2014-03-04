/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.geo.tile;

import com.jme3.math.Vector2f;
import java.util.ArrayList;

/**
 * A template of tiles which can perform lookup like a lookup table, with index
 * and range.
 *
 * A TileSheet is a "common" data structure to store "orginal" Tile. Custom
 * TileSheet can extends this class to provide more functions.
 *
 * FIXME: Intergrate with Repository pattern.
 * 
 * @author cuong.nguyenmanh2
 */
public class TileSheet {

    protected int width;
    protected int height;
    protected float scaleX;
    protected float scaleY;
    protected int cols;
    protected int rows;
    protected ArrayList<Tile> tileList;

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
            tileList = new ArrayList<Tile>(width * height);
        }
        evenSplit();
    }

    public void evenSplit() {
        float sx = 1f / width;
        float sy = 1f / height;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Tile newTile = new Tile();
                newTile.topLeft = new Vector2f(x * sx, y * sy);
                newTile.topRight = new Vector2f((x + 1) * sx, y * sy);
                newTile.bottomLeft = new Vector2f(sx * x, (y + 1) * sy);
                newTile.bottomRight = new Vector2f((x + 1) * sx, (y + 1) * sy);
                tileList.add(newTile);
            }
        }
    }

    public Tile get(int index) {
        return tileList.get(index);
    }
}
