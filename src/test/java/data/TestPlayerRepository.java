package data;

import model.Player;

import java.util.Collection;
import java.util.List;

/**
 * Created by brian on 9/17/15.
 */
public class TestPlayerRepository implements Repository<Player> {
    @Override
    public List<Player> getAll() {
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
}
