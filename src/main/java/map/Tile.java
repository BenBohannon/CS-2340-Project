package map;

import javafx.scene.image.Image;

/**
 * Created by Ben 9/14/15.
 * Holds the data of each Tile on the Map.
 */
public class Tile implements Locatable {

    TileType type = TileType.PLAIN;
    Map.Location loc;

    public Tile(TileType type) {
        this.type = type;
    }

    /**
     * @see Locatable
     */
    public Map.Location getLocation() {
        return loc;
    }

    /**
     * @see Locatable
     */
    public void setLocation(Map.Location location) {
        loc = location;
    }

    /**
     * Returns the natively sized image for this Tile's TileType.
     * @return Image of this tile.
     */
    public Image getImage() {
        Image img;
        switch (type) {
            case PLAIN:
                img = new Image("/tiles/plain.png");
                break;
            case MOUNTAIN:
                img = new Image("/tiles/mountain.png");
                break;
            case RIVER:
                img = new Image("/tiles/river.png");
                break;
            case TOWN:
                img = new Image("/tiles/town.png");
                break;
            default:
                throw new NullPointerException("Type was null!");
        }

        return img;
    }

    /**
     * Returns the image for this Tile's TileType with the specified width and height.
     * @param width width of the image in pixels
     * @param height height of the image in pixels
     * @return Image of this tile.
     */
    public Image getImage(int width, int height) {
        Image img;
        switch (type) {
            case PLAIN:
                img = new Image("/tiles/plain.png", width, height, false, false);
                break;
            case MOUNTAIN:
                img = new Image("/tiles/mountain.png",  width, height, false, false);
                break;
            case RIVER:
                img = new Image("/tiles/river.png",  width, height, false, false);
                break;
            case TOWN:
                img = new Image("/tiles/town.png",  width, height, false, false);
                break;
            default:
                throw new NullPointerException("Type was null!");
        }

        return img;
    }


}

enum TileType {
    RIVER,
    MOUNTAIN,
    PLAIN,
    TOWN
}