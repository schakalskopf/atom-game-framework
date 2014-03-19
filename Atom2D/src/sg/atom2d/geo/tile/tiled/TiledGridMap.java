/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.geo.tile.tiled;

import sg.atom2d.assets.tiled.MapStructure;
import sg.atom2d.geo.map.DefaultMap2D;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class TiledGridMap extends DefaultMap2D {

    MapStructure mapStructure;

    public TiledGridMap(MapStructure mapStructure) {
        super(mapStructure.width, mapStructure.height);
    }

    public TiledGridMap(int width, int height) {
        super(width, height);
    }
}
