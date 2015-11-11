package model.entity;

import javafx.scene.paint.Color;
import model.map.Tile;


import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by brian on 9/17/15.
 */
public class Player {
    private int score;
    private int smithore;
    private int crystite;
    private int food;
    private int energy;
    private int money = 2000;
    private int id;
    private PlayerRace race;
    private String name;
    private Color color;
    private ArrayList<Tile> ownedProperties = new ArrayList<>();
    private int rank;

    private Collection<Mule> mules;

    /**
     * initiales mules of players
     */
    public Player() {
        mules = new LinkedList<>();
    }

    /**
     * adds a mule to a player
     * @param mule mule to be added
     */
    public void addMule(Mule mule) {
        if (mule == null) {
            throw new NullPointerException("mule cannot be null");
        }
        mules.add(mule);
    }

    /**
     * gets mules of player
     * @return mules
     */
    public Collection<Mule> getMules() {
        return mules;
    }

    /**
     * gets money of player
     * @return money
     */
    public int getMoney() {
        return money;
    }

    /**
     * offsets money by given amount.
     * @param money amount to offset money by
     */
    public void offsetMoney(int money) {
        this.money += money;
    }

    /**
     * gets color of player
     * @return player color
     */
    public Color getColor() {
        return color;
    }

    /**
     * sets color of player
     * @param color player color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * gets race of player
     * @return race of player
     */
    public PlayerRace getRace() {
        return race;
    }

    /**
     * sets race of player
     * @param race race of player
     */
    public void setRace(PlayerRace race) {
        this.race = race;
    }

    /**
     * sets id of player
     * @param id player id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * offsets smithore by given amount.
     * @param amount amount to offset smithore by
     */
    public void offsetSmithore(int amount) {
        smithore += amount;
    }

    /**
     * gets rank of player
     * @return rank of player
     */
    public int getRank() {
        return rank;
    }

    /**
     * sets rank of player
     * @param rank rank of player
     */
    public void setRank(int rank) {
        this.rank = rank;
    }


    /**
     * Gets the player's smithore
     * @return The smithore
     */
    public int getSmithore() {
        return smithore;
    }

    /**
     * offsets Crystite by given amount.
     * @param amount amount to offset Crystite by
     */
    public void offsetCrystite(int amount) {
        crystite += amount;
    }

    /**
     * Gets the player's crystite
     * @return The crystite
     */
    public int getCrystite() {
        return crystite;
    }

    /**
     * offsets food by given amount.
     * @param amount amount to offset food by
     */
    public void offsetFood(int amount) {
        food += amount;
        System.out.println(food);
    }

    /**
     * Gets the player's food
     * @return The food
     */
    public int getFood() {
        return food;
    }


    /**
     * Gets the player's energy
     * @return The energy
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * offsets energy by given amount.
     * @param amount amount to offset energy by
     */
    public void offsetEnergy(int amount) {
        energy += amount;
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
            throw new java.lang.IllegalArgumentException(
                    "Property cannot be null.");
        }
        ownedProperties.add(property);
        property.setOwner(this);
    }

    /**
     * Removes the property from the list of owned properties
     * @param property The property to be removed
     */
    public void removeProperty(Tile property) {
        ownedProperties.remove(property);
        property.setOwner(null);
    }

    /**
     * Buys the property if the player has sufficient funds
     * @param property The property to be bought
     * @param price The price of the property
     * @throws RuntimeException if the player does not have enough money
     */
    public void buyProperty(Tile property, int price) {
        if (money - price < 0) {
            //Label l = new Label("Cannot buy, insufficient funds");
            throw new RuntimeException("Cannot buy, insufficient funds.");
        }
        if (property.ownedBy() != null) {
            throw new RuntimeException("Cannot buy, already owned");
        }
        money = money - price;
        ownedProperties.add(property);
        property.setOwner(this);
    }

    /**
     * Sells the property for the given price
     * @param property The property being sold
     * @param price The price it is being sold for
     */
    public void sellProperty(Tile property, int price) {
        money += price;
        removeProperty(property);
        property.setOwner(null);
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

    /**
     * gets id of player
     * @return player id num
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param BTU
     * @return
     */
    public int getBTU(int BTU) {
        //TODO different based on race
        return BTU;
    }

    /**
     * gets name of player.
     * @return name of player
     */
    public String getName() {
        return name;
    }

    /**
     * sets player name.
     * @param name name of player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets player score.
     * @return score
     */
    public int getScore() {
        return score;
    }
}
