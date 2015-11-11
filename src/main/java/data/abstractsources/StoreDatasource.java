package data.abstractsources;

/**
 * Created by connor on 10/4/15.
 */
public interface StoreDatasource {
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
