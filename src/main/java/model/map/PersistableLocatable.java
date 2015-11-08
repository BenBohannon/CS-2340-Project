package model.map;


import javax.persistence.*;

/**
 * Created by brian on 10/29/15.
 */
@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public abstract class PersistableLocatable implements Locatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public final void setId(int pId) {
        id = pId;
    }

    public final int getId() {
        return id;
    }

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public final int hashCode() {
        return getId();
    }
}
