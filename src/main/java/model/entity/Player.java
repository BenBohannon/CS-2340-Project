package model.entity;

import javafx.scene.paint.Color;
import model.map.Tile;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by brian on 9/17/15.
 */

@Entity
public class Player {

    private int score;
    private int smithore;
    private int crystite;
    private int food;
    private int energy;
    private int money;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private PlayerRace race;
    private String name;
    @Convert(converter = ColorConverter.class)
    private Color color;
    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    private List<Tile> ownedProperties;
    @Transient
    private int rank;

    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    private Collection<Mule> mules;

    public Player() {
        setMules(new LinkedList<>());
        ownedProperties = new ArrayList<>();
    }

    public final void addMule(Mule mule) {
        if (mule == null) {
            throw new NullPointerException("mule cannot be null");
        }
        getMules().add(mule);
    }

    public final void setMoney(int pMoney) {
        money = pMoney;
    }

    public final int getMoney() {
        return money;
    }

    public final void offsetMoney(int pMoney) {
        this.money += pMoney;
    }

    public final Color getColor() {
        return color;
    }

    public final void setColor(Color pColor) {
        this.color = pColor;
    }

    public final PlayerRace getRace() { return race; }

    public final void setRace(PlayerRace pRace) {
        this.race = pRace;
    }

    public final void setId(int pId) {
        this.id = pId;
    }

    public final void offsetSmithore(int amount) {
        smithore += amount;
    }

    public final int getRank() {
        return rank;
    }

    public final void setRank(int pRank) {
        this.rank = pRank;
    }


    /**
     * Gets the player's SMITHORE
     * @return The SMITHORE
     */
    public final int getSmithore() {
        return smithore;
    }

    public final void offsetCrystite(int amount) {
        crystite += amount;
    }

    /**
     * Gets the player's CRYSTITE
     * @return The CRYSTITE
     */
    public final int getCrystite() {
        return crystite;
    }

    public final void offsetFood(int amount) {
        food += amount;
        System.out.println(food);
    }

    /**
     * Gets the player's FOOD
     * @return The FOOD
     */
    public final int getFood() {
        return food;
    }


    /**
     * Gets the player's ENERGY
     * @return The ENERGY
     */
    public final int getEnergy() {
        return energy;
    }

    public final void offsetEnergy(int amount) {
        energy += amount;
    }


    /**
     * Constructs player that owns the lands passed in
     * @param pOwnedProperties The properties the player owns
     */
    public Player(ArrayList<Tile> pOwnedProperties) {
        this.ownedProperties = pOwnedProperties;
    }

    /**
     * Gets and returns the properties that the player owns
     * @return the properties the player owns
     */
    public final List<Tile> getOwnedProperties() {
        return ownedProperties;
    }

    /**
     * Adds the property passed in to the list of owned properties
     * @param property The property being added to the list
     */
    public final void addProperty(Tile property) {
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
    public final void removeProperty(Tile property) {
        ownedProperties.remove(property);
        property.setOwner(null);
    }

    /**
     * Buys the property if the player has sufficient funds
     * @param property The property to be bought
     * @param price The price of the property
     * @throws RuntimeException if the player does not have enough money
     */
    public final void buyProperty(Tile property, int price) {
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
    public final void sellProperty(Tile property, int price) {
        money += price;
        removeProperty(property);
        property.setOwner(null);
    }

    /**
     * Checks if the property passed in is owned by the player
     * @param property The property being checked to see if the player owns it
     * @return whether or not the player owns the given property
     */
    public final boolean ownsProperty(Tile property) {
        return ownedProperties.contains(property);
    }

    @Override
    public final boolean equals(Object obj) {
        if ((obj == null) || !(obj instanceof Player)) {
            return false;
        }
        return ((Player) obj).getId() == getId();
    }

    @Override
    public final int hashCode() {
        return id;
    }

    public final int getId() {
        return id;
    }

    public final int getPTU(int pBTU) {
        //TODO different based on race
        return pBTU;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String pName) {
        this.name = pName;
    }

    public final int getScore() {
        return score;
    }

    public final Collection<Mule> getMules() {
        return mules;
    }

    public final void setMules(Collection<Mule> pMules) {
        this.mules = pMules;
    }

    @Converter
    public static class ColorConverter implements AttributeConverter<Color, Integer> {

        private static final int LOW_BITS = 0x000000FF;
        private static final int MAX_COLOR_COMPONENT = 255;

        @Override
        public final Integer convertToDatabaseColumn(Color attribute) {
            if (attribute == null) {
                return 0;
            }
            return attribute.hashCode();
        }

        @Override
        public final Color convertToEntityAttribute(Integer dbData) {
            /* moves the R, G, and B components of the integer color data
               into the bottom eight bits and then removes all other bits
               24, 16, and 8 are the differences in bit position that
               the bits need to be moved. These numbers will never change
               so long as the int does not change.*/
            int r = ((dbData >> 24) & LOW_BITS);
            int g = (dbData >> 16) & LOW_BITS;
            int b = (dbData >> 8) & LOW_BITS;
            // change to 0.0 - 1.0 //
            return Color.color((double) r / MAX_COLOR_COMPONENT, (double) g / MAX_COLOR_COMPONENT,
                    (double) b / MAX_COLOR_COMPONENT);
        }

    }
}
