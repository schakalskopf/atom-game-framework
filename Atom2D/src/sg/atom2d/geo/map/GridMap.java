/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.geo.map;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Range;
import com.google.common.collect.Table;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import sg.atom.utils.datastructure.collection.Pair;

/**
 * A Generic Grid map with each Cell is an object with utils. A GridMap is a
 * discrete field of T value with integer coordinate. It's a data structure to
 * store objects whom coordinate aligned in a grid. Consider a GridMap of T can
 * act like a 2Dimensional Array of T for example if the GridMap 's dense. You
 * can tell it made of boxes, yes, other implemenation of grids are hex and tris
 * also provided within atom2d framework. This is a very sotiphicated
 * implementation, for lightweight implementation you should use IntMap2D
 * instead.
 *
 * <p>Under the curtain, it backed with a 2d array of Cell object if it's dense
 * or a Guava Table if it's spare. So there are two available constructors for
 * those purpose. This map can also be filled and painted like a bitmap,
 * generate a buffer on it, conver from and to bytes.
 *
 * <p>A GridMap can generate a RTree or QuadTree upon its stored data to speed
 * up internal query. </p>
 *
 * <p>Ten common "walk" functions avaiable in directions: <ul><li>Left, Right,
 * Top, Bottom</ul> A custom external "walk" function can be specified to run
 * upon its stored data if need. More over, GridMap can generate a Graph upon
 * its walkable/non walkable area.
 *
 * <p>Another common functions is to compare/compose maps with same
 * "resolution", which is not only same size or kind of same data meaning. Walk
 * and bitset compare in this scenario also supported.
 *
 * <p>This implementation inspired by a lot articles in the internet about
 * GridMap implementation:
 *
 * <ul> <li>DataStructure for game programmers</ul>
 *
 * <p>If you save a giant map, you may interested in streaming your map into the
 * logic or rendering pipeline. This enabled by partitioning your map and save
 * into trunk of tiles. A directional advide are also can be configed for
 * streaming scheme. Read:
 * http://gamearchitect.net/Articles/StreamingBestiary.html
 *
 * @author cuong.nguyenmanh2
 */
public class GridMap<T> implements Map2D<T>, Map<Pair, T> {

    public static enum MapDensity {

        Full, Dense, Spare
    };
    public static int DEFAUL_MAP_SIZE = 100;
    public static int DEFAUL_MAP_SIZE_WIDTH = 100;
    public static int DEFAUL_MAP_SIZE_HEIGHT = 100;
    private static Object travelResult;
    public MapDensity mapDensity;
    private T[][] mapArray;
    private Table<Integer, Integer, T> mapTable;
    private HashMap<T, Properties> mapProperties;
    public int width;
    public int height;
    private boolean enableFullSearch = true;
    private GridTravel.GridTravelMethod defaultTravelMethod;

    public GridMap(Class<T> clazz) {
        this(clazz, DEFAUL_MAP_SIZE_WIDTH, DEFAUL_MAP_SIZE_HEIGHT, MapDensity.Full);
    }

    public GridMap(Class<T> clazz, int width, int height) {
        this(clazz, width, height, MapDensity.Full);
    }

    public GridMap(Class<T> clazz, int width, int height, MapDensity density) {
        this.width = width;
        this.height = height;

        this.mapDensity = density;
        if (density == MapDensity.Full) {
            this.mapArray = (T[][]) Array.newInstance(clazz, width, height);
        } else if (density == MapDensity.Dense) {
            this.mapTable = ArrayTable.create(createRangeSet(width), createRangeSet(height));
        } else {
            this.mapTable = HashBasedTable.create(width, height);
        }

    }

    public List<Map2D> getChildren() {
        return null;
    }

    public boolean isInside(Pair position) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public T getAt(Pair position) {
        return getAt(position.getFirst(), position.getFirst());
    }

    public Set<Pair> coveredPosition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int size() {
        return width * height;
    }

    public boolean isEmpty() {
        if (mapDensity.equals(MapDensity.Full)) {
            return false;
        } else {
            return mapTable.isEmpty();
        }
    }

    public boolean containsKey(Object key) {
        if (key != null && key instanceof Pair) {
            return isInside((Pair) key);
        } else {
            return false;
        }
    }

    public boolean containsValue(Object value) {
        if (mapDensity == MapDensity.Full) {
            if (!enableFullSearch) {
                throw new UnsupportedOperationException("Not supported yet.");
            } else {
                /*
                 //FIXME: Better search. Foodfill and random search
                 travelColRow(new GridTravel<Boolean>() {
                 @Override
                 public Boolean visit(int x, int y) {
                 throw new UnsupportedOperationException("Not supported yet.");
                 }
                 });
                 */
                return false;
            }
        } else {
            return mapTable.containsValue(value);
        }
    }

    public T get(Object key) {
        if (key != null && key instanceof Pair) {
            return getAt((Pair) key);
        } else {
            return null;
        }
    }

    public T put(Pair key, T value) {
        return putAt(((Integer) key.getFirst()).intValue(), ((Integer) key.getSecond()).intValue(), value);
    }

    public T remove(Object key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void putAll(Map<? extends Pair, ? extends T> m) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void clear() {
        if (mapDensity == MapDensity.Full) {
            Arrays.fill(mapArray, null);
        } else {
            this.mapTable.clear();
        }
    }

    public Set<Pair> keySet() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection<T> values() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Set<Entry<Pair, T>> entrySet() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public T putAt(Pair position, T tile) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Integer> createRangeSet(int max) {
        return ContiguousSet.create(Range.closed(0, max), DiscreteDomain.integers()).asList();
    }

    public void buildMap() {
        // init and build map array
    }
    //GEOGRAPHIC 

    public T adjacentTo(T cell, GridTravel.GridRelativePostion position) {
        return null;
    }

    public T adjacentTo(T cell, GridTravel.GridDirection direction) {
        return null;
    }

    public T nextTo(T cell) {
        return null;
    }

    public T leftTo(T cell) {
        return null;
    }

    public T rightTo(T cell) {
        return null;
    }

    public T topTo(T cell) {
        return null;
    }

    public T bottomTo(T cell) {
        return null;
    }
    //TRAVEL

    public void travelRow(int row) {
    }

    public void travelCol(int col) {
    }

    public void travelRowCol(GridTravel action) {
        //FIXME: Better search. Foodfill and random search
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                travelResult = action.visit(x, y, getAt(x, y));
            }
        }
    }

    public void travelColRow(GridTravel action) {
        //FIXME: Better search. Foodfill and random search
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                action.visit(x, y, getAt(x, y));
            }
        }
    }

    public void travelFloodFill(int x, int y) {
    }
    //TRANSFORM

    // SETTER & GETTER
    public T[][] getRawView() {
        //return mapArray;
        return mapArray;
    }

    public Table<Integer, Integer, T> getViewAsTable() {
        Table<Integer, Integer, T> table = HashBasedTable.create();
        return table;
    }

    public T putAt(int x, int y, T tile) {
        if (this.mapDensity == MapDensity.Full) {
            mapArray[x][y] = tile;
            return tile;
        } else {
            return mapTable.put(x, y, tile);
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public T getAt(int x, int y) {
        if (this.mapDensity == MapDensity.Full) {
            return mapArray[x][y];
        } else {
            return mapTable.get(x, y);
        }
    }

    public T getAt(Object x, Object y) {
        if (this.mapDensity == MapDensity.Full) {
            return mapArray[((Integer) x).intValue()][((Integer) y).intValue()];
        } else {
            return mapTable.get(x, y);
        }
    }

    public boolean isOutOfBorder(int x, int y) {
        return true;
    }

    public int totalIndexedTile() {
        return 0;
    }

    public HashMap<T, Properties> getMapProperties() {
        return mapProperties;
    }

    public Properties getMapProperty(T cell) {
        return mapProperties.get(cell);
    }

    // Some bitmap functions
    public void paint(Map2D brush) {
    }

    // Static integer
    public static Pair<Integer, Integer> getPair(int x, int y) {
        return new Pair<Integer, Integer>(x, y);
    }

    public static Pair<Float, Float> getPair(float x, float y) {
        return new Pair<Float, Float>(x, y);
    }

    public static Pair<Double, Double> getPair(double x, double y) {
        return new Pair<Double, Double>(x, y);
    }
}
