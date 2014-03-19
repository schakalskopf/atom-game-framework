/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.geo.map;

import java.util.List;
import java.util.Set;
import sg.atom.utils.collection.Pair;

/**
 * Positional map in 2D.
 *
 * <p>Contains children (layers, coodinates, entities).
 *
 * <p>Pair is generic container represent 2 dimentional object. This class is
 * unsafe but for the ease of use is fine.
 *
 * @author cuong.nguyenmanh2
 */
public interface Map2D<T> {

    public List<Map2D> getChildren();

    public boolean isInside(Pair position);

    public T getAt(Pair position);

    public T putAt(Pair position, T tile);

    public Set<Pair> coveredPosition();
}
