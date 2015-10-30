package data.concretesources;

import data.abstractsources.SqlRepository;
import model.entity.Mule;
import model.entity.MuleType;
import org.hibernate.Session;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by brian on 10/26/15.
 */
public class SqlMuleRepository extends SqlRepository<Mule> {
    @Override
    public List<Mule> getAll() {
        Session s = sessionFactory.openSession();
        List<MuleRecord> muleRecords = s.createQuery("FROM MuleRecord").list();
        s.close();
        return null;
    }

    @Override
    public Mule get(Object id) {
        return null;
    }

    @Override
    public Mule save(Mule entity) {
        return null;
    }

    @Override
    public Mule delete(Object id) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    private Mule parseMuleRecord(MuleRecord muleRecord) {
        Mule mule  = new Mule(muleRecord.muleType);
        mule.setId(muleRecord.id);
        return null;
    }

    @Entity
    class MuleRecord {
        @Id
        int id;
        @Enumerated
        MuleType muleType;

        int playerId;
    }
}
