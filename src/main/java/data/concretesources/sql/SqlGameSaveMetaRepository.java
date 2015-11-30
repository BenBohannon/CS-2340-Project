package data.concretesources.sql;

import com.google.inject.Inject;
import data.abstractsources.Repository;
import model.entity.GameSaveMeta;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by brian on 11/29/15.
 */
public class SqlGameSaveMetaRepository implements Repository<GameSaveMeta> {

    private SessionFactory sessionFactory;

    private Set<GameSaveMeta> records;

    @Inject
    public SqlGameSaveMetaRepository(SessionFactory pSessionFactory) {
        sessionFactory = pSessionFactory;
        populateRecords();
    }

    private void populateRecords() {
        if (records == null) {
            records = new LinkedHashSet<>(); // using Linked because order matters
        }

        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM GameSaveMeta");

        records.clear();
        if (query.list() != null) {
            records.addAll((List<GameSaveMeta>) query.list().stream()
                    .sorted(SqlGameSaveMetaRepository::compareGameSavesByLastPlayed)
                    .collect(Collectors.toList()));
        }
        session.close();
    }

    private void persist() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        if (records != null) {
            for (GameSaveMeta entity : records) {
                session.merge(entity);
            }
        }

        session.getTransaction().commit();
        session.flush();
        session.close();
    }

    public static int compareGameSavesByLastPlayed(Object o1, Object o2) {
        if (!(o1 instanceof GameSaveMeta && o2 instanceof GameSaveMeta)) {
            throw new IllegalArgumentException();
        }
        GameSaveMeta g1 = (GameSaveMeta) o1;
        GameSaveMeta g2 = (GameSaveMeta) o2;
        if (g1.getLastPlayed().getTime() > g2.getLastPlayed().getTime()) {
            return 1;
        } else if (g1.getLastPlayed().getTime() < g2.getLastPlayed().getTime()) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public Set<GameSaveMeta> getAll() {
        populateRecords();
        return records;
    }

    @Override
    public GameSaveMeta get(Object id) {
        populateRecords();

        if (!(id instanceof Integer)) {
            throw new IllegalArgumentException("id null or not int");
        }

        int targetId = (Integer) id;

        Optional<GameSaveMeta> gameSave = records.stream()
                .filter(gs -> gs.getId() == targetId)
                .findFirst();
        return gameSave.isPresent() ? gameSave.get() : null;
    }

    @Override
    public GameSaveMeta save(GameSaveMeta entity) {
        populateRecords();
        if (records.stream()
                .anyMatch(gameSave -> gameSave.getId() == entity.getId())) {
            records.remove(entity);
        }

        records.add(entity);
        persist();

        return null;
    }

    @Override
    public GameSaveMeta delete(Object id) {
        populateRecords();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        GameSaveMeta g = get(id);
        if (g != null) {
            records.remove(g);
        }

        session.delete(g);

        session.getTransaction().commit();
        session.flush();
        session.close();

        populateRecords();

        return g;
    }

    @Override
    public int size() {
        populateRecords();
        return records.size();
    }
}
