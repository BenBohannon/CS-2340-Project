package data.concretesources;

import com.google.inject.Inject;
import data.abstractsources.TurnDatasource;
import org.hibernate.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by brian on 10/29/15.
 */
public class SqlTurnDatasource implements TurnDatasource {

    @Inject
    SessionFactory sessionFactory;

    private TurnRecord record;

    public SqlTurnDatasource() {
        populateRecord();
    }

    private void persist() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(record);

        session.getTransaction().commit();
        session.flush();
        session.close();
    }

    private void populateRecord() {
        Session session = sessionFactory.openSession();
        org.hibernate.Query query = session.createQuery("FROM StoreRecord");
        List<TurnRecord> list = query.list();
        if (list == null || list.size() < 1) {
            record = new TurnRecord();
        } else {
            record = list.get(0);
        }
        session.close();
    }

    @Override
    public void saveRound(int round) {
        record.round = round;

        persist();
    }

    @Override
    public void saveFinishedPlayerIds(List<Integer> finishedPlayerIds) {
        record.finishedPlayerIds = finishedPlayerIds;

        persist();
    }

    @Override
    public int getRound() {
        populateRecord();

        return record.round;
    }

    @Override
    public List<Integer> getFinishedPlayerIds() {
        populateRecord();

        return record.finishedPlayerIds;
    }

    @Entity
    public static class TurnRecord {
        @Id
        @GeneratedValue(strategy= GenerationType.AUTO)
        int id;

        int round;
        @ElementCollection
        List<Integer> finishedPlayerIds;
    }
}
