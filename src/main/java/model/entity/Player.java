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
    private int smithore;
    private int crystite;
    private int food;
    private int energy;
    private int money;
    private int id;
    private String name;
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

    public void offsetMoney(int money) {
        this.money += money;
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

    public void offsetSmithore(int amount) {
        smithore += amount;
    }


    /**
     * Gets the player's smithore
     * @return The smithore
     */
    public int getSmithore() {
        return smithore;
    }

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

    public void offsetFood(int amount) {
        food += amount;
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

    public int getPTU(int BTU) {
        //TODO different based on race
        return BTU;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
