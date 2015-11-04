import model.entity.MuleType;
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

/**
 * Created by brian on 10/26/15.
 */
public class TestHibernate {


    public static void main(String[] args) {
        try {
            SessionFactory sessionFactory = new TestHibernate().setUp();

            Session session = sessionFactory.openSession();
            session.save(new TestEntity());
            session.flush();
            session.close();

            session = sessionFactory.openSession();
            Query query = session.createQuery("FROM TestEntity");
            System.out.println(query.list().get(0).toString());
        } catch (Exception e) {
            e.printStackTrace();
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
