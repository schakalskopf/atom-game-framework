/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.geo.tile;

import java.util.Collection;
import sg.atom.utils.collection.Pair;

/**
 * An abstract tile rewrite rule (generative, transform, production) affect in
 * an area.
 *
 * <p>TilingRule is a "common" approach in games for tilling problem. A
 * TilingRule take an input (single parameter or a collection of tiles as input)
 * and then make changes to its associated data for ex:transform it into
 * different form. For ex: make a transition between "Grass" tile and "Dirt"
 * tile is a very common scenario.</p>
 *
 * <p>A TilingRule often ask a tilingsytem for the nessesary info in it work,
 * for ex: the neighbour of a tile or an area.</p>
 *
 * FIXME: Replace with Guava convertor.
 *
 * @author cuong.nguyenmanh2
 */
public interface TilingRule<T extends Tilable> {

    public void apply(Object location, Object input);

    public void apply(Object location, Collection<T> inputs);
}
