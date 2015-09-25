package data;

import map.TileType;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Random;

/**
 * Created by brian on 9/24/15.
 */
public class MapInfoHolder {
    public TileType getTileType(int row, int col) {
        Random rand = new Random();
        if (col == 4) {
            if (row == 2) {
                //Make a town
                return TileType.TOWN;

            } else {
                //Make a river
                return TileType.RIVER;
            }
        } else {
            if (rand.nextInt(6) == 0) {
                return TileType.MOUNTAIN;
            } else {
                return TileType.PLAIN;
            }
        }
    }
}
