package data;

import com.google.inject.Inject;
import javafx.scene.paint.Color;
import model.entity.Mule;
import model.entity.Player;
import model.entity.PlayerRace;
import model.map.Tile;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Table;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by brian on 10/26/15.
 */
public class SqlPlayerRepository extends SqlRepository<Player> {

    @Inject
    SqlRepository<Mule> muleSqlRepository;

    @Override
    public List<Player> getAll() {
        return null;
    }

    @Override
    public Player get(Object id) {
        return null;
    }

    @Override
    public Player save(Player entity) {
        return null;
    }

    @Override
    public Player delete(Object id) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    List<Player> getAllRelatedTo(int foreignKey) {
        return null;
    }

    @Entity
    class PlayerRecord {
        @Id
        int id;
        String name;
        int score;
        int smithore;
        int crystite;
        int food;
        int energy;
        int money;
        @Enumerated
        PlayerRace race;
        int color;

        int gameInfoId;

        //ArrayList<Tile> ownedProperties = new ArrayList<>();

        //Collection<Mule> mules;
    }
}
