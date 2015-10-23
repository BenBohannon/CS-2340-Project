package model.service;

import model.entity.Player;

/**
 * Created by brian on 9/21/15.
 */
@FunctionalInterface
public interface TurnEndListener {
    public void onTurnEnd(Player player);
}
