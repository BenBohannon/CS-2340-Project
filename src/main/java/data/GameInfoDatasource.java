package data;

import com.google.inject.Inject;
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

    public GameInfoDatasource() {

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