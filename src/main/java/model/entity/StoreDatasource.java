package model.entity;

/**
 * Created by connor on 10/4/15.
 */
public interface StoreDatasource {

    public void saveAmount(int energy, int food, int smithore, int crystite);
    public void savePrice(int energyPrice, int foodPrice, int smithorePrice, int crystitePrice);
    public void getEnergy();
    public void getFood();
    public void getSmithore();
    public void getCrystite();

}
