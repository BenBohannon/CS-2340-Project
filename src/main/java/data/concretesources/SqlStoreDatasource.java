package data.concretesources;

import com.google.inject.Inject;
import data.abstractsources.StoreDatasource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by brian on 10/28/15.
 */
public class SqlStoreDatasource implements StoreDatasource {

    @Inject
    SessionFactory sessionFactory;

    StoreRecord record;

    public SqlStoreDatasource() {
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

    @Entity
    public static class StoreRecord {
        @Id
        @GeneratedValue(strategy= GenerationType.AUTO)
        int id;

        int food;
        int crystite;
        int energy;
        int smithore;
        int energyPrice;
        int foodPrice;
        int crystitePrice;
        int smithorePrice;

        int muleCount;

    }
}