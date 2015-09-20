package model;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by brian on 9/17/15.
 */
public class Player {
    private int smithore;
    private int crystite;
    private int food;
    private int energy;
    private int money;
    private int score;

    public Collection<Mule> mules;

    public Player() {
        mules = new LinkedList<>();
    }

    public void addMule(Mule mule) {
        if (mule == null) {
            throw new NullPointerException("mule cannot be null");
        }
        mules.add(mule);
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int amount) {
        money = money + amount;
    }

    //TODO implement ownership of land
}
