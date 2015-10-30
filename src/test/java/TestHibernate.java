import javafx.scene.paint.Color;
import model.entity.Mule;
import model.entity.MuleType;
import model.entity.Player;
import model.entity.PlayerRace;
import model.map.Map;
import model.map.PersistableLocatable;
import model.map.Tile;
import model.map.TileType;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by brian on 10/26/15.
 */
public class TestHibernate {


    public static void main(String[] args) {
        try {
            Player p1 = new Player();
            p1.setName("P1");
            p1.setId(-1);
            p1.setColor(Color.ALICEBLUE);
            p1.setRace(PlayerRace.Bonzoid);

            Player p2 = new Player();
            p2.setName("P2");
            p2.setId(-1);
            p2.setRace(PlayerRace.Buzzite);
            p2.setColor(Color.BLANCHEDALMOND);

            Mule m1 = new Mule(MuleType.Crysite);
            Mule m2 = new Mule(MuleType.Energy);

            m1.setLocation(new Map.Location());
            m2.setLocation(new Map.Location());

            p1.addMule(m1);
            p2.addMule(m2);




            SessionFactory sessionFactory = new TestHibernate().setUp();

            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.merge(p1);
            session.merge(p2);
            session.getTransaction().commit();
            session.flush();
            session.close();

//            session = sessionFactory.openSession();
//            Query query = session.createQuery("FROM Player");
//            List<Player> playerList = query.list();
//            System.out.println(query.list().size());
//            for (Player p : (List<Player>) query.list()) {
//                System.out.println(p);
//                for (Mule m : p.mules) {
//                    System.out.println(m);
//                }
//            }

            session = sessionFactory.openSession();
            Query locatableQuery = session.createQuery("FROM PersistableLocatable");
            Query muleQuery = session.createQuery("FROM Mule");
            List<Mule> mules  = muleQuery.list();
            System.out.println(String.format("locatables:%d mules:%d", locatableQuery.list().size(), muleQuery.list().size()));

            session.beginTransaction();
            for (Mule m : mules) {
                PersistableLocatable p = new Mule(m.getType());
                p.setLocation(new Map.Location());
                p.setId(m.getId());
                session.merge(p);
            }

            Tile tile = new Tile(TileType.MOUNTAIN_1);
            tile.setLocation(new Map.Location());
            session.merge(tile);

            session.getTransaction().commit();

            System.out.println("new size" + session.createQuery("FROM PersistableLocatable").list().size());

            session.close();
            sessionFactory.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }

    SessionFactory setUp() throws Exception {
        // A SessionFactory is set up once for an application!
        SessionFactory sessionFactory = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(new File(getClass().getResource("/sql/hibernate.cfg.xml").getFile())) // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            e.printStackTrace();
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
        }
        return sessionFactory;
    }
}
