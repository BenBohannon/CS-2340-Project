package data.concretesources;

import com.google.inject.Inject;
import data.abstractsources.LocationDatasource;
import model.map.Locatable;
import model.map.PersistableLocatable;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by brian on 10/29/15.
 */
public class SqlLocationDatasource implements LocationDatasource {

    @Inject
    SessionFactory sessionFactory;

    Set<PersistableLocatable> records;

    public SqlLocationDatasource() {
        populateRecords();
    }

    private void populateRecords() {
        if (records == null) {
            records = new HashSet<>();
        }

        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM PersistableLocatable");

        records.clear();
        if (query.list() != null) {
            records.addAll(query.list());
        }
        session.close();
    }

    private void persist() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        if (records != null) {
            for (PersistableLocatable entity : records) {
                session.merge(entity);
            }
        }

        session.getTransaction().commit();
        session.flush();
        session.close();
    }

    @Override
    public Collection<Locatable> get(int row, int col) {
        populateRecords();

        return records.stream()
                .filter(r -> r.getLocation().getCol() == col && r.getLocation().getRow() == row)
                .collect(Collectors.toSet());
    }

    @Override
    public void save(int row, int col, Locatable locatable) {
        PersistableLocatable persistableLocatable = null;
        if (locatable != null && locatable instanceof PersistableLocatable) {
            persistableLocatable = (PersistableLocatable) locatable;
        } else {
            throw new IllegalArgumentException("locatable is null or is not of type PersistableLocatable");
        }

        if (records.contains(persistableLocatable)) {
            records.remove(persistableLocatable);
        }

        records.add(persistableLocatable);

        persist();
    }

    @Override
    public void saveAll(int row, int col, Collection<Locatable> locatables) {
        Set<PersistableLocatable> additions = locatables.stream()
                .map(l -> (PersistableLocatable) l)
                .collect(Collectors.toSet());

        for (PersistableLocatable ele : additions) {
            if (records.contains(ele)) {
                records.remove(ele);
            }

            records.add(ele);
        }

        persist();
    }
}