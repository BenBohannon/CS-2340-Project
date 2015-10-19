package model.entity;

import model.map.Locatable;
import model.map.Map;

/**
 * Created by brian on 9/17/15.
 */
public class Mule implements Locatable {
    private Map.Location location;
    private MuleType type;

    public Mule(MuleType type) {
        this.type = type;
    }

    public MuleType getType() {
        return type;
    }

    @Override
    public Map.Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Map.Location location) {
        this.location = location;
    }
}
