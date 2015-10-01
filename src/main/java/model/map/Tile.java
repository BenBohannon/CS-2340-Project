package model.map;

import javafx.scene.image.Image;
import model.entity.Player;

import java.util.Random;

/**
 * Created by Ben 9/14/15.
 * Holds the data of each Tile on the Map.
 */
public class Tile implements Locatable {

    TileType type = TileType.PLAIN;
    Map.Location loc;
    int numOfMountains = 0;

    private Player owner;

    public Tile(TileType type) {
        this.type = type;

        if (type == TileType.MOUNTAIN) {
            Random rand = new Random();
            numOfMountains = rand.nextInt(3) + 1;
        }
    }

    /**
     * Sets the owner of the tile
     * @param owner The new owner of the tile
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Returns the owner if the tile is owned, and null if it is not
     * @return The owner of the tile
     */
    public Player ownedBy() {
        return owner;
    }

    public TileType getTileType() {
        return type;
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
                if (numOfMountains == 1) {
                    img = new Image("/tiles/1mountain.png");
                } else if (numOfMountains == 2) {
                    img = new Image("/tiles/2mountains.png");
                } else {
                    img = new Image("/tiles/3mountains.png");
                }
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
                if (numOfMountains == 1) {
                    img = new Image("/tiles/1mountain.png", width, height, false, false);
                } else if (numOfMountains == 2) {
                    img = new Image("/tiles/2mountains.png", width, height, false, false);
                } else {
                    img = new Image("/tiles/3mountains.png", width, height, false, false);
                }
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