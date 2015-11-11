package model.entity;

import model.map.Locatable;
import model.map.Map;

/**
 * Created by brian on 9/17/15.
 */
public class Mule implements Locatable {
    private int id;
    private Map.Location location;
    private MuleType type;

    /**
     * initialises a mule
     * @param type type of mule
     */
    public Mule(MuleType type) {
        this.type = type;
    }

    /**
     * gets type of mule
     * @return type of mule
     */
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

    /**
     * gets id of mule
     * @return id number
     */
    public int getId() {
        return id;
    }

    /**
     * sets the id of a mule
     * @param id id number
     */
    public void setId(int id) {
        this.id = id;
    }
}
