/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.geo.tile;

/**
 *
 * @author cuong.nguyenmanh2
 */
public interface TilingRule<T extends Tile> {
    public void apply(T thisTile);
}
