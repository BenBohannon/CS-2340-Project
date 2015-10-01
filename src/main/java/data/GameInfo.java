package data;

/**
 * Created by brian on 9/23/15.
 */
public class GameInfo {

    public static int getMaxRounds() {
        return 12;
    }

    public static int getFoodRequirement(int round) {
        return (round / 4) + 3;
    }

    public static int BTU(int numBTU) {
        return numBTU * 15;
    }
}