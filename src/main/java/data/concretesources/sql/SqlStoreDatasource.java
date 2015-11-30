package data.concretesources.sql;

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

    private data.concretesources.StoreRecord record;

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
        List<data.concretesources.StoreRecord> list = query.list();
        if (list == null || list.size() < 1) {
            record = new data.concretesources.StoreRecord();
        } else {
            record = list.get(0);
        }
        session.close();
    }

    @Override
    public final void saveAmount(int energy, int food, int smithore, int crystite) {
        record.setEnergy(energy);
        record.setFood(food);
        record.setSmithore(smithore);
        record.setCrystite(crystite);

        persist();
    }

    @Override
    public final void savePrice(int energyPrice, int foodPrice, int smithorePrice, int crystitePrice) {
        record.setEnergyPrice(energyPrice);
        record.setFoodPrice(foodPrice);
        record.setSmithorePrice(smithorePrice);
        record.setCrystitePrice(crystitePrice);

        persist();
    }

    @Override
    public final int getEnergy() {
        populateRecord();

        return record.getEnergy();
    }

    @Override
    public final int getFood() {
        populateRecord();

        return record.getFood();
    }

    @Override
    public final int getSmithore() {
        populateRecord();

        return record.getSmithore();
    }

    @Override
    public final int getCrystite() {
        populateRecord();

        return record.getCrystite();
    }

    @Override
    public final int getEnergyPrice() {
        populateRecord();

        return record.getEnergyPrice();
    }

    @Override
    public final int getFoodPrice() {
        populateRecord();

        return record.getFoodPrice();
    }

    @Override
    public final int getSmithorePrice() {
        populateRecord();

        return record.getSmithorePrice();
    }

    @Override
    public final int getCrystitePrice() {
        populateRecord();

        return record.getCrystitePrice();
    }

    @Override
    public final int getMuleCount() {
        populateRecord();

        return record.getMuleCount();
    }

    @Override
    public final void setMuleCount(int muleCount) {
        record.setMuleCount(muleCount);

        persist();
    }

}