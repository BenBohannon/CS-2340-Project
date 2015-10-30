package data.concretesources;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by brian on 10/29/15.
 */
@Entity
public class StoreRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
