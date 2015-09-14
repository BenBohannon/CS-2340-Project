package map;

/**
 * Created by Ben 9/14/15.
 * Holds the data of each Tile on the Map.
 */
public class Tile implements Locatable {

    TileType type;

    public Tile(TileType type) {
        this.type = type
    }

}

public enum TileType {
    River,
    Mountain,
    Plain,
    Town
}