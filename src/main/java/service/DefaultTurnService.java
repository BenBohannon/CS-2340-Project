package service;

import com.google.inject.Inject;
import data.Repository;
import data.TurnEndListener;
import model.Player;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Timer;

/**
 * Created by brian on 9/21/15.
 */
public class DefaultTurnService {

    private int roundNumber;
    private Repository<Player> playerRepository;
    private Collection<TurnEndListener> turnEndListeners;
    private Collection<Integer> finishedPlayerIds;
    private boolean turnInProgress;
    private Timer timer;

    @Inject
    public DefaultTurnService(Repository<Player> playerRepository) {
        this.playerRepository = playerRepository;
        turnEndListeners = new LinkedList<>();
        finishedPlayerIds = new LinkedList<>();
    }

    public Player beginTurn() {
        if (turnInProgress) {
            throw new RuntimeException("Turn already in progress");
        }

        turnInProgress = true;
    }

    public boolean isTurnInProgress() {
        return turnInProgress;
    }

    public Player getCurrentPlayer() {
        return null;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public int getTimeLeftInTurn() {
        return 0;
    }

    public void setTurnEndListener(TurnEndListener listener) {
        turnEndListeners.add(listener);
    }

    public void removeTurnEndListener(TurnEndListener listener) {
        turnEndListeners.remove(listener);
    }
}
