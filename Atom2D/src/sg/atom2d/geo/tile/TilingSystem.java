/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.geo.tile;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Table;
import java.util.Collection;
import java.util.Map;
import sg.atom.algorimth.travel.ITravel;

/**
 * A tiling system offer a context for tiling jobs and handy methods for Tile,
 * TileSheet and TileRule.
 *
 * <p>A TilingSystem is a "Common" approach for tiling problem in common games.
 * It's the "one place" for tiling job, has any info a Tile,TileSheet,TileRule
 * needs. Manage them. And more over, a bridge to access the overlay data above/
 * outside of the system.</p>
 *
 * <p>For example: if a grass tile only grow in the highland country, in which
 * the TileRule have to ask the "height" of the Cell in the GridMap. A Guava
 * Function has to transform the real location of the Tile embeding in the
 * GridMap and return it to the TileRule.</p>
 *
 * <p>TilingSystem also should provide a a monitoring facilities for its
 * operated Tiling jobs. This is a simple of ShapeGrammarSystem architecture.
 * Take a look at CityGen framework to learn more about ShapeGrammar.
 *
 * @author cuong.nguyenmanh2
 */
public interface TilingSystem<T extends Tilable> {

    public Tilable getTileAt(Object location);

    public void setTileAt(Object location, Tilable tile);

    public void fillTiles(Object location, Tilable tile);

    public void fillTiles(Object location, Function<Tilable, Tilable> function);

    public void fillTiles(Object location, Predicate<Tilable> condition);

    public Collection<Tilable> getRelatives(Object location, ITravel travel);

    public Collection<Tilable> getRelatives(Object location, Predicate<Tilable> condition);

    public Map<Object, Tilable> getRelativesMap(Object location, Predicate<Tilable> condition);

    public Table<Object, Object, Tilable> getRelativesAligned(Object location, Predicate<Tilable> condition);

    public void notifyTileEvent(Object location, Tilable tile);
}
