package model.map;

import javafx.scene.image.Image;
import model.entity.Player;

import javax.persistence.*;

/**
 * Created by Ben 9/14/15.
 * Holds the data of each Tile on the Map.
 */
@Entity
@PrimaryKeyJoinColumn(name="id")
public class Tile extends PersistableLocatable {

    @Enumerated
    TileType type;
    @Embedded
    Map.Location loc;

    @OneToOne(fetch = FetchType.EAGER)
    private Player owner;

    public Tile() {
        // required default constructor //
    }

    public Tile(TileType pType) {
        this.type = pType;
    }

    /**
     * Sets the owner of the tile
     * @param pOwner The new owner of the tile
     */
    public final void setOwner(Player pOwner) {
        owner = pOwner;
    }

    /**
     * Returns the owner if the tile is owned, and null if it is not
     * @return The owner of the tile
     */
    public final Player ownedBy() {
        return owner;
    }

    public final TileType getTileType() {
        return type;
    }

    /**
     * @see Locatable
     */
    public final Map.Location getLocation() {
        return loc;
    }

    /**
     * @see Locatable
     */
    public final void setLocation(Map.Location location) {
        loc = location;
    }

    /**
     * Returns the natively sized image for this Tile's TileType.
     * @return Image of this tile.
     */
    public final Image getImage() {
        return new Image(type.getImagePath());
    }

    /**
     * Returns the image for this Tile's TileType with the specified width and height.
     * @param width width of the image in pixels
     * @param height height of the image in pixels
     * @return Image of this tile.
     */
    public final Image getImage(int width, int height) {
        return new Image(type.getImagePath(), width, height, true, false);
    }

    @Override
    /**
     * We can keep the definition of hashcode() from super, as our
     * implementation here would be equivalent
     */
    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Tile)) {
            return false;
        }

        Tile other = (Tile) obj;

        return other.getId() == getId();
    }
}