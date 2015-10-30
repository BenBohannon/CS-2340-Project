package data.concretesources;

import javax.persistence.*;
import java.util.List;

/**
 * Created by brian on 10/29/15.
 */
@Entity
public class TurnRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int round;
    @ElementCollection
    List<Integer> finishedPlayerIds;
}
