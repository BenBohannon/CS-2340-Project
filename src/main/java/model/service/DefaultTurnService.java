package model.service;

import com.google.inject.Inject;
import data.GameInfo;
import data.Repository;
import data.StoreInfoHolder;
import data.TurnEndListener;
import model.Entity.Player;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by brian on 9/21/15.
 */
public class DefaultTurnService {

    private static final String TURN_IN_PROGRESS = "A turn is currently in progress.";
    private static final String TURN_NOT_IN_PROGRESS = "No turn is currently in progress";

    private int roundNumber;
    private Collection<TurnEndListener> turnEndListeners;
    private Player currentPlayer;

    private boolean turnInProgress;
    private long turnStartTime;
    private long turnDuration;

    private Timer timer;

    private Repository<Player> playerRepository;
    private StoreInfoHolder storeInfo;

    //players are added to this list after their turns are complete//
    private Collection<Integer> finishedPlayerIds;

    @Inject
    public DefaultTurnService(Repository<Player> playerRepository, StoreInfoHolder storeInfo) {
        this.playerRepository = playerRepository;
        this.storeInfo = storeInfo;
        turnEndListeners = new LinkedList<>();
        finishedPlayerIds = new LinkedList<>();
    }

    public Player beginTurn() {
        if (turnInProgress) {
            throw new RuntimeException(TURN_IN_PROGRESS);
        }
        if (roundNumber > GameInfo.getMaxRounds()) {
            throw new RuntimeException("Max rounds exceeded. Game should be over");
        }

        Stream<Player> stream = playerRepository.getAll().stream()
                .filter(player -> !(finishedPlayerIds.contains(player.getId())));
        if (storeInfo.getMuleCount() > 7) {
            //next player is highest score if mules remaining > 7//
            currentPlayer = stream
                    .max((p1, p2) -> p1.getScore() - p2.getScore())
                    .get();
        } else {
            //next player is lowest score if mules remaining <= 7//
            currentPlayer = stream
                    .min((p1, p2) -> p1.getScore() - p2.getScore())
                    .get();
        }


        //turn time in millis//
        float foodRatio = (float) currentPlayer.getFood() / GameInfo.getFoodRequirement(roundNumber);
        turnDuration = (int) (currentPlayer.getPTU(GameInfo.BTU(4)) + currentPlayer.getPTU(GameInfo.BTU(91)) * foodRatio);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                endTurn();
            }
        }, turnDuration);

        turnStartTime = new Date().getTime();
        turnEndListeners = new LinkedList<>();
        turnInProgress = true;

        return currentPlayer;
    }

    public boolean isTurnInProgress() {
        return turnInProgress;
    }

    public Player getCurrentPlayer() {
        if (turnInProgress) {
            return currentPlayer;
        }
        return null;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    /**
     * Sets up TurnService state for the next round.
     * Additionally, removes all {@link TurnEndListener}s.
     * Does not begin turn.
     * @return
     */
    public int beginRound() {
        if (timer != null
                || turnInProgress
                || !((finishedPlayerIds.size() == 0) && (finishedPlayerIds.size() == playerRepository.getAll().size()))) {
            throw new IllegalStateException(TURN_IN_PROGRESS);
        }
        flushRound(roundNumber + 1);
        return roundNumber;
    }

    /**
     * returns the amount of time remaining in the current turn as an int,
     * which should be an acceptable unless the turn length is over a month.
     * @return remaining time left in the current turn in millis
     */
    public int getTimeLeftInTurn() {
        if (!turnInProgress) {
            throw new IllegalStateException(TURN_NOT_IN_PROGRESS);
        }
        return (int) (turnStartTime + turnDuration - new Date().getTime());
    }

    public void addTurnEndListener(TurnEndListener listener) {
        if (!turnInProgress) {
            throw new IllegalStateException(TURN_NOT_IN_PROGRESS);
        }
        turnEndListeners.add(listener);
    }

    public void removeTurnEndListener(TurnEndListener listener) {
        if (!turnInProgress) {
            throw new IllegalStateException(TURN_NOT_IN_PROGRESS);
        }
        turnEndListeners.remove(listener);
    }

    /**
     * Cancels this round and sets the turnmanager's state as if the round had just begun.
     * This includes removing all TurnEndListeners.
     * Additionally, sets the round number.
     * @param roundNumber the new round number
     */
    public void flushRound(int roundNumber) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        finishedPlayerIds = new LinkedList<>();
        currentPlayer = null;
        turnInProgress = false;
        turnEndListeners = new LinkedList<>();
        turnStartTime = -1;
        turnDuration = -1;
        this.roundNumber = roundNumber;
    }

    private void endTurn() {
        timer = null;
        Player player = currentPlayer;
        currentPlayer = null;
        finishedPlayerIds.add(player.getId());
        turnInProgress = false;
        turnStartTime = -1;
        turnDuration = -1;

        //push out event//
        for (TurnEndListener listener : turnEndListeners) {
            listener.onTurnEnd(player);
        }
        turnEndListeners = null;
    }
}
