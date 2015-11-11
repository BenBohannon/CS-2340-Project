package model.map;

/**
 * Created by Benjamin on 9/17/2015.
 */
public enum TileType {
    RIVER(4, 2, 0, "/tiles/river.png"),
    MOUNTAIN_1(1, 1, 2, "/tiles/1mountain.png"),
    MOUNTAIN_2(1, 1, 3, "/tiles/2mountains.png"),
    MOUNTAIN_3(1, 1, 4, "/tiles/3mountains.png"),
    PLAIN(2, 3, 1, "/tiles/plain.png"),
    TOWN(0, 0, 0, "/tiles/town.png");


    private int foodPC;
    private int energyPC;
    private int smithorePC;
    private String imagePath;

    TileType(int pFoodPC, int pEnergyPC, int pSmithorePC, String pImagePath) {
        this.imagePath = pImagePath;
        this.foodPC = pFoodPC;
        this.energyPC = pEnergyPC;
        this.smithorePC = pSmithorePC;
    }

    /**
     * The FOOD production capacity is the amount of FOOD that can be produced on this tile per turn
     * with a FOOD mule
     * @return FOOD production capacity
     */
    public int getFoodPC() {
        return foodPC;
    }

    /**
     * The ENERGY production capacity is the amount of ENERGY that can be produced on this tile per turn
     * with an ENERGY mule
     * @return ENERGY production capacity
     */
    public int getEnergyPC() {
        return energyPC;
    }

    /**
     * The SMITHORE production capacity is the amount of SMITHORE that can be produced on this tile per turn
     * with a SMITHORE mule
     * @return SMITHORE production capacity
     */
    public int getSmithorePC() {
        return smithorePC;
    }

    public String getImagePath() {
        return imagePath;
    }
}
