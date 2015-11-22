package data;

import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;

/**
 * InfoHolder class that serves as a place to define constants and
 * utility methods for the game.
 */
public class GameInfoDatasource {



    private static final int MAX_ROUNDS_DEFAULT = 12;

    private static final int BTU_TO_SECONDS_RATIO = 15;

    /**
     * saves game
     */
    public GameInfoDatasource() {

    }

    /**
     * saves game (this is not right)
     */
    protected void setUp() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                .configure(new File(getClass().getResource(
                        "/sql/hibernate.cfg.xml").getFile()))
                        // configures settings from hibernate.cfg.xml
                .build();
//        try {
//            SessionFactory sessionFactory = new MetadataSources(
//                    registry).buildMetadata().buildSessionFactory();
//        } catch (Exception e) {
            // The registry would be destroyed by the
            // SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
//        }
    }

    private int round;

    /**
     * gets max num of rounds
     * @return num of rounds
     */
    public final int getMaxRounds() {
        return MAX_ROUNDS_DEFAULT;
    }

    /**
     * gets current round
     * @return current round
     */
    public int getRound() {
        return round;
    }

    /**
     * sets current round
     * @param pRound round number
     */
    public void setRound(int pRound) {
        this.round = pRound;
    }

    /**
     * get food requirement
     * @param pRound current round
     * @return food requirement
     */
    public int getFoodRequirement(int pRound) {
        /*
            these actually are "magic numbers" from what I can tell.
            the food requirement is defined as the round divided
            by four, plus three
        */
        return (pRound / 4) + 3;
    }

    public static int getBTU(int numBTU) {
        return numBTU * BTU_TO_SECONDS_RATIO;
    }
}