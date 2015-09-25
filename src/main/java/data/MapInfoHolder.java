package data;

import map.TileType;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by brian on 9/24/15.
 * this class is a temporary implementation of a system to keep a consistent map. This
 * is a stopgap for preventing the map from randomizing each time the MapPresenter is
 * instantiated.
 */
public class MapInfoHolder {

    private static TileType[][] TILE_GRID = {
            {TileType.MOUNTAIN, TileType.MOUNTAIN, TileType.MOUNTAIN, TileType.PLAIN, TileType.RIVER, TileType.PLAIN, TileType.PLAIN, TileType.PLAIN, TileType.PLAIN},
            {TileType.MOUNTAIN, TileType.MOUNTAIN, TileType.PLAIN, TileType.PLAIN, TileType.RIVER, TileType.PLAIN, TileType.PLAIN, TileType.MOUNTAIN, TileType.PLAIN},
            {TileType.MOUNTAIN, TileType.PLAIN, TileType.PLAIN, TileType.PLAIN, TileType.TOWN, TileType.PLAIN, TileType.MOUNTAIN, TileType.MOUNTAIN, TileType.PLAIN},
            {TileType.PLAIN, TileType.PLAIN, TileType.PLAIN, TileType.PLAIN, TileType.RIVER, TileType.MOUNTAIN, TileType.MOUNTAIN, TileType.MOUNTAIN, TileType.PLAIN},
            {TileType.PLAIN, TileType.MOUNTAIN, TileType.PLAIN, TileType.PLAIN, TileType.RIVER, TileType.MOUNTAIN, TileType.MOUNTAIN, TileType.MOUNTAIN, TileType.PLAIN},

    };

    public TileType getTileType(int row, int col) {
        return TILE_GRID[row][col];
    }
}