package data.abstractsources;

import model.map.Locatable;

import java.util.Collection;

/**
 * Abstract interface for the Map's datasource, used for loose coupling.
 */
public interface LocationDatasource {
    Collection<Locatable> get(int row, int col);
    void save(int row, int col, Locatable locatable);
    void saveAll(int row, int col, Collection<Locatable> locatables);
    void remove(Locatable locatable);
}
