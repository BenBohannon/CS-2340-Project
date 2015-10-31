package model.map;

import data.abstractsources.LocationDatasource;

import javax.persistence.*;

/**
 * Created by brian on 10/29/15.
 */
@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public abstract class PersistableLocatable implements Locatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || !(obj instanceof PersistableLocatable)) {
            return false;
        }

        PersistableLocatable other = (PersistableLocatable) obj;

        return id == other.getId();
    }

    @Override
    public int hashCode() {
        return id;
    }
}
