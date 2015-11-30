package data.concretesources;

import com.google.inject.Inject;
import data.abstractsources.Repository;
import model.entity.GameSaveMetaData;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by brian on 11/29/15.
 */
public class SqlGameSaveMetaDataRepository implements Repository<GameSaveMetaData> {

    private SessionFactory sessionFactory;

    private Set<GameSaveMetaData> records;

    @Inject
    public SqlGameSaveMetaDataRepository(SessionFactory pSessionFactory) {
        sessionFactory = pSessionFactory;
        populateRecords();
    }

    private void populateRecords() {
        if (records == null) {
            records = new LinkedHashSet<>();
        }

        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM GameSaveMetaData");

        records.clear();
        if (query.list() != null) {
            records.addAll((List<GameSaveMetaData>) query.list().stream()
                    .sorted(SqlGameSaveMetaDataRepository::compareGameSavesByLastPlayed)
                    .collect(Collectors.toList()));
        }
        session.close();
    }

    private void persist() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        if (records != null) {
            for (GameSaveMetaData entity : records) {
                session.merge(entity);
            }
        }

        session.getTransaction().commit();
        session.flush();
        session.close();
    }

    public static int compareGameSavesByLastPlayed(Object o1, Object o2) {
        if (!(o1 instanceof GameSaveMetaData && o2 instanceof GameSaveMetaData)) {
            throw new IllegalArgumentException();
        }
        GameSaveMetaData g1 = (GameSaveMetaData) o1;
        GameSaveMetaData g2 = (GameSaveMetaData) o2;
        if (g1.getLastPlayed().getTime() > g2.getLastPlayed().getTime()) {
            return 1;
        } else if (g1.getLastPlayed().getTime() < g2.getLastPlayed().getTime()) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public Set<GameSaveMetaData> getAll() {
        populateRecords();
        return records;
    }

    @Override
    public GameSaveMetaData get(Object id) {
        populateRecords();

        if (!(id instanceof Integer)) {
            throw new IllegalArgumentException("id null or not int");
        }

        int targetId = (Integer) id;

        Optional<GameSaveMetaData> gameSave = records.stream()
                .filter(gs -> gs.getId() == targetId)
                .findFirst();
        return gameSave.isPresent() ? gameSave.get() : null;
    }

    @Override
    public GameSaveMetaData save(GameSaveMetaData entity) {
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
    public GameSaveMetaData delete(Object id) {
        populateRecords();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        GameSaveMetaData g = get(id);
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
