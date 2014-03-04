/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.geo.tile;

/**
 * A tiling system offer a context for tiling jobs and handy methods for
 * Tile, TileSheet and TileRule.
 *
 * <p>A TilingSystem is a "Common" approach for tiling problem in common games.
 * It's the "one place" for tiling job, has any info a Tile,TileSheet,TileRule
 * needs. Manage them. And more over, a bridge to access the overlay data above/
 * outside of the system. </p>
 *
 * <p>For example: if a grass tile only grow in the highland country, in which
 * the TileRule have to ask the "height" of the Cell in the GridMap. A Guava
 * Function has to transform the real location of the Tile embeding in the
 * GridMap and return it to the TileRule.</p>
 *
 * @author cuong.nguyenmanh2
 */
public interface TilingSystem<T extends Tile> {
}
