package data.abstractsources;

import model.map.Locatable;

import java.util.Collection;

/**
 * Created by brian on 9/13/15.
 */
public interface LocationDatasource {
    Collection<Locatable> get(int row, int col);
    void save(int row, int col, Locatable locatable);
    void saveAll(int row, int col, Collection<Locatable> locatables);
    void remove(Locatable locatable);
}
