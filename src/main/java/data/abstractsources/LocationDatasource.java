package data.abstractsources;

import model.map.Locatable;

import java.util.Collection;

/**
 * Created by brian on 9/13/15.
 */
public interface LocationDatasource {
    public Collection<Locatable> get(int row, int col);
    public void save(int row, int col, Locatable locatable);
    public void saveAll(int row, int col, Collection<Locatable> locatables);
    public void remove(Locatable locatable);
}
