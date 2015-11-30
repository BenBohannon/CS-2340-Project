package model.map;


import model.entity.GameSaveMetaData;

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
    @ManyToOne(fetch = FetchType.EAGER)
    private GameSaveMetaData gameSaveMetaData;

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

    public GameSaveMetaData getGameSaveMetaData() {
        return gameSaveMetaData;
    }

    public void setGameSaveMetaData(GameSaveMetaData gameSaveMetaData) {
        this.gameSaveMetaData = gameSaveMetaData;
    }
}
