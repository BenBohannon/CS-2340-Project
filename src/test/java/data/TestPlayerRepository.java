package data;

import data.abstractsources.Repository;
import model.entity.Player;

import java.util.List;
import java.util.Set;

/**
 * Created by brian on 9/17/15.
 */
public class TestPlayerRepository implements Repository<Player> {
    @Override
    public Set<Player> getAll() {
        System.out.println("getAll()");
        return null;
    }

    @Override
    public Player get(Object id) {
        System.out.println("get()");
        return null;
    }

    @Override
    public Player save(Player entity) {
        System.out.println("save()");
        return null;
    }

    @Override
    public Player delete(Object id) {
        System.out.println("delete()");
        return null;
    }

    @Override
    public int size() {
        System.out.println("size()");
        return -1;
    }
}
