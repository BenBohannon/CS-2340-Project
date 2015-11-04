package data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;

/**
 * Created by brian on 9/23/15.
 */
public class GameInfoDatasource {
    SessionFactory sessionFactory;
    public GameInfoDatasource() {

    }
    protected void setUp() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(new File(getClass().getResource("/sql/hibernate.cfg.xml").getFile())) // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    private int round;

    public int getMaxRounds() {
        return 12;
    }

    public int getRound() {
        return round;
    }

    public void setRound() {
        this.round = round;
    }

    public int getFoodRequirement(int round) {
        return (round / 4) + 3;
    }

    public static int BTU(int numBTU) {
        return numBTU * 15;
    }
}