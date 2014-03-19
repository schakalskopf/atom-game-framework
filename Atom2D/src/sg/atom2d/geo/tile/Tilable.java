/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.geo.tile;

/**
 * Tiable is a interface for object that meant to be tiled around in space.
 *
 * <p>It has to have positional attributes and should be clonable. The "tiling"
 * also include the meaning that a Tile should be "relative" to others in a
 * context. This context can be a sheet or a result of tiling job.
 *
 * <p>Align proposed a method for the tiler to "tile" this object.
 *
 * @author cuong.nguyenmanh2
 */
public interface Tilable extends Cloneable {

    public Object getPosition();

    public Object getRelative(Object otherPosition);

    public void setRelative(Tilable otherTile, Object otherPosition);

    public void align(Object position);
}
