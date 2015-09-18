package data;

import model.Player;

/**
 * Created by brian on 9/17/15.
 * Temporary way to deal with turn management issue
 */
public interface TurnManager {
    public Player getCurrentPlayer();
    public int getRoundNumber();
    public int getTimeLeftInTurn();
}
