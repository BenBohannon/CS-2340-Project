package data.concretesources;


import data.abstractsources.LocationDatasource;
import model.map.Locatable;

import java.util.Collection;

/**
 * Temporary implementation of the LocationDatasource that we can use
 * while testing the application. Not everything is implemented.
 */
public class MemoryLocationDatasource implements LocationDatasource {

    private Collection<Locatable>[][] dataGrid;

    @Override
    public final Collection<Locatable> get(int row, int col) {
        return dataGrid[row][col];
    }

    @Override
    public void save(int row, int col, Locatable locatable) {

    }

    @Override
    public void saveAll(int row, int col, Collection<Locatable> locatables) {

    }

    @Override
    public void remove(Locatable locatable) {

    }
}
