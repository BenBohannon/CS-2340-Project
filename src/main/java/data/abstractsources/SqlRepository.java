package data.abstractsources;

import com.google.inject.Inject;
import data.abstractsources.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
}
