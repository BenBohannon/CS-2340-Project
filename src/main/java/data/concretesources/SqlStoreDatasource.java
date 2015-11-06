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

    StoreRecord record;

    @Inject
    public SqlStoreDatasource(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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
        record.smithore = StoreDatasource.smithore;
        record.food = StoreDatasource.food;
        record.energy = StoreDatasource.energy;
        record.crystite = StoreDatasource.crystite;
        session.close();
    }

    @Override
    public void saveAmount(int energy, int food, int smithore, int crystite) {
        record.energy = energy;
        record.food = food;
        record.smithore = smithore;
        record.crystite = crystite;

        persist();
    }

    @Override
    public void savePrice(int energyPrice, int foodPrice, int smithorePrice, int crystitePrice) {
        record.energyPrice = energyPrice;
        record.foodPrice = foodPrice;
        record.smithorePrice = smithorePrice;
        record.crystitePrice = crystitePrice;

        persist();
    }

    @Override
    public int getEnergy() {
        populateRecord();

        return record.energy;
    }

    @Override
    public int getFood() {
        populateRecord();

        return record.food;
    }

    @Override
    public int getSmithore() {
        populateRecord();

        return record.smithore;
    }

    @Override
    public int getCrystite() {
        populateRecord();

        return record.crystite;
    }

    @Override
    public int getEnergyPrice() {
        populateRecord();

        return record.energyPrice;
    }

    @Override
    public int getFoodPrice() {
        populateRecord();

        return record.foodPrice;
    }

    @Override
    public int getSmithorePrice() {
        populateRecord();

        return record.smithorePrice;
    }

    @Override
    public int getCrystitePrice() {
        populateRecord();

        return record.crystitePrice;
    }

    @Override
    public int getMuleCount() {
        populateRecord();

        return record.muleCount;
    }

    @Override
    public void setMuleCount(int muleCount) {
        record.muleCount = muleCount;

        persist();
    }

}