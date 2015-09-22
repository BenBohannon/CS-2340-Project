package model;

import map.Tile;

import java.util.ArrayList;
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
    private ArrayList<Tile> ownedProperties;

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

    /**
     * Adds the amount passed in to the players money
     * @param amount Amount to be added
     */
    public void addMoney(int amount) {
        money = money + amount;
    }

    /**
     * Adds the amount passed in to the players smithore
     * @param smithore Amount to be added
     */
    public void addSmithore(int smithore) {
        this.smithore += smithore;
    }

    /**
     * Gets the player's smithore
     * @return The smithore
     */
    public int getSmithore() {
        return smithore;
    }

    /**
     * Adds the amount passed in to the players crystite
     * @param crystite Amount to be added
     */
    public void addCrystite(int crystite) {
        this.crystite += crystite;
    }

    /**
     * Gets the player's crystite
     * @return The crystite
     */
    public int getCrystite() {
        return crystite;
    }

    /**
     * Adds the amount passed in to the players food
     * @param food Amount to be added
     */
    public void addFood(int food) {
        this.food += food;
    }

    /**
     * Gets the player's food
     * @return The food
     */
    public int getFood() {
        return food;
    }

    /**
     * Adds the amount passed in to the players energy
     * @param energy Amount to be added
     */
    public void addEnergy(int energy) {
        this.energy += energy;
    }

    /**
     * Gets the player's energy
     * @return The energy
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * Adds the amount passed in to the players score
     * @param score Amount to be added
     */
    public void addScore(int score) {
        this.score += score;
    }

    /**
     * Gets the player's score
     * @return The score
     */
    public int getScore() {
        return score;
    }

    /**
     * Constructs player that owns the lands passed in
     * @param ownedProperties The properties the player owns
     */
    public Player(ArrayList<Tile> ownedProperties) {
        this.ownedProperties = ownedProperties;
    }

    /**
     * Gets and returns the properties that the player owns
     * @return the properties the player owns
     */
    public ArrayList<Tile> getOwnedProperties() {
        return ownedProperties;
    }

    /**
     * Adds the property passed in to the list of owned properties
     * @param property The property being added to the list
     */
    public void addProperty(Tile property) {
        if (property == null) {
            throw new java.lang.IllegalArgumentException("Property cannot be null.");
        }
        ownedProperties.add(property);
    }

    /**
     * Removes the property from the list of owned properties
     * @param property The property to be removed
     */
    public void removeProperty(Tile property) {
        ownedProperties.remove(property);
    }

    /**
     * Checks if the property passed in is owned by the player
     * @param property The property being checked to see if the player owns it
     * @return whether or not the player owns the given property
     */
    public boolean ownsProperty(Tile property) {
        return ownedProperties.contains(property);
    }

    //TODO implement ownership of land
}
