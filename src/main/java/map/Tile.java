package map;

/**
 * Created by Ben 9/14/15.
 * Holds the data of each Tile on the Map.
 */
public class Tile implements Locatable {

    TileType type;
    Map.Location loc;

    public Tile(TileType type) {
        this.type = type;
    }

    public Map.Location getLocation() {
        return loc;
    }

    public void setLocation(Map.Location location) {
        loc = location;
    }

}

enum TileType {
    River,
    Mountain,
    Plain,
    Town
}