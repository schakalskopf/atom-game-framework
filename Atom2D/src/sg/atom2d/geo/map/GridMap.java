/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom2d.geo.map;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.jme3.math.Vector2f;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Properties;

/**
 * A Generic Grid map with each Cell is an object with utils.
 *
 * It backed with an array of Cell object or a Guava Table if it's spare. So
 * there are two available constructors for those purpose.
 *
 * @author cuong.nguyenmanh2
 */
public class GridMap<T> {

    public static enum MapRelativePostion {

        Left, Right, Top, Bottom
    }

    public static enum MapDirection {

        North, East, South, West, NorthWest, NorthEast, SouthWest, SouthEast
    }

    public static enum MapDensity {

        Full, Dent, Spare
    };
    public static int DEFAUL_MAP_SIZE = 100;
    public static int DEFAUL_MAP_SIZE_WIDTH = 100;
    public static int DEFAUL_MAP_SIZE_HEIGHT = 100;
    public MapDensity mapDensity;
    private T[][] mapArray;
    private Table<Integer, Integer, T> mapTable;
    private HashMap<T, Properties> mapProperties;
    public int width;
    public int height;

    public GridMap() {
        this(DEFAUL_MAP_SIZE_WIDTH, DEFAUL_MAP_SIZE_HEIGHT);
    }

    @SuppressWarnings("empty-statement")
    public GridMap(Class<T> clazz, int width, int height) {
        this.width = width;
        this.height = height;
        this.mapDensity = MapDensity.Full;
        this.mapArray = (T[][]) Array.newInstance(clazz, width, height);
        this.mapTable = null;
    }

    public GridMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.mapDensity = MapDensity.Spare;
        this.mapArray = null;
        this.mapTable = HashBasedTable.create();
    }

    public void buildMap() {
        // init and build map array
    }
    //GEOGRAPHIC & TRAVEL

    public MapRelativePostion from(MapDirection directionEx) {
        return MapRelativePostion.Bottom;
    }

    public T adjacentTo(T cell, MapRelativePostion position) {
        return null;
    }

    public T adjacentTo(T cell, MapDirection direction) {
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

    public void travelRow(int row) {
    }

    public void travelCol(int col) {
    }

    public void travelRowCol() {
    }

    public void travelColRow() {
    }

    public void travelFloodFill(int x, int y) {
    }
    //TRANSFORM

    // SETTER & GETTER
    public T[][] getView() {
        return mapArray;
    }

    public Table<Integer, Integer, T> getViewAsTable() {
        Table<Integer, Integer, T> table = HashBasedTable.create();
        return table;
    }

    public void setTileAt(int x, int y, T tile) {
        mapArray[x][y] = tile;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public T getTileAt(Vector2f pos) {
        return null;
    }

    public T getTileAt(int x, int y) {
        return mapArray[x][y];
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
}
