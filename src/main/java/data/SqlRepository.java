package data;

import model.entity.Mule;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;
import java.util.List;

/**
 * Created by brian on 10/26/15.
 */
public abstract class SqlRepository<T> implements Repository<T> {

    protected SessionFactory sessionFactory;

    public SqlRepository() {
        try {
            setUp();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("couldn't connect to SQL database");
        }
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

    protected List<T> getAllRelatedTo(int foreignKey, String tableName, String colName) {
        Session session = sessionFactory.openSession();
        List<T> list = session.createQuery(String.format("FROM %s x WHERE x.%s = %d", tableName, colName, foreignKey)).list();
        session.close();
        return list;
    }

    abstract List<T> getAllRelatedTo(int foreignKey);
}
