package model;

import javafx.scene.paint.Color;
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
    private int id;
    private Color color;
    private ArrayList<Tile> ownedProperties = new ArrayList<>();

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

    public void removeMoney(int amount) {
        if (money - amount >= 0) {
            money = money - amount;
        }
    }

    public int getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Adds the amount passed in to the players smithore
     * @param smithore Amount to be added
     * @param price Price of the smithore
     */
    public void buySmithore(int smithore, int price) {
        if (this.money - price >= 0) {
            this.smithore += smithore;
            removeMoney(price);
            System.out.println(money);
        }
    }

    /**
     * Allows the player to sell smithore
     * @param smithore Amount to be sold
     * @param price Price of the smithore
     */
    public void sellSmithore(int smithore, int price) {
        if (this.smithore - smithore >= 0) {
            this.smithore -= smithore;
            addMoney(price);
            System.out.println(money);
        }
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
     * @param price Price of the crystite
     */
    public void buyCrystite(int crystite, int price) {
        if (this.money - price >= 0) {
            this.crystite += crystite;
            removeMoney(price);
            System.out.println(money);
        }
    }

    /**
     * Allows the player to sell crystite
     * @param crystite Amount to be sold
     * @param price Price of the crystite
     */
    public void sellCrystite(int crystite, int price) {
        if (this.crystite - crystite >= 0) {
            this.crystite -= crystite;
            addMoney(price);
            System.out.println(money);
        }
    }

    /**
     * Gets the player's crystite
     * @return The crystite
     */
    public int getCrystite() {
        return crystite;
    }

    /**
     * Allows the player to buy food
     * @param food Amount to be added
     * @param price Price of the food
     */
    public void buyFood(int food, int price) {
        if (this.money - price >= 0) {
            removeMoney(price);
            this.food += food;
            System.out.println(money);
        }
    }

    /**
     * Allows the player to sell food
     * @param food Amount to be sold
     * @param price Price of the food
     */
    public void sellFood(int food, int price) {
        if (this.food - food >= 0) {
            this.food -= food;
            addMoney(price);
            System.out.println(money);
        }
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
     * Removes energy from the player
     * @param energy Amount to be removed
     */
    public void removeEnergy(int energy) {
        this.energy -= energy;
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
     * Lowers the player's score
     * @param score Amount to be lowered by
     */
    public void lowerScore(int score) {
        this.score -= score;
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
}