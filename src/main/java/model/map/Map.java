package model.map;

import com.google.inject.Inject;
import data.LocationDatasource;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by brian on 9/12/15.
 * Manages the Location grid.
 * All changes of location for any object on the
 * main model.map are managed and tracked by the Map
 * class. It uses a grid of {@link Map.Location} to
 * store and present this information.
 * See {@link Map.Location} for more info.
 */
public class Map {

    private Location[][] locationGrid;

    private LocationDatasource datasource;

    @Inject
    public Map(LocationDatasource lds) {
        datasource = lds;
        int rows = 5;
        int cols = 9;

        if (rows < 1 || cols < 1) {
            throw new IllegalArgumentException(
                    "model.map must have positive integer rows and columns");
        }

        locationGrid = new Location[cols][rows];

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                locationGrid[i][j] = new Location(i, j, this);
                Collection<Locatable> occupants = datasource.get(i, j);
                for (Locatable e : occupants) {
                    add(e, i, j);
                }
            }
        }
    }

    /**
     * Retrieves all {@link Locatable}s in the
     * location specified, of the type specified.
     * @param row row of location in question
     * @param col column of location in question
     * @param type filter results to only this type
     * @param <T> type used to filter must extend {@link Locatable}
     * @return an array with the type of the Objects requested
     */
    public <T extends Locatable> T[] getOccupants(int row,
                                                  int col, Class<T> type) {
        return locationGrid[row][col].getOccupants(type);
    }

    /**
     * Returns all {@link Locatable}s in the {@link Location} specified.
     * @param row row of location in question
     * @param col column of location in question
     * @return an array of type {@link Locatable}
     *          containing all Objects stored at the specified location
     */
    public Locatable[] getOccupants(int row, int col) {
        return getOccupants(row, col, Locatable.class);
    }

    /**
     * Adds a previously unplaced {@link Locatable} to the model.map.
     * If the Object already has a {@link Location},
     * it should use the {@link Map#move(Locatable, int, int)}
     * or {@link Location#moveHere(Locatable)} methods.
     * @param locatable Locatable to be added
     * @param row row to which it should be added
     * @param col column to which it should be added
     */
    public void add(Locatable locatable, int row, int col) {
        if (locatable.getLocation() != null) {
            throw new IllegalArgumentException(
                    "This Object already has a location assigned to it."
                            + "In this case, the move()"
                            + "method should be used.");
        }

        if (!isInBounds(row, col)) {
            throw new IllegalArgumentException(
                    "row or col value is out of bounds.");
        }

        Location newLocation = locationGrid[row][col];
        if (!newLocation.addOccupant(locatable)) {
            throw new RuntimeException("internal issue");
        }

        locatable.setLocation(newLocation);
    }

    /**
     * Removes a {@link Locatable} from the model.map grid.
     * @param locatable locatable to be removed from the model.map grid
     */
    public void remove(Locatable locatable) {
        if (!locatable.getLocation().removeOccupant(locatable)) {
            throw new RuntimeException("internal issue");
        }

        locatable.setLocation(null);
    }

    /**
     * Moves a {@link Locatable} from its location to a new one.
     * Convenience method for
     * {@link Map#remove(Locatable)} and
     * then {@link Map#add(Locatable, int, int)},
     * plus some checking to make sure this operation is logical.
     * @param locatable locatable to be moved.
     * @param row row to which the object should be moved.
     * @param col column to which the object should be moved.
     */
    public void move(Locatable locatable, int row, int col) {
        if (locatable.getLocation() == null) {
            throw new IllegalArgumentException(
                    "Locatable was not on the model.map; cannot move.");
        }

        remove(locatable);

        add(locatable, row, col);
    }

    /**
     * get num of rows
     * @return num of rows
     */
    public int getRows() {
        return locationGrid.length;
    }

    /**
     * get num of columns
     * @return num of columns
     */
    public int getCols() {
        return locationGrid[0].length;
    }

    /**
     * check if given row and col is in bounds
     * @param row specified row
     * @param col specified column
     * @return if in bounds
     */
    private boolean isInBounds(int row, int col) {
        return row >= 0  && row < getRows()
                && col >= 0 && col < getCols();
    }

//==============================================================================
    /**
     * The Location class encapsulates location information for a
     * {@link Locatable} on the model.map, and is used by the
     * {@link Map} as an api for accessing this info. Each
     * {@link Locatable} stores a reference to it's Location when
     * it is being presented to the user on the game model.map.
     * The Location Object offers this {@link Locatable} the
     * answer to the question "Where am I?". However, because
     * each {@link Locatable} can only manage its position through
     * the {@link Map} grid, client code can also easily answer
     * the question "What is here?". In order to prevent the Location
     * class from being used without the {@link Map}'s knowledge,
     * only the {@link Map} has the ability to instantiate
     * and manage Locations. However, because all of these
     * management endpoints are available from a Location instance,
     * the {@link Locatable} essentially retains the ability to
     * manage its own location.
     */
    public class Location {

        private int row;
        private int col;
        private Map map;

        private Collection<Locatable> occupants;

        /**
         * instantiates a location class object
         * @param row row of object
         * @param col col of object
         * @param map map on which object is present
         */
        private Location(int row, int col, Map map) {
            this.row = row;
            this.col = col;
            this.map = map;
            occupants = new LinkedList<>();
        }

        /**
         * Adds a previously unplaced {@link Locatable} to this location.
         * If the Object already has a {@link Location},
         * it should use the {@link Map#move(Locatable, int, int)}
         * or {@link Location#moveHere(Locatable)} methods.
         * @param locatable Locatable to be added
         */
        public void add(Locatable locatable) {
            map.add(locatable, row, col);
        }

        /**
         * Removes a {@link Locatable} from the model.map this location.
         * @param locatable locatable to be removed from the model.map grid
         */
        public void remove(Locatable locatable) {
            map.remove(locatable);
        }

        /**
         * Moves a {@link Locatable} from its location to a this one.
         * Convenience method for
         * {@link Location#remove(Locatable)}
         * and then {@link Location#add(Locatable)},
         * plus some checking to make sure this operation is logical.
         * @param locatable locatable to be moved.
         */
        public void moveHere(Locatable locatable) {
            map.move(locatable, row, col);
        }

        /**
         * Retrieves all {@link Locatable}s in the
         * location specified, of the type specified
         * @param type filter results to only this type
         * @param <T> type used to filter must extend {@link Locatable}
         * @return an array with the type of the Objects requested
         */
        public <T extends Locatable> T[] getOccupants(Class<T> type) {
            return occupants.stream()
                    .filter(type::isInstance)
                    .toArray(size -> (T[]) Array.newInstance(type, size));
        }

        /**
         * Returns all {@link Locatable}s
         * in the {@link Location} specified
         * @return an array of type {@link Locatable} containing
         *          all Objects stored at the specified location
         */
        public Locatable[] getOccupants() {
            return getOccupants(Locatable.class);
        }

        /**
         * get num of row
         * @return row number
         */
        public int getRow() {
            return row;
        }

        /**
         * get num of col
         * @return column number
         */
        public int getCol() {
            return col;
        }

        /**
         * remove what is a location
         * @param occupant locatable value
         * @return if removed
         */
        private boolean removeOccupant(Locatable occupant) {
            return occupants.remove(occupant);
        }

        /**
         * add something at a location
         * @param occupant locatable value
         * @return if added
         */
        private boolean addOccupant(Locatable occupant) {
            return occupants.add(occupant);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Location)) {
                return false;
            }

            Location other = (Location) obj;

            if (other.getRow() == getRow() && other.getCol() == getCol()) {
                return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return row * 2 + col;
        }
    }
}
