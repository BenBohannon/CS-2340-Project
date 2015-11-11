package model.service;

import model.entity.Player;

/**
 * Created by brian on 9/21/15.
 */
@FunctionalInterface
public interface TurnEndListener {

    /**
     * Called by the Turn Service upon ending a turn.
     * @param player player whose turn just ended.
     */
    void onTurnEnd(Player player);
}
