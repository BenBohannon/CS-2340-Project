package model;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by brian on 9/17/15.
 */
public class Player {
    private int id;
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


    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || !(obj instanceof Player)) {
            return false;
        }
        return ((Player) obj).getId() == getId();
    }

    @Override
    public int hashCode() {
        return id;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public int getPTU(int BTU) {
        //TODO different based on race
        return BTU;
    }

    public int getFood() {
        return food;
    }
}
