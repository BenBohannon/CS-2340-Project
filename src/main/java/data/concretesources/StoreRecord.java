package data.concretesources;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by brian on 10/29/15.
 */
@Entity
public class StoreRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int food;
    private int crystite;
    private int energy;
    private int smithore;
    private int energyPrice;
    private int foodPrice;
    private int crystitePrice;
    private int smithorePrice;

    private int muleCount;

    public final int getId() {
        return id;
    }

    public final void setId(int pId) {
        this.id = pId;
    }

    public final int getFood() {
        return food;
    }

    public final void setFood(int pFood) {
        this.food = pFood;
    }

    public final int getCrystite() {
        return crystite;
    }

    public final void setCrystite(int pCrystite) {
        this.crystite = pCrystite;
    }

    public final int getEnergy() {
        return energy;
    }

    public final void setEnergy(int pEnergy) {
        this.energy = pEnergy;
    }

    public final int getSmithore() {
        return smithore;
    }

    public final void setSmithore(int pSmithore) {
        this.smithore = pSmithore;
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

    public final int getCrystitePrice() {
        return crystitePrice;
    }

    public final void setCrystitePrice(int pCrystitePrice) {
        this.crystitePrice = pCrystitePrice;
    }

    public final int getSmithorePrice() {
        return smithorePrice;
    }

    public final void setSmithorePrice(int pSmithorePrice) {
        this.smithorePrice = pSmithorePrice;
    }

    public final int getMuleCount() {
        return muleCount;
    }

    public final void setMuleCount(int pMuleCount) {
        this.muleCount = pMuleCount;
    }
}
