package model.entity;

import model.map.Map;
import model.map.PersistableLocatable;

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

    public Mule(MuleType pType) {
        this.type = pType;
    }

    /**
     * gets type of mule
     * @return type of mule
     */
    public MuleType getType() {
        return type;
    }

    public final void setType(MuleType pType) {
        this.type = pType;
    }

    @Override
    public final Map.Location getLocation() {
        return location;
    }

    @Override
    public final void setLocation(Map.Location pLocation) {
        this.location = pLocation;
    }

    @Override
    /**
     * We can keep the definition of hashcode() from super, as our
     * implementation here would be equivalent
     */
    public final boolean equals(Object obj) {
        if ((obj == null) || !(obj instanceof Mule)) {
            return false;
        }

        Mule other = (Mule) obj;

        return other.getId() == getId();
    }
}
