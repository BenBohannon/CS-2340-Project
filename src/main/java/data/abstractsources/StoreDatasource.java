package data.abstractsources;

/**
 * Created by connor on 10/4/15.
 */
public interface StoreDatasource {

    int ENERGY = 16;
    int FOOD = 16;
    int SMITHORE = 0;
    int CRYSTITE = 0;

    int ENERGY_PRICE = -2500;
    int FOOD_PRICE = -3000;
    int SMITHORE_PRICE = -5000;
    int CRYSTITE_PRICE = -10000;

    void saveAmount(int energy, int food, int smithore, int crystite);
    void savePrice(int energyPrice, int foodPrice, int smithorePrice, int crystitePrice);
    int getEnergy();
    int getFood();
    int getSmithore();
    int getCrystite();
    int getEnergyPrice();
    int getFoodPrice();
    int getSmithorePrice();
    int getCrystitePrice();
    int getMuleCount();
    void setMuleCount(int muleCount);

}
