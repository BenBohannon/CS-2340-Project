package data;

/**
 * Created by brian on 9/23/15.
 */
public class GameInfoDatasource {

    private static final int MAX_ROUNDS_DEFAULT = 12;

    private static final int BTU_TO_SECONDS_RATIO = 15;

    public GameInfoDatasource() {

    }

    private int round;

    public final int getMaxRounds() {
        return MAX_ROUNDS_DEFAULT;
    }

    public final int getRound() {
        return round;
    }

    public final void setRound(int pRound) {
        this.round = pRound;
    }

    public static int getBTU(int numBTU) {
        return numBTU * BTU_TO_SECONDS_RATIO;
    }
}