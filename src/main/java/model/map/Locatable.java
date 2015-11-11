package model.map;


/**
 * Created by brian on 9/13/15.
 *
 * Locatables should define a private {@link model.map.Map.Location}, but should
 * never explicitly assign it; Object location should be managed through
 * the methods of the {@link Map} or {@link model.map.Map.Location} instances.
 */
public interface Locatable {
    /**
     * Should return the privately defined {@link model.map.Map.Location} instance.
     * Used internally by the {@link Map} class to retreive
     * info about the a client Object.
     * @return the location
     */
    Map.Location getLocation();

    /**
     * Should assign the privately defined {@link model.map.Map.Location} instance.
     * Used internally by the Map class to move Locatables.
     * Calling this method anywhere else will cause an error.
     */
    void setLocation(Map.Location location);
}
