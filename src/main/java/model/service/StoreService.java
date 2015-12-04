package model.service;

import com.google.inject.Inject;
import data.abstractsources.Repository;
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
    private Repository<Player> playerRepository;

    private boolean isInitialized;


    @Inject
    public StoreService(StoreDatasource pStoreDatasource, Repository<Player> pPlayerRepository) {
        storeDatasource = pStoreDatasource;
        playerRepository = pPlayerRepository;
    }

    private void initializeFromDatasource() {
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
        if (!isInitialized) {
            initializeFromDatasource();
            isInitialized = true;
        }
        return energy;
    }

    public final int getFood() {
        if (!isInitialized) {
            initializeFromDatasource();
            isInitialized = true;
        }
        return food;
    }

    public final int getSmithore() {
        if (!isInitialized) {
            initializeFromDatasource();
            isInitialized = true;
        }
        return smithore;
    }

    public final int getCrystite() {
        if (!isInitialized) {
            initializeFromDatasource();
            isInitialized = true;
        }
        return crystite;
    }

    public final int getMuleCount() {
        if (!isInitialized) {
            initializeFromDatasource();
            isInitialized = true;
        }
        return storeDatasource.getMuleCount();
    }

    public final void decrementMuleCount() {
        if (!isInitialized) {
            initializeFromDatasource();
            isInitialized = true;
        }
        storeDatasource.setMuleCount(storeDatasource.getMuleCount() - 1);
    }

    /**
     * Sells 1 ENERGY unit to the specified player, at the going rate
     * @param player The player buying the ENERGY
     */
    public final void sellEnergy(Player player) {
        if (!isInitialized) {
            initializeFromDatasource();
            isInitialized = true;
        }
        if (energy > 0 && player.getMoney() + energyPrice >= 0) {
            energy--;
            player.offsetEnergy(1);
            player.offsetMoney(-(energyPrice));
            storeDatasource.saveAmount(energy, food, smithore, crystite);
            playerRepository.save(player);
        }
    }

    /**
     * Buys 1 unit of ENERGY from the player at the going rate
     * @param player The player who is selling the ENERGY
     */
    public final void buyEnergy(Player player) {
        if (!isInitialized) {
            initializeFromDatasource();
            isInitialized = true;
        }
        if (player.getEnergy() > 0) {
            energy++;
            player.offsetEnergy(-1);
            player.offsetMoney(energyPrice);
            storeDatasource.saveAmount(energy, food, smithore, crystite);
            playerRepository.save(player);
        }
    }

    /**
     * Sells 1 unit of FOOD to the specified player at the going rate
     * @param player The player buying the FOOD
     */
    public final void sellFood(Player player) {
        if (!isInitialized) {
            initializeFromDatasource();
            isInitialized = true;
        }
        if (food > 0 && player.getMoney() + foodPrice >= 0) {
            food--;
            player.offsetFood(1);
            player.offsetMoney(-(foodPrice));
            storeDatasource.saveAmount(energy, food, smithore, crystite);
            playerRepository.save(player);
        }
    }

    /**
     * Buys 1 unit of FOOD from the specified player at the going rate
     * @param player The player selling the FOOD
     */
    public final void buyFood(Player player) {
        if (!isInitialized) {
            initializeFromDatasource();
            isInitialized = true;
        }
        if (player.getFood() > 0) {
            food++;
            player.offsetFood(-1);
            player.offsetMoney(foodPrice);
            storeDatasource.saveAmount(energy, food, smithore, crystite);
            playerRepository.save(player);
        }
    }

    /**
     * Sells 1 unit of SMITHORE to the specified player at the going rate
     * @param player The player buying the SMITHORE
     */
    public final void sellSmithore(Player player) {
        if (!isInitialized) {
            initializeFromDatasource();
            isInitialized = true;
        }
        if (smithore > 0 && player.getMoney() + smithorePrice >= 0) {
            smithore--;
            player.offsetSmithore(1);
            player.offsetMoney(-(smithorePrice));
            storeDatasource.saveAmount(energy, food, smithore, crystite);
            playerRepository.save(player);
        }
    }

    /**
     * Buys 1 unit of SMITHORE from the specified player at the going rate
     * @param player The player selling the SMITHORE
     */
    public final void buySmithore(Player player) {
        if (!isInitialized) {
            initializeFromDatasource();
            isInitialized = true;
        }
        if (player.getSmithore() > 0) {
            smithore++;
            player.offsetSmithore(-1);
            player.offsetMoney(smithorePrice);
            storeDatasource.saveAmount(energy, food, smithore, crystite);
            playerRepository.save(player);
        }
    }

    /**
     * Sells 1 unit of CRYSTITE to the specified player at the going rate
     * @param player The player buying the CRYSTITE
     */
    public final void sellCrystite(Player player) {
        if (!isInitialized) {
            initializeFromDatasource();
            isInitialized = true;
        }
        if (crystite > 0 && player.getMoney() + crystitePrice >= 0) {
            crystite--;
            player.offsetCrystite(1);
            player.offsetMoney(-(crystitePrice));
            storeDatasource.saveAmount(energy, food, smithore, crystite);
            playerRepository.save(player);
        }
    }

    /**
     * Buys 1 unit of CRYSTITE from the specified player at the going rate
     * @param player The player selling the CRYSTITE
     */
    public final void buyCrystite(Player player) {
        if (!isInitialized) {
            initializeFromDatasource();
            isInitialized = true;
        }
        if (player.getCrystite() > 0) {
            crystite++;
            player.offsetCrystite(-1);
            player.offsetMoney(crystitePrice);
            storeDatasource.saveAmount(energy, food, smithore, crystite);
            playerRepository.save(player);
        }
    }

    public final int getEnergyPrice() {
        if (!isInitialized) {
            initializeFromDatasource();
            isInitialized = true;
        }
        return energyPrice;
    }

    public final void setEnergyPrice(int pEnergyPrice) {
        if (!isInitialized) {
            initializeFromDatasource();
            isInitialized = true;
        }
        this.energyPrice = pEnergyPrice;
    }

    public final int getFoodPrice() {
        if (!isInitialized) {
            initializeFromDatasource();
            isInitialized = true;
        }
        return foodPrice;
    }

    public final void setFoodPrice(int pFoodPrice) {
        if (!isInitialized) {
            initializeFromDatasource();
            isInitialized = true;
        }
        this.foodPrice = pFoodPrice;
    }

    public final int getSmithorePrice() {
        if (!isInitialized) {
            initializeFromDatasource();
            isInitialized = true;
        }
        return smithorePrice;
    }

    public final void setSmithorePrice(int pSmithorePrice) {
        if (!isInitialized) {
            initializeFromDatasource();
            isInitialized = true;
        }
        this.smithorePrice = pSmithorePrice;
    }

    public final int getCrystitePrice() {
        if (!isInitialized) {
            initializeFromDatasource();
            isInitialized = true;
        }
        return crystitePrice;
    }

    public final void setCrystitePrice(int pCrystitePrice) {
        if (!isInitialized) {
            initializeFromDatasource();
            isInitialized = true;
        }
        this.crystitePrice = pCrystitePrice;
    }
}
