package model.service;

import com.google.inject.Inject;
import data.GameInfo;
import data.Repository;
import data.StoreInfoHolder;
import data.TurnEndListener;
import model.entity.Player;

import java.util.*;
import java.util.stream.Stream;

/**
 * Service that will keep track of turns and rounds.
 *
 * Guice should be configured to inject this as a singleton for now, but this
 * can be changed after persistence is added.
 *
 * To use:
 * Not surprisingly, {@link DefaultTurnService#beginRound()} should be called at the
 * beginning of each round. This sets increments the round, as well as assures that
 * the TurnService is in the correct state.
 *
 * {@link DefaultTurnService#beginTurn()} should be called at the beginning of each
 * turn. This will calculate which {@link Player} should have the next turn, and begin that
 * turn by starting a countdown timer that lasts the length of the turn.
 *
 * While the Player's turn is ongoing, the methods {@link DefaultTurnService#getTimeLeftInTurn()} and
 * {@link DefaultTurnService#getCurrentPlayer()} can be consumed.
 *
 * Additionally, while the turn is in progress, any number of {@link TurnEndListener}s can be added.
 * When the turn ends, the DefaultTurnService will call {@link TurnEndListener#onTurnEnd(Player)} on
 * each registered TurnEndListener.
 * NOTE: {@link presenters.Presenter}s should unregister themselves using {@link DefaultTurnService#removeTurnEndListener(TurnEndListener)}
 * when their views are no longer on screen, as this will impede garbage collection and likely produce unexpected results.
 * Thus, each Presenter that consumes the DefaultTurnService should check it for an ongoing turn and register a listener at the
 * beginning of its lifecycle.
 *
 * for other functionality:
 * @see DefaultTurnService#endTurn()
 * @see DefaultTurnService#isAllTurnsOver()
 * @see DefaultTurnService#flushRound(int)
 *
 */
public class DefaultTurnService {

    private static final String TURN_IN_PROGRESS = "A turn is currently in progress.";
    private static final String TURN_NOT_IN_PROGRESS = "No turn is currently in progress";

    private volatile int roundNumber;
    private volatile Collection<TurnEndListener> turnEndListeners;
    private volatile Player currentPlayer;

    private volatile boolean turnInProgress;
    private volatile long turnStartTime;
    private volatile long turnDuration;

    private volatile Timer timer;

    private Repository<Player> playerRepository;
    private StoreInfoHolder storeInfo;

    //players are added to this list after their turns are complete//
    private volatile Collection<Integer> finishedPlayerIds;

    @Inject
    public DefaultTurnService(Repository<Player> playerRepository, StoreInfoHolder storeInfo) {
        this.playerRepository = playerRepository;
        this.storeInfo = storeInfo;
        turnEndListeners = new LinkedList<>();
        finishedPlayerIds = new LinkedList<>();
    }

    /**
     * Starts the appropriate {@link Player}'s turn timer
     * @return the Player whose turn was started.
     */
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
        //turnDuration = (int) (currentPlayer.getPTU(GameInfo.BTU(4)) + currentPlayer.getPTU(GameInfo.BTU(91)) * foodRatio);
        turnDuration = 10000L; //TEMPORARY CODE. EVERY PLAYER GETS 10 seconds.

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

    /**
     * A turn is considered in progress if the timer continues to count down.
     * @return whether the turn is in progress
     */
    public boolean isTurnInProgress() {
        return turnInProgress;
    }

    /**
     * The current Player is the Player whose turn is currently in progress
     * @return the Player whose turn is in progress, or null if none are in progress
     */
    public Player getCurrentPlayer() {
        if (turnInProgress) {
            return currentPlayer;
        }
        return null;
    }

    /**
     * If no rounds have begun, the round is zero. The first round is one, stretching
     * to the maximum number of rounds, defined in {@link GameInfo#getMaxRounds()}
     * @return the round number
     */
    public int getRoundNumber() {
        return roundNumber;
    }

    /**
     * @see DefaultTurnService#isTurnInProgress()
     * @return true if all Players' turns have ended, false otherwise
     */
    public boolean isAllTurnsOver() {
        return finishedPlayerIds.size() == playerRepository.getAll().size();
    }

    /**
     * Sets up TurnService state for the next round.
     * Additionally, removes all {@link TurnEndListener}s.
     * Does not begin turn.
     *
     * @see #getRoundNumber()
     * @return the round number
     */
    public int beginRound() {
        if (timer != null
                || turnInProgress
                || !((finishedPlayerIds.size() == 0) || (finishedPlayerIds.size() == playerRepository.getAll().size()))) {
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

    /**
     * Adds a listener that will be invoked when the currently ongoing turn ends.
     *
     * All {@link TurnEndListener}s are removed after the end of the turn
     *
     * TurnEndListeners will be invoked before the {@link #endTurn()} method returns.
     *
     * @param listener the listener that will be notified of the turn end
     * @throws IllegalStateException if no turn is ongoing
     */
    public void addTurnEndListener(TurnEndListener listener) {
        if (!turnInProgress) {
            throw new IllegalStateException(TURN_NOT_IN_PROGRESS);
        }
        turnEndListeners.add(listener);
    }

    /**
     * Removes a {@link TurnEndListener} from being invoked when the turn ends.
     * @param listener listener that will no longer be notified of the turn end
     */
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


    /**
     * Prematurely ends a player's turn, calling all listeners
     * @return the player whose turn it was.
     */
    public Player endTurn() {
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
        Player player = currentPlayer;
        currentPlayer = null;
        turnInProgress = false;
        finishedPlayerIds.add(player.getId());
        turnStartTime = -1;
        turnDuration = -1;

        //push out event//
        for (TurnEndListener listener : turnEndListeners) {
            listener.onTurnEnd(player);
        }
        turnEndListeners = null;
        return player;
    }
}
