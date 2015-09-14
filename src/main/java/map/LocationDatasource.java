package map;

import java.util.Collection;

/**
 * Created by brian on 9/13/15.
 */
public interface LocationDatasource {
    public Collection<Locatable> get(int row, int col);
}
