package data.concretesources;

import com.google.inject.Inject;
import data.abstractsources.StoreDatasource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by brian on 10/28/15.
 */
public class SqlStoreDatasource implements StoreDatasource {

    private SessionFactory sessionFactory;

    private StoreRecord record;

    @Inject
    public SqlStoreDatasource(SessionFactory pSessionFactory) {
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
        Query query = session.createQuery("FROM StoreRecord");
        List<StoreRecord> list = query.list();
        if (list == null || list.size() < 1) {
            record = new StoreRecord();
        } else {
            record = list.get(0);
        }
        session.close();
    }

    @Override
    public final void saveAmount(int energy, int food, int smithore, int crystite) {
        record.energy = energy;
        record.food = food;
        record.smithore = smithore;
        record.crystite = crystite;

        persist();
    }

    @Override
    public final void savePrice(int energyPrice, int foodPrice, int smithorePrice, int crystitePrice) {
        record.energyPrice = energyPrice;
        record.foodPrice = foodPrice;
        record.smithorePrice = smithorePrice;
        record.crystitePrice = crystitePrice;

        persist();
    }

    @Override
    public final int getEnergy() {
        populateRecord();

        return record.energy;
    }

    @Override
    public final int getFood() {
        populateRecord();

        return record.food;
    }

    @Override
    public final int getSmithore() {
        populateRecord();

        return record.smithore;
    }

    @Override
    public final int getCrystite() {
        populateRecord();

        return record.crystite;
    }

    @Override
    public final int getEnergyPrice() {
        populateRecord();

        return record.energyPrice;
    }

    @Override
    public final int getFoodPrice() {
        populateRecord();

        return record.foodPrice;
    }

    @Override
    public final int getSmithorePrice() {
        populateRecord();

        return record.smithorePrice;
    }

    @Override
    public final int getCrystitePrice() {
        populateRecord();

        return record.crystitePrice;
    }

    @Override
    public final int getMuleCount() {
        populateRecord();

        return record.muleCount;
    }

    @Override
    public final void setMuleCount(int muleCount) {
        record.muleCount = muleCount;

        persist();
    }

}