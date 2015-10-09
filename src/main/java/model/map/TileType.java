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

    TileType(int foodPC, int energyPC, int smithorePC, String imagePath) {
        this.imagePath = imagePath;
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

    public String getImagePath() {
        return imagePath;
    }
}
