/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.geo.map;

import java.util.ArrayList;
import java.util.List;
import sg.atom2d.geo.tile.Tile;
import sg.atom2d.geo.tile.TilingSystem;

/**
 *
 * @author cuong.nguyenmanh2
 */
/**
 * Contains layers of GridMap.
 *
 * @author cuong.nguyenmanh2
 */
public class DefaultMap2D implements Map2D, TilingSystem<Tile> {

    ArrayList<GridMap> layers;

    public List<GridMap> getLayers() {
        return new ArrayList<GridMap>();
    }
}
