/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.geo.map;

import java.util.Collection;

/**
 * An abstract map "area", which has a boundary determinate the site it cover.
 *
 * <p>In theory, an area can be continuous or made up from parts (isles), a
 * MapRegion has to be one continuous piece so its boundaries have to be closed.
 * The area usually determinated by a geometry shape like a rect or a circle as
 * its boundary shape. Consider a pixelate version of a circle as an example of
 * an tile area in real application.</p>
 *
 * @author hungcuong
 */
public interface MapRegion<T> {

    public Collection<T> getCoveredCells();

    public void travel(T currentTile, Runnable action);
}
