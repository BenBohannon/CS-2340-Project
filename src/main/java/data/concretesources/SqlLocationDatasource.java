package data.concretesources;

import com.google.inject.Inject;
import data.abstractsources.LocationDatasource;
import model.map.Locatable;
import model.map.PersistableLocatable;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by brian on 10/29/15.
 */
public class SqlLocationDatasource implements LocationDatasource {

    private SessionFactory sessionFactory;

    private Set<PersistableLocatable> records;

    @Inject
    public SqlLocationDatasource(SessionFactory pSessionFactory) {
        this.setSessionFactory(pSessionFactory);
        populateRecords();
    }

    private void populateRecords() {
        if (getRecords() == null) {
            setRecords(new HashSet<>());
        }

        Session session = getSessionFactory().openSession();
        Query query = session.createQuery("FROM PersistableLocatable");

        getRecords().clear();
        if (query.list() != null) {
            getRecords().addAll(query.list());
        }
        session.close();
    }

    private void persist() {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();

        if (getRecords() != null) {
            for (PersistableLocatable entity : getRecords()) {
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

        return getRecords().stream()
                .filter(new Predicate<PersistableLocatable>() {
                    @Override
                    public boolean test(PersistableLocatable r) {
                        return r.getLocation().getCol() == col && r.getLocation().getRow() == row;
                    }
                })
                .collect(Collectors.toSet());
    }

    @Override
    public void save(int row, int col, Locatable locatable) {
        PersistableLocatable persistableLocatable = null;
        if (locatable instanceof PersistableLocatable) {
            persistableLocatable = (PersistableLocatable) locatable;
        } else {
            throw new IllegalArgumentException("locatable is null or is not of type PersistableLocatable");
        }

        if (getRecords().contains(persistableLocatable)) {
            getRecords().remove(persistableLocatable);
        }

        getRecords().add(persistableLocatable);

        persist();
    }

    @Override
    public void saveAll(int row, int col, Collection<Locatable> locatables) {
        Set<PersistableLocatable> additions = locatables.stream()
                .map(new Function<Locatable, PersistableLocatable>() {
                    @Override
                    public PersistableLocatable apply(Locatable l) {
                        return (PersistableLocatable) l;
                    }
                })
                .collect(Collectors.toSet());

        for (PersistableLocatable ele : additions) {
            if (getRecords().contains(ele)) {
                getRecords().remove(ele);
            }

            getRecords().add(ele);
        }

        persist();
    }

    @Override
    public void remove(Locatable locatable) {
        if (!(locatable instanceof PersistableLocatable)) {
            throw new IllegalArgumentException("argument must be non-null instance of PersistableLocatable");
        }

        populateRecords();

        if (getRecords().contains(locatable)) {
            Session session = getSessionFactory().openSession();
            session.beginTransaction();

            session.delete(locatable);

            session.getTransaction().commit();
            session.flush();
            session.close();
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory pSessionFactory) {
        this.sessionFactory = pSessionFactory;
    }

    public final Set<PersistableLocatable> getRecords() {
        return records;
    }

    public void setRecords(Set<PersistableLocatable> pRecords) {
        this.records = pRecords;
    }
}
