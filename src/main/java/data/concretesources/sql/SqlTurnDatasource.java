package data.concretesources.sql;

import com.google.inject.Inject;
import data.abstractsources.TurnDatasource;
import model.entity.*;
import model.service.*;
import org.hibernate.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by brian on 10/29/15.
 */
public class SqlTurnDatasource implements TurnDatasource {

    private SessionFactory sessionFactory;

    private GameSaveMetaHolderService gameSaveMetaHolder;

    private data.concretesources.TurnRecord record;

    @Inject
    public SqlTurnDatasource(SessionFactory pSessionFactory,
                             GameSaveMetaHolderService pGameSaveMetaHolder) {
        this.sessionFactory = pSessionFactory;
        gameSaveMetaHolder = pGameSaveMetaHolder;
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

        String hqlString = String.format("FROM TurnRecord TR WHERE TR.gameSaveMeta.id = %d",
                gameSaveMetaHolder.getGameSaveMeta().getId());
        org.hibernate.Query query = session.createQuery(hqlString);

        List<data.concretesources.TurnRecord> list = query.list();
        if (list == null || list.size() < 1) {
            record = new data.concretesources.TurnRecord();

            record.setFinishedPlayerIds(new LinkedList<>());
            record.setGameSaveMeta(gameSaveMetaHolder.getGameSaveMeta());

        } else {
            record = list.get(0);
        }
        session.close();
    }

    @Override
    public final void saveRound(int round) {
        if (record == null) {
            populateRecord();
        }

        record.setRound(round);

        persist();
    }

    @Override
    public final void saveFinishedPlayerIds(List<Integer> finishedPlayerIds) {
        if (record == null) {
            populateRecord();
        }

        record.setFinishedPlayerIds(finishedPlayerIds);

        persist();
    }

    @Override
    public final int getRound() {
        populateRecord();

        return record.getRound();
    }

    @Override
    public final List<Integer> getFinishedPlayerIds() {
        populateRecord();

        return record.getFinishedPlayerIds();
    }

}
