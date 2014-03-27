/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.geo.map;

import sg.atom.utils.algorimth.travel.ITravel;
import sg.atom.utils.datastructure.collection.Pair;

/**
 * A Travel in Grid Space.
 * 
 * This implementation use a Hilbert walk underlying.
 *
 * @author cuong.nguyenmanh2
 */
public abstract class GridTravel<T, R> implements ITravel<T, R> {

    public static enum GridTravelMethod {

        RowCol, ColRow, Col, Row, Directional, Floodfill, Fractal, RandomUniform, RandomMonteCarlo
    }

    public static enum GridRelativePostion {

        Left, Right, Top, Bottom
    }

    public static enum GridDirection {

        North, East, South, West, NorthWest, NorthEast, SouthWest, SouthEast
    }

    public abstract R visit(int x, int y, T target);

    public GridRelativePostion from(GridDirection directionEx) {
        return GridRelativePostion.Bottom;
    }

    public GridDirection compose(GridDirection first, GridDirection second) {
        return GridDirection.East;
    }

    public GridDirection compose(Pair<GridDirection, GridDirection> pair) {
        return GridDirection.East;
    }
}
