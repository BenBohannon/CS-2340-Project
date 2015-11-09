package model.service;

import com.google.inject.Inject;
import data.abstractsources.StoreDatasource;
import model.entity.Player;

/**
 * Created by connor on 10/4/15.
 */
public class StoreService {

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
    public StoreService(StoreDatasource pStoreDatasource) {
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

    public final int getEnergy() {
        return energy;
    }

    public final int getFood() {
        return food;
    }

    public final int getSmithore() {
        return smithore;
    }

    public final int getCrystite() {
        return crystite;
    }

    public final int getMuleCount() {
        return storeDatasource.getMuleCount();
    }

    public final void decrementMuleCount() {
        storeDatasource.setMuleCount(storeDatasource.getMuleCount() - 1);
    }

    /**
     * Sells 1 ENERGY unit to the specified player, at the going rate
     * @param player The player buying the ENERGY
     */
    public final void sellEnergy(Player player) {
        if (energy > 0) {
            if (player.getMoney() - energyPrice >= 0) {
                energy--;
                player.offsetEnergy(1);
                player.offsetMoney(-(energyPrice));
                storeDatasource.saveAmount(energy, food, smithore, crystite);
            }
        }
    }

    /**
     * Buys 1 unit of ENERGY from the player at the going rate
     * @param player The player who is selling the ENERGY
     */
    public final void buyEnergy(Player player) {
        if (player.getEnergy() > 0) {
            energy++;
            player.offsetEnergy(-1);
            player.offsetMoney(energyPrice);
            storeDatasource.saveAmount(energy, food, smithore, crystite);
        }
    }

    /**
     * Sells 1 unit of FOOD to the specified player at the going rate
     * @param player The player buying the FOOD
     */
    public final void sellFood(Player player) {
        if (food > 0) {
            if (player.getMoney() - foodPrice >= 0) {
                food--;
                player.offsetFood(1);
                player.offsetMoney(-(foodPrice));
                storeDatasource.saveAmount(energy, food, smithore, crystite);
            }
        }
    }

    /**
     * Buys 1 unit of FOOD from the specified player at the going rate
     * @param player The player selling the FOOD
     */
    public final void buyFood(Player player) {
        if (player.getFood() > 0) {
            food++;
            player.offsetFood(-1);
            player.offsetMoney(foodPrice);
            storeDatasource.saveAmount(energy, food, smithore, crystite);
        }
    }

    /**
     * Sells 1 unit of SMITHORE to the specified player at the going rate
     * @param player The player buying the SMITHORE
     */
    public final void sellSmithore(Player player) {
        if (smithore > 0) {
            if (player.getMoney() - smithorePrice >= 0) {
                smithore--;
                player.offsetSmithore(1);
                player.offsetMoney(-(smithorePrice));
                storeDatasource.saveAmount(energy, food, smithore, crystite);
            }
        }
    }

    /**
     * Buys 1 unit of SMITHORE from the specified player at the going rate
     * @param player The player selling the SMITHORE
     */
    public final void buySmithore(Player player) {
        if (player.getSmithore() > 0) {
            smithore++;
            player.offsetSmithore(-1);
            player.offsetMoney(smithorePrice);
            storeDatasource.saveAmount(energy, food, smithore, crystite);
        }
    }

    /**
     * Sells 1 unit of CRYSTITE to the specified player at the going rate
     * @param player The player buying the CRYSTITE
     */
    public final void sellCrystite(Player player) {
        if (crystite > 0) {
            if (player.getMoney() - crystitePrice >= 0) {
                crystite--;
                player.offsetCrystite(1);
                player.offsetMoney(-(crystitePrice));
                storeDatasource.saveAmount(energy, food, smithore, crystite);
            }
        }
    }

    /**
     * Buys 1 unit of CRYSTITE from the specified player at the going rate
     * @param player The player selling the CRYSTITE
     */
    public final void buyCrystite(Player player) {
        if (player.getCrystite() > 0) {
            crystite++;
            player.offsetCrystite(-1);
            player.offsetMoney(crystitePrice);
            storeDatasource.saveAmount(energy, food, smithore, crystite);
        }
    }

    public final int getEnergyPrice() {
        return energyPrice;
    }

    public final void setEnergyPrice(int pEnergyPrice) {
        this.energyPrice = pEnergyPrice;
    }

    public final int getFoodPrice() {
        return foodPrice;
    }

    public final void setFoodPrice(int pFoodPrice) {
        this.foodPrice = pFoodPrice;
    }

    public final int getSmithorePrice() {
        return smithorePrice;
    }

    public final void setSmithorePrice(int pSmithorePrice) {
        this.smithorePrice = pSmithorePrice;
    }

    public final int getCrystitePrice() {
        return crystitePrice;
    }

    public final void setCrystitePrice(int pCrystitePrice) {
        this.crystitePrice = pCrystitePrice;
    }
}
