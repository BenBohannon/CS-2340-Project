package data;

import com.google.inject.Inject;
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

    @Inject
    protected SessionFactory sessionFactory;

    protected List<T> getAllRelatedTo(int foreignKey, String tableName, String colName) {
        Session session = sessionFactory.openSession();
        List<T> list = session.createQuery(String.format("FROM %s x WHERE x.%s = %d", tableName, colName, foreignKey)).list();
        session.close();
        return list;
    }

    abstract List<T> getAllRelatedTo(int foreignKey);
}
