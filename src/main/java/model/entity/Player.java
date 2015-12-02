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
    @ManyToOne(fetch = FetchType.EAGER)
    private GameSaveMeta gameSaveMeta;
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

    /**
     * initiales mules of players
     */
    public Player() {
        setMules(new LinkedList<>());
        ownedProperties = new ArrayList<>();
    }

    /**
     * adds a mule to a player
     * @param mule mule to be added
     */
    public void addMule(Mule mule) {
        if (mule == null) {
            throw new IllegalArgumentException("mule cannot be null");
        }
        getMules().add(mule);
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
     * Sets the player's money. This is necessary in addition to
     * the convenience method Player#offsetMoney() because it
     * @param pMoney
     */
    public void setMoney(int pMoney) {
        money = pMoney;
    }

    /**
     * offsets money by given amount.
     * @param pMoney amount to offset money by
     */
    public void offsetMoney(int pMoney) {
        this.money += pMoney;
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
     * @param pColor player color
     */
    public void setColor(Color pColor) {
        this.color = pColor;
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
     * @param pRace race of player
     */
    public void setRace(PlayerRace pRace) {
        this.race = pRace;
    }

    /**
     * sets id of player
     * @param pId player id
     */
    public void setId(int pId) {
        this.id = pId;
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
     * @param pRank rank of player
     */
    public void setRank(int pRank) {
        this.rank = pRank;
    }


    /**
     * Gets the player's SMITHORE
     * @return The SMITHORE
     */
    public final int getSmithore() {
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
     * Gets the player's CRYSTITE
     * @return The CRYSTITE
     */
    public final int getCrystite() {
        return crystite;
    }

    /**
     * offsets food by given amount.
     * @param amount amount to offset food by
     */
    public void offsetFood(int amount) {
        food += amount;
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

    /**
     * offsets energy by given amount.
     * @param amount amount to offset energy by
     */
    public void offsetEnergy(int amount) {
        energy += amount;
    }


    /**
     * Constructs player that owns the lands passed in
     * @param pOwnedProperties The properties the player owns
     */
    public Player(List<Tile> pOwnedProperties) {
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
            throw new IllegalArgumentException("Cannot buy, insufficient funds.");
        }
        if (property.ownedBy() != null) {
            throw new IllegalArgumentException("Cannot buy, already owned");
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

    /**
     * gets id of player
     * @return player id num
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param pBTU
     * @return
     */
    public int getBTU(int pBTU) {
        //TODO different based on race
        return pBTU;
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
     * @param pName name of player
     */
    public void setName(String pName) {
        this.name = pName;
    }

    /**
     * gets player score.
     * @return score
     */
    public int getScore() {
        return score;
    }

    public final void setMules(Collection<Mule> pMules) {
        this.mules = pMules;
    }

    public GameSaveMeta getGameSaveMeta() {
        return gameSaveMeta;
    }

    public void setGameSaveMeta(GameSaveMeta gameSaveMeta) {
        this.gameSaveMeta = gameSaveMeta;
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
