package data;

import map.Locatable;
import map.LocationDatasource;

import java.util.Collection;

/**
 * Created by brian on 9/21/15.
 */
public class MemoryLocationDatasource implements LocationDatasource {

    public Collection<Locatable>[][] dataGrid;

    @Override
    public Collection<Locatable> get(int row, int col) {
        return dataGrid[row][col];
    }

    @Override
    public void save(int row, int col, Locatable locatable) {

    }

    @Override
    public void saveAll(int row, int col, Collection<Locatable> locatables) {

    }
}
