package model.entity;

import com.google.inject.Inject;

/**
 * Created by connor on 10/4/15.
 */
public class Store {

    /*private int STARTING_ENERGY = 16;
    private int STARTING_FOOD = 16;
    private int STARTING_SMITHORE = 0;
    private int STARTING_CRYSTITE = 0;
    private int STARTING_ENERGY_PRICE = -25;
    private int STARTING_FOOD_PRICE = -30;
    private int STARTING_SMITHORE_PRICE = -50;
    private int STARTING_CRYSTITE_PRICE = -100;
    */

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
     * Sells 1 energy unit to the specified player, at the going rate
     * @param player The player buying the energy
     */
    public void sellEnergy(Player player) {
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
     * Buys 1 unit of energy from the player at the going rate
     * @param player The player who is selling the energy
     * @param price The price it is being sold for
     */
    public void buyEnergy(Player player, int price) {
        if (player.getEnergy() > 0) {
            energy++;
            player.offsetEnergy(-1);
            player.offsetMoney(price);
            storeDatasource.saveAmount(energy, food, smithore, crystite);
        }
    }

    /**
     * Sells 1 unit of food to the specified player at the going rate
     * @param player The player buying the food
     */
    public void sellFood(Player player) {
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
     * Buys 1 unit of food from the specified player at the going rate
     * @param player The player selling the food
     * @param price The price it is being sold for
     */
    public void buyFood(Player player, int price) {
        if (player.getMoney() > 0) {
            food++;
            player.offsetFood(-1);
            player.offsetMoney(price);
            storeDatasource.saveAmount(energy, food, smithore, crystite);
        }
    }

    /**
     * Sells 1 unit of smithore to the specified player at the going rate
     * @param player The player buying the smithore
     */
    public void sellSmithore(Player player) {
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
     * Buys 1 unit of smithore from the specified player at the going rate
     * @param player The player selling the smithore
     * @param price The price it is being sold for
     */
    public void buySmithore(Player player, int price) {
        if (player.getSmithore() > 0) {
            smithore++;
            player.offsetSmithore(-1);
            player.offsetMoney(price);
            storeDatasource.saveAmount(energy, food, smithore, crystite);
        }
    }

    /**
     * Sells 1 unit of crystite to the specified player at the going rate
     * @param player The player buying the crystite
     */
    public void sellCrystite(Player player) {
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
     * Buys 1 unit of crystite from the specified player at the going rate
     * @param player The player selling the crystite
     * @param price The price it is being sold for
     */
    public void buyCrystite(Player player, int price) {
        if (player.getCrystite() > 0) {
            crystite++;
            player.offsetCrystite(-1);
            player.offsetMoney(price);
            storeDatasource.saveAmount(energy, food, smithore, crystite);
        }
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
