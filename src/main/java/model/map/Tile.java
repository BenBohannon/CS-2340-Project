package model.map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.entity.Player;

import java.util.Random;

/**
 * Created by Ben 9/14/15.
 * Holds the data of each Tile on the Map.
 */
public class Tile implements Locatable {

    TileType type;
    Map.Location loc;

    private Player owner;

    public Tile(TileType type) {
        this.type = type;
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
        return new Image(type.getImagePath());
    }

    /**
     * Returns the image for this Tile's TileType with the specified width and height.
     * @param width width of the image in pixels
     * @param height height of the image in pixels
     * @return Image of this tile.
     */
    public Image getImage(int width, int height) {
        return new Image(type.getImagePath(), width, height, true, false);
    }
}