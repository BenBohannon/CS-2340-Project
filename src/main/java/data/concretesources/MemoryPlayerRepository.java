package data.concretesources;

import data.abstractsources.Repository;
import model.entity.Player;

import java.util.ArrayList;

import java.util.List;

/**
 * Created by brian on 9/24/15.
 */
public class MemoryPlayerRepository implements Repository<Player> {

    private static int nextPlayerId = 0;

    private ArrayList<Player> players;

    public MemoryPlayerRepository() {
        players = new ArrayList<>();
    }

    @Override
    public List<Player> getAll() {
        return players;
    }

    @Override
    public Player get(Object id) {
        if (!(id instanceof Integer)) {
            throw new IllegalArgumentException("Player id must be an Integer");
        }
        int playerId = (int) id;
        return players.stream()
                .filter(player -> player.getId() == playerId)
                .findAny()
                .get();
    }

    @Override
    public Player save(Player entity) {
        if (entity.getId() == -1) {
            entity.setId(nextPlayerId++);
        }
        if (players.contains(entity)) {
            //no action needed in memory//
        } else {
            players.add(entity);
        }
        return entity;
    }

    @Override
    public Player delete(Object id) {
        if (!(id instanceof Integer)) {
            throw new IllegalArgumentException("Player id must be an Integer");
        }
        int playerId = (int) id;
        Player p = players.stream()
                .filter(player -> player.getId() == playerId)
                .findAny()
                .get();
        players.remove(p);
        return p;
    }

    @Override
    public int size() {
        int size = players.size();
        return size;
    }
}
