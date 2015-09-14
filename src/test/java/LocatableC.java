import map.Locatable;
import map.Map;

/**
 * Created by brian on 9/14/15.
 * Test Class for Map
 */
class LocatableC extends ABC implements Locatable {
    private Map.Location location;

    @Override
    public Map.Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Map.Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LocatableC)) {
            return false;
        }

        return ((ABC) obj).getId() == this.getId();
    }
}
