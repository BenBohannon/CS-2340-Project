package data.abstractsources;

import model.entity.Player;

import java.util.Collection;
import java.util.List;

/**
 * Created by brian on 10/28/15.
 */
public interface TurnDatasource {
    void saveRound(int round);
    void saveFinishedPlayerIds(List<Integer> finishedPlayerIds);
    int getRound();
    List<Integer> getFinishedPlayerIds();
}
