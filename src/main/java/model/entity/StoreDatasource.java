package model.entity;

/**
 * Created by connor on 10/4/15.
 */
public interface StoreDatasource {

    public int energy = 16;
    public int food = 16;
    public int smithore = 0;
    public int crystite = 0;

    public int energyPrice = -25;
    public int foodPrice = -30;
    public int smithorePrice = -50;
    public int crystitePrice = -100;

    public void saveAmount(int energy, int food, int smithore, int crystite);
    public void savePrice(int energyPrice, int foodPrice, int smithorePrice, int crystitePrice);
    public int getEnergy();
    public int getFood();
    public int getSmithore();
    public int getCrystite();
    public int getEnergyPrice();
    public int getFoodPrice();
    public int getSmithorePrice();
    public int getCrystitePrice();

}
