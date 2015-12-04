package data.concretesources;

import model.entity.GameSaveMeta;

import javax.persistence.*;
import java.util.List;

/**
 * Created by brian on 10/29/15.
 */
@Entity
public class TurnRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private
    int id;

    @ManyToOne(fetch = FetchType.EAGER)
    private GameSaveMeta gameSaveMeta;

    private int round;
    @ElementCollection(fetch = FetchType.EAGER)
    private
    List<Integer> finishedPlayerIds;

    public final int getId() {
        return id;
    }

    public final void setId(int pId) {
        this.id = pId;
    }

    public final int getRound() {
        return round;
    }

    public final void setRound(int pRound) {
        this.round = pRound;
    }

    public final List<Integer> getFinishedPlayerIds() {
        return finishedPlayerIds;
    }

    public final void setFinishedPlayerIds(List<Integer> pFinishedPlayerIds) {
        this.finishedPlayerIds = pFinishedPlayerIds;
    }

    public GameSaveMeta getGameSaveMeta() {
        return gameSaveMeta;
    }

    public void setGameSaveMeta(GameSaveMeta gameSaveMeta) {
        this.gameSaveMeta = gameSaveMeta;
    }
}
