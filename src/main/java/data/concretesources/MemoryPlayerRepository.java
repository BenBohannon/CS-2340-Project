package data.concretesources;

import data.abstractsources.Repository;
import model.entity.Player;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Temporary implementation of a Repository<Player> that we can use
 * while testing the application. Must be bound as a singleton in
 * the DI container, as it does not persist anything to disk.
 */
public class MemoryPlayerRepository implements Repository<Player> {

    private static int nextPlayerId = 0;
    private static int getNextPlayerId() {
        return nextPlayerId++;
    }

    private List<Player> players;

    /**
     * initialises an ArrayList of players
     */
    public MemoryPlayerRepository() {
        players = new ArrayList<>();
    }

    @Override
    public Set<Player> getAll() {
        HashSet<Player> set = new HashSet<>();
        set.addAll(players);
        return set;
    }

    @Override
    public Player get(Object id) {
        if (!(id instanceof Integer)) {
            throw new IllegalArgumentException("Player id must be an Integer");
        }
        int playerId = (int) id;
        return players.stream()
                .filter(new Predicate<Player>() {
                    @Override
                    public boolean test(Player player) {
                        return player.getId() == playerId;
                    }
                })
                .findAny()
                .get();
    }

    @Override
    public Player save(Player entity) {
        if (entity.getId() == -1) {
            entity.setId(getNextPlayerId());
        }
        if (!(players.contains(entity))) {
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
                .filter(new Predicate<Player>() {
                    @Override
                    public boolean test(Player player) {
                        return player.getId() == playerId;
                    }
                })
                .findAny()
                .get();
        players.remove(p);
        return p;
    }

    @Override
    public int size() {
        return players.size();
    }
}
