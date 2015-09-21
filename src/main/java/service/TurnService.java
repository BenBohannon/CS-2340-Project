package service;

import data.TurnEndListener;
import model.Player;

/**
 * Created by brian on 9/17/15.
 * Temporary way to deal with turn management issue
 */
public interface TurnService {
    public Player getCurrentPlayer();
    public int getRoundNumber();
    public int getTimeLeftInTurn();
    public void setTurnEndListener(TurnEndListener listener);
    public void removeTurnEndListener(TurnEndListener listener);
}
