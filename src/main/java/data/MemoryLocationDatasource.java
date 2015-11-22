package data;


import model.map.Locatable;

import java.util.Collection;

/**
 * Created by brian on 9/21/15.
 */
public class MemoryLocationDatasource implements LocationDatasource {

    private Collection<Locatable>[][] dataGrid;

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

    /**
     * gets the data grid
     * @return data grid
     */
    public Collection[][] getDataGrid() {
        return dataGrid;
    }
}
