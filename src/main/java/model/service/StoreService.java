package model.service;

import com.google.inject.Inject;
import data.abstractsources.StoreDatasource;
import model.entity.Player;

/**
 * Created by connor on 10/4/15.
 */
public class StoreService {

    /**
     * Quantity of energy in the store.
     */
    private int energy;

    /**
     * Quantity of food in the store.
     */
    private int food;

    /**
     * Quantity of smithore in the store.
     */
    private int smithore;

    /**
     * Quantity of crystite in the store.
     */
    private int crystite;

    /**
     * Price of energy.
     */
    private int energyPrice;

    /**
     * Price of food.
     */
    private int foodPrice;

    /**
     * Price of smithore.
     */
    private int smithorePrice;

    /**
     * Price of crystite.
     */
    private int crystitePrice;

    /**
     * Model information holder for the store.
     */
    private StoreDatasource storeDatasource;

    /**
     * Constructor for a StoreService, which is called by the dependency
     * injection code.
     * @param pStoreDatasource Model information holder for the store.
     */
    @Inject
    public StoreService(final StoreDatasource pStoreDatasource) {
        storeDatasource = pStoreDatasource;

        energy = storeDatasource.getEnergy();
        food = storeDatasource.getFood();
        smithore = storeDatasource.getSmithore();
        crystite = storeDatasource.getCrystite();

        energyPrice = storeDatasource.getEnergyPrice();
        foodPrice = storeDatasource.getFoodPrice();
        smithorePrice = storeDatasource.getSmithorePrice();
        crystitePrice = storeDatasource.getCrystitePrice();
    }

    /**
     * Gets the number of energy in the store.
     * @return amount of Energy in the store
     */
    public final int getEnergy() {
        return energy;
    }

    /**
     * Gets the number of food in the store.
     * @return amount of food in the store
     */
    public final int getFood() {
        return food;
    }

    /**
     * Gets the number of smithore in the store.
     * @return amount of smithore in the store
     */
    public final int getSmithore() {
        return smithore;
    }

    /**
     * Gets the number of crystite in the store.
     * @return amount of crystite in the store
     */
    public final int getCrystite() {
        return crystite;
    }

    /**
     * Gets the number of mules in the store.
     * @return amount of mules in the store
     */
    public final int getMuleCount() {
        return storeDatasource.getMuleCount();
    }

    /**
     * Decreases the number of mules in the store by 1.
     */
    public final void decrementMuleCount() {
        storeDatasource.setMuleCount(storeDatasource.getMuleCount() - 1);
    }

    /**
     * Sells 1 ENERGY unit to the specified player, at the going rate.
     * @param player The player buying the ENERGY
     */
    public final void sellEnergy(final Player player) {
        if (energy > 0) {
            if (player.getMoney() + energyPrice >= 0) {
                energy--;
                player.offsetEnergy(1);
                player.offsetMoney(energyPrice);
                storeDatasource.saveAmount(energy, food, smithore, crystite);
            }
        }
    }

    /**
     * Buys 1 unit of ENERGY from the player at the going rate.
     * @param player The player who is selling the ENERGY
     * @param price The price it is being sold for
     */
    public final void buyEnergy(final Player player, final int price) {
        if (player.getEnergy() > 0) {
            energy++;
            player.offsetEnergy(-1);
            player.offsetMoney(price);
            storeDatasource.saveAmount(energy, food, smithore, crystite);
        }
    }

    /**
     * Sells 1 unit of FOOD to the specified player at the going rate.
     * @param player The player buying the FOOD
     */
    public final void sellFood(final Player player) {
        if (food > 0) {
            if (player.getMoney() + foodPrice >= 0) {
                food--;
                player.offsetFood(1);
                player.offsetMoney(foodPrice);
                storeDatasource.saveAmount(energy, food, smithore, crystite);
            }
        }
    }

    /**
     * Buys 1 unit of FOOD from the specified player at the going rate.
     * @param player The player selling the FOOD
     * @param price The price it is being sold for
     */
    public final void buyFood(final Player player, final int price) {
        if (player.getFood() > 0) {
            food++;
            player.offsetFood(-1);
            player.offsetMoney(price);
            storeDatasource.saveAmount(energy, food, smithore, crystite);
        }
    }

    /**
     * Sells 1 unit of SMITHORE to the specified player at the going rate.
     * @param player The player buying the SMITHORE
     */
    public final void sellSmithore(final Player player) {
        if (smithore > 0) {
            if (player.getMoney() + smithorePrice >= 0) {
                smithore--;
                player.offsetSmithore(1);
                player.offsetMoney(smithorePrice);
                storeDatasource.saveAmount(energy, food, smithore, crystite);
            }
        }
    }

    /**
     * Buys 1 unit of SMITHORE from the specified player at the going rate.
     * @param player The player selling the SMITHORE
     * @param price The price it is being sold for
     */
    public final void buySmithore(final Player player, final int price) {
        if (player.getSmithore() > 0) {
            smithore++;
            player.offsetSmithore(-1);
            player.offsetMoney(price);
            storeDatasource.saveAmount(energy, food, smithore, crystite);
        }
    }

    /**
     * Sells 1 unit of CRYSTITE to the specified player at the going rate.
     * @param player The player buying the CRYSTITE
     */
    public final void sellCrystite(final Player player) {
        if (crystite > 0) {
            if (player.getMoney() + crystitePrice >= 0) {
                crystite--;
                player.offsetCrystite(1);
                player.offsetMoney(crystitePrice);
                storeDatasource.saveAmount(energy, food, smithore, crystite);
            }
        }
    }

    /**
     * Buys 1 unit of CRYSTITE from the specified player at the going rate.
     * @param player The player selling the CRYSTITE
     * @param price The price it is being sold for
     */
    public final void buyCrystite(final Player player, final int price) {
        if (player.getCrystite() > 0) {
            crystite++;
            player.offsetCrystite(-1);
            player.offsetMoney(price);
            storeDatasource.saveAmount(energy, food, smithore, crystite);
        }
    }

    /**
     * Gets the price of energy in the store.
     * @return price of Energy
     */
    public final int getEnergyPrice() {
        return energyPrice;
    }

    /*
    public final void setEnergyPrice(final int pEnergyPrice) {
        this.energyPrice = pEnergyPrice;
    }
    */

    /**
     * Gets the price of food in the store.
     * @return price of food
     */
    public final int getFoodPrice() {
        return foodPrice;
    }

    /*
    public final void setFoodPrice(final int pFoodPrice) {
        this.foodPrice = pFoodPrice;
    }
    */

    /**
     * Gets the price of smithore in the store.
     * @return price of smithore
     */
    public final int getSmithorePrice() {
        return smithorePrice;
    }

    /*
    public final void setSmithorePrice(final int pSmithorePrice) {
        this.smithorePrice = pSmithorePrice;
    }
    */

    /**
     * Gets the price of crystite in the store.
     * @return price of crystite
     */
    public final int getCrystitePrice() {
        return crystitePrice;
    }

    /*
    public final void setCrystitePrice(final int pCrystitePrice) {
        this.crystitePrice = pCrystitePrice;
    }
    */
}
