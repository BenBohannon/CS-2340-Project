package model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by brian on 11/29/15.
 */

@Entity
public class GameSaveMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Date gameStarted;
    private Date lastPlayed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(Date gameStarted) {
        this.gameStarted = gameStarted;
    }

    public Date getLastPlayed() {
        return lastPlayed;
    }

    public void setLastPlayed(Date lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GameSaveMeta) {
            GameSaveMeta other = (GameSaveMeta) obj;

            return other.getId() == getId();
        }

        return false;
    }
}
