package data.concretesources.sql;

import com.google.inject.Inject;
import data.abstractsources.Repository;
import model.entity.Mule;
import model.service.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by brian on 10/26/15.
 */
public class SqlMuleRepository implements Repository<Mule> {

    private SessionFactory sessionFactory;

    private GameSaveMetaHolderService gameSaveMetaHolderService;

    private Set<Mule> records;

    @Inject
    public SqlMuleRepository(SessionFactory pSessionFactory, GameSaveMetaHolderService pGameSaveMetaHolder) {
        this.sessionFactory = pSessionFactory;
        gameSaveMetaHolderService = pGameSaveMetaHolder;
    }

    private void populateRecords() {
        if (records == null) {
            records = new HashSet<>();
        }

        Session session = sessionFactory.openSession();

        String hqlString = String.format("FROM Mule M WHERE M.gameSaveMeta.id = %d",
                gameSaveMetaHolderService.getGameSaveMeta().getId());
        Query query = session.createQuery(hqlString);

        records.clear();
        if (query != null) {
            records.addAll(query.list());
        }

        session.close();
    }

    private void persist() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        if (records != null) {
            for (Mule m : records) {
                session.merge(m);
            }
        }

        session.getTransaction().commit();
        session.flush();
        session.close();
    }

    @Override
    public Set<Mule> getAll() {
        populateRecords();
        return records;
    }

    @Override
    public Mule get(Object id) {
        populateRecords();

        if (!(id instanceof Integer)) {
            throw new IllegalArgumentException("id null or not int");
        }

        int targetId = (Integer) id;

        Optional<Mule> m = records.stream()
                .filter(new Predicate<Mule>() {
                    @Override
                    public boolean test(Mule mule) {
                        return mule.getId() == targetId;
                    }
                })
                .findFirst();
        return m.isPresent() ? m.get() : null;
    }

    @Override
    public Mule save(Mule entity) {
        populateRecords();

        entity.setGameSaveMeta(gameSaveMetaHolderService.getGameSaveMeta());

        if (records.stream()
                .anyMatch(mule -> mule.getId() == entity.getId())) {
            records.remove(entity);
        }

        records.add(entity);
        persist();

        return get(entity.getId());
    }

    @Override
    public Mule delete(Object id) {
        populateRecords();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Mule m = get(id);
        if (m != null) {
            records.remove(m);
        }

        session.delete(m);

        session.getTransaction().commit();
        session.flush();
        session.close();

        populateRecords();

        return m;
    }

    @Override
    public int size() {
        populateRecords();
        return records.size();
    }
}
