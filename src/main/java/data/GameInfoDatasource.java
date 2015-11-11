package data;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;

/**
 * Created by brian on 9/23/15.
 */
public class GameInfoDataSource {

    /**
     * saves game
     */
    public GameInfoDataSource() {

    }

    /**
     * saves game
     * @throws Exception manual SessionFactory deletion
     */
    protected void setUp() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                .configure(new File(getClass().getResource(
                        "/sql/hibernate.cfg.xml").getFile()))
                        // configures settings from hibernate.cfg.xml
                .build();
        try {
            SessionFactory sessionFactory = new MetadataSources(
                    registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the
            // SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    private int round;

    /**
     * gets max num of rounds
     * @return num of rounds
     */
    public int getMaxRounds() {
        return 12;
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
     * @param round round number
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * get food requirement
     * @param round current round
     * @return food requirement
     */
    public int getFoodRequirement(int round) {
        return (round / 4) + 3;
    }

    public static int BTU(int numBTU) {
        return numBTU * 15;
    }
}