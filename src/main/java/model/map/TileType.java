package model.map;

/**
 * Created by Benjamin on 9/17/2015.
 */
public enum TileType {
    RIVER(4, 2, 0),
    MOUNTAIN_1(1, 1, 2),
    MOUNTAIN_2(1, 1, 3),
    MOUNTAIN_3(1, 1, 4),
    PLAIN(2, 3, 1),
    TOWN(0, 0, 0);


    private int foodPC;
    private int energyPC;
    private int smithorePC;

    TileType(int foodPC, int energyPC, int smithorePC) {
        this.foodPC = foodPC;
        this.energyPC = energyPC;
        this.smithorePC = smithorePC;
    }

    /**
     * The food production capacity is the amount of food that can be produced on this tile per turn
     * with a food mule
     * @return food production capacity
     */
    public int getFoodPC() {
        return foodPC;
    }

    /**
     * The energy production capacity is the amount of energy that can be produced on this tile per turn
     * with an energy mule
     * @return energy production capacity
     */
    public int getEnergyPC() {
        return energyPC;
    }

    /**
     * The smithore production capacity is the amount of smithore that can be produced on this tile per turn
     * with a smithore mule
     * @return smithore production capacity
     */
    public int getSmithorePC() {
        return smithorePC;
    }
}
