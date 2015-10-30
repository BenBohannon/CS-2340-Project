package model.entity;

import model.map.Locatable;
import model.map.Map;
import model.map.PersistableLocatable;
import org.hibernate.annotations.Generated;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.*;

/**
 * Created by brian on 9/17/15.
 */

@Entity
@PrimaryKeyJoinColumn(name="id")
public class Mule extends PersistableLocatable {
    @Embedded
    private Map.Location location;
    @Enumerated
    private MuleType type;

    public Mule() {
        //Required default constructor for hibernate//
    }

    public Mule(MuleType type) {
        this.type = type;
    }

    public MuleType getType() {
        return type;
    }

    public void setType(MuleType type) {
        this.type = type;
    }

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
        if ((obj == null) || !(obj instanceof Mule)) {
            return false;
        }

        Mule other = (Mule) obj;

        return other.getId() == id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
