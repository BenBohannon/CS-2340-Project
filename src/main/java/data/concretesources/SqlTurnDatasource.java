package data.concretesources;

import com.google.inject.Inject;
import data.abstractsources.TurnDatasource;
import org.hibernate.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by brian on 10/29/15.
 */
public class SqlTurnDatasource implements TurnDatasource {

    private SessionFactory sessionFactory;

    private TurnRecord record;

    @Inject
    public SqlTurnDatasource(SessionFactory pSessionFactory) {
        this.sessionFactory = pSessionFactory;
        populateRecord();
    }

    private void persist() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.merge(record);

        session.getTransaction().commit();
        session.flush();
        session.close();
    }

    private void populateRecord() {
        Session session = sessionFactory.openSession();
        org.hibernate.Query query = session.createQuery("FROM TurnRecord");
        List<TurnRecord> list = query.list();
        if (list == null || list.size() < 1) {
            record = new TurnRecord();
            record.finishedPlayerIds = new LinkedList<>();
        } else {
            record = list.get(0);
        }
        session.close();
    }

    @Override
    public final void saveRound(int round) {
        record.round = round;

        persist();
    }

    @Override
    public final void saveFinishedPlayerIds(List<Integer> finishedPlayerIds) {
        record.finishedPlayerIds = finishedPlayerIds;

        persist();
    }

    @Override
    public final int getRound() {
        populateRecord();

        return record.round;
    }

    @Override
    public final List<Integer> getFinishedPlayerIds() {
        populateRecord();

        return record.finishedPlayerIds;
    }

}
