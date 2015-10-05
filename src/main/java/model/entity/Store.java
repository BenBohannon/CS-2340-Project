package model.entity;

import com.google.inject.Inject;

/**
 * Created by connor on 10/4/15.
 */
public class Store {

    private int STARTING_ENERGY = 16;
    private int STARTING_FOOD = 16;
    private int STARTING_SMITHORE = 0;
    private int STARTING_CRYSTITE = 0;
    private int STARTING_ENERGY_PRICE = 25;
    private int STARTING_FOOD_PRICE = 30;
    private int STARTING_SMITHORE_PRICE = 50;
    private int STARTING_CRYSTITE_PRICE = 100;

    private int energy;
    private int food;
    private int smithore;
    private int crystite;

    private int energyPrice;
    private int foodPrice;
    private int smithorePrice;
    private int crystitePrice;

    private StoreDatasource storeDatasource;


    @Inject
    public Store(StoreDatasource storeDatasource) {
        this.storeDatasource = storeDatasource;

        energy = STARTING_ENERGY;
        food = STARTING_FOOD;
        smithore = STARTING_SMITHORE;
        crystite = STARTING_CRYSTITE;

        energyPrice = STARTING_ENERGY_PRICE;
        foodPrice = STARTING_FOOD_PRICE;
        smithorePrice = STARTING_SMITHORE_PRICE;
        crystitePrice = STARTING_CRYSTITE_PRICE;

        storeDatasource.saveAmount(energy, food, smithore, crystite);
        storeDatasource.savePrice(energyPrice, foodPrice, smithorePrice, crystitePrice);
    }

    /**
     * Sells 1 energy unit to the specified player, at the going rate
     * @param player The player buying the energy
     */
    public void sellEnergy(Player player) {
        if (energy != 0) {
            energy--;
            player.buyEnergy(energyPrice);
            storeDatasource.saveAmount(energy, food, smithore, crystite);
        }
    }

    /**
     * Buys 1 unit of energy from the player at the going rate
     * @param player The player who is selling the energy
     * @param price The price it is being sold for
     */
    public void buyEnergy(Player player, int price) {
        energy++;
        player.sellEnergy(price);
        storeDatasource.saveAmount(energy, food, smithore, crystite);
    }

    /**
     * Sells 1 unit of food to the specified player at the going rate
     * @param player The player buying the food
     */
    public void sellFood(Player player) {
        if (food != 0) {
            food--;
            player.buyFood(foodPrice);
            storeDatasource.saveAmount(energy, food, smithore, crystite);
        }
    }

    /**
     * Buys 1 unit of food from the specified player at the going rate
     * @param player The player selling the food
     * @param price The price it is being sold for
     */
    public void buyFood(Player player, int price) {
        food++;
        player.sellFood(price);
        storeDatasource.saveAmount(energy, food, smithore, crystite);
    }

    /**
     * Sells 1 unit of smithore to the specified player at the going rate
     * @param player The player buying the smithore
     */
    public void sellSmithore(Player player) {
        if (smithore != 0) {
            smithore--;
            player.buySmithore(smithorePrice);
            storeDatasource.saveAmount(energy, food, smithore, crystite);
        }
    }

    /**
     * Buys 1 unit of smithore from the specified player at the going rate
     * @param player The player selling the smithore
     * @param price The price it is being sold for
     */
    public void buySmithore(Player player, int price) {
        smithore++;
        player.sellSmithore(price);
        storeDatasource.saveAmount(energy, food, smithore, crystite);
    }

    /**
     * Sells 1 unit of crystite to the specified player at the going rate
     * @param player The player buying the crystite
     */
    public void sellCrystite(Player player) {
        if (crystite != 0) {
            crystite--;
            player.sellCrystite(crystitePrice);
            storeDatasource.saveAmount(energy, food, smithore, crystite);
        }
    }

    /**
     * Buys 1 unit of crystite from the specified player at the going rate
     * @param player The player selling the crystite
     * @param price The price it is being sold for
     */
    public void buyCrystite(Player player, int price) {
        crystite++;
        player.sellCrystite(price);
        storeDatasource.saveAmount(energy, food, smithore, crystite);
    }

    public int getEnergyPrice() {
        return energyPrice;
    }

    public void setEnergyPrice(int energyPrice) {
        this.energyPrice = energyPrice;
    }

    public int getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(int foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getSmithorePrice() {
        return smithorePrice;
    }

    public void setSmithorePrice(int smithorePrice) {
        this.smithorePrice = smithorePrice;
    }

    public int getCrystitePrice() {
        return crystitePrice;
    }

    public void setCrystitePrice(int crystitePrice) {
        this.crystitePrice = crystitePrice;
    }
}
