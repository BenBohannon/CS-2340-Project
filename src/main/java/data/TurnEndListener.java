package data;

import model.Player;

/**
 * Created by brian on 9/21/15.
 */
@FunctionalInterface
public interface TurnEndListener {
    public void onTurnEnd(Player player);
}
