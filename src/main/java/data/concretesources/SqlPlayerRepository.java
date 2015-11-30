package data.concretesources;

import com.google.inject.Inject;
import data.abstractsources.Repository;
import model.entity.Player;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by brian on 10/26/15.
 */
public class SqlPlayerRepository implements Repository<Player> {

    private SessionFactory sessionFactory;

    private Set<Player> records;

    @Inject
    public SqlPlayerRepository(SessionFactory pSessionFactory) {
        this.sessionFactory = pSessionFactory;
        populateRecords();
    }

    private void populateRecords() {
        if (records == null) {
            records = new HashSet<>();
        }

        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Player");

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
            for (Player p : records) {
                session.merge(p);
            }
        }

        session.getTransaction().commit();
        session.flush();
        session.close();
    }

    @Override
    public Set<Player> getAll() {
        populateRecords();
        return records;
    }

    @Override
    public Player get(Object id) {
        populateRecords();

        if (!(id instanceof Integer)) {
            throw new IllegalArgumentException("id null or not int");
        }

        int targetId = (Integer) id;

        Optional<Player> p = records.stream()
                .filter(player -> player.getId() == targetId)
                .findFirst();
        return p.isPresent() ? p.get() : null;
    }

    @Override
    public Player save(Player entity) {
        populateRecords();
        if (records.stream()
                .anyMatch(player -> player.getId() == entity.getId())) {
            records.remove(entity);
        }

        records.add(entity);
        persist();

        return null;
    }

    @Override
    public Player delete(Object id) {
        populateRecords();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Player p = get(id);
        if (p != null) {
            records.remove(p);
        }

        session.delete(p);

        session.getTransaction().commit();
        session.flush();
        session.close();

        populateRecords();

        return p;
    }

    @Override
    public int size() {
        populateRecords();

        return records.size();
    }
}
