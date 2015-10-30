package data.concretesources;

import com.google.inject.Inject;
import data.abstractsources.LocationDatasource;
import data.abstractsources.Repository;
import model.entity.Mule;
import model.map.Locatable;
import model.map.PersistableLocatable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collection;
import java.util.List;

/**
 * Created by brian on 10/29/15.
 */
public class SqlLocationDatasource implements LocationDatasource {

    @Inject
    SessionFactory sessionFactory;

    List<PersistableLocatable> records;

    public SqlLocationDatasource() {

    }

    private void populateRecords() {
        Session session = sessionFactory.openSession();
    }

    @Override
    public Collection<Locatable> get(int row, int col) {
        return null;
    }

    @Override
    public void save(int row, int col, Locatable locatable) {

    }

    @Override
    public void saveAll(int row, int col, Collection<Locatable> locatables) {

    }
}
