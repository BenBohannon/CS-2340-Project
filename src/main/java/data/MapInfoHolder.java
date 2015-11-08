package data;


import model.map.TileType;

/**
 * Created by brian on 9/24/15.
 * this class is a temporary implementation of a system to keep a consistent map. This
 * is a stopgap for preventing the map from randomizing each time the MapPresenter is
 * instantiated.
 */
public class MapInfoHolder {

    private static final TileType[][] TILE_GRID = {
            {TileType.MOUNTAIN_2, TileType.MOUNTAIN_2, TileType.MOUNTAIN_1, TileType.PLAIN, TileType.RIVER, TileType.PLAIN, TileType.PLAIN, TileType.PLAIN, TileType.PLAIN},
            {TileType.MOUNTAIN_3, TileType.MOUNTAIN_1, TileType.PLAIN, TileType.PLAIN, TileType.RIVER, TileType.PLAIN, TileType.PLAIN, TileType.MOUNTAIN_1, TileType.PLAIN},
            {TileType.MOUNTAIN_1, TileType.PLAIN, TileType.PLAIN, TileType.PLAIN, TileType.TOWN, TileType.PLAIN, TileType.MOUNTAIN_1, TileType.MOUNTAIN_1, TileType.PLAIN},
            {TileType.PLAIN, TileType.PLAIN, TileType.PLAIN, TileType.PLAIN, TileType.RIVER, TileType.MOUNTAIN_1, TileType.MOUNTAIN_3, TileType.MOUNTAIN_2, TileType.PLAIN},
            {TileType.PLAIN, TileType.MOUNTAIN_2, TileType.PLAIN, TileType.PLAIN, TileType.RIVER, TileType.MOUNTAIN_1, TileType.MOUNTAIN_2, TileType.MOUNTAIN_1, TileType.PLAIN},

    };

    public final TileType getTileType(int row, int col) {
        return TILE_GRID[row][col];
    }
}