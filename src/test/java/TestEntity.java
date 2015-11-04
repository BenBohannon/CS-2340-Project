import model.entity.MuleType;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 * Created by brian on 10/26/15.
 */
@Entity
public class TestEntity {
        @Id
        int id;
        @Enumerated
        MuleType muleType;

        int playerId;
}
