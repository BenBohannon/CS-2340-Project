import model.map.Tile;
import java.util.ArrayList;

/**
 * Created by connor on 9/20/15.
 */

/**
 * Skeleton class for player, which just keeps track of properties
 * owned by the player
 */
public class Player {
    private ArrayList<Tile> ownedProperties;

    /**
     * Constructs player that owns the lands passed in
     * @param ownedProperties The properties the player owns
     */
    public Player(ArrayList<Tile> ownedProperties) {
        this.ownedProperties = ownedProperties;
    }

    /**
     * Gets and returns the properties that the player owns
     * @return the properties the player owns
     */
    public ArrayList<Tile> getOwnedProperties() {
        return ownedProperties;
    }

    /**
     * Adds the property passed in to the list of owned properties
     * @param property The property being added to the list
     */
    public void addProperty(Tile property) {
        ownedProperties.add(property);
    }

    /**
     * Removes the property from the list of owned properties
     * @param property The property to be removed
     */
    public void removeProperty(Tile property) {
        ownedProperties.remove(property);
    }

    /**
     * Checks if the property passed in is owned by the player
     * @param property The property being checked to see if the player owns it
     * @return whether or not the player owns the given property
     */
    public boolean ownsProperty(Tile property) {
        return ownedProperties.contains(property);
    }

}
