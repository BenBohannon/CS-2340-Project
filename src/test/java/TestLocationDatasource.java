import map.Locatable;
import map.LocationDatasource;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by brian on 9/14/15.
 * Simple source for random, test data for the {@link map.Map}
 */
class TestLocationDatasource implements LocationDatasource {

    Collection<Locatable>[][] locationData;

    public TestLocationDatasource() {
        locationData = new Collection[10][10];
        for (int i = 0; i < locationData.length; i++) {
            for (int j = 0; j < locationData[0].length; j++) {
                //create collection for location grid//
                locationData[i][j] = new LinkedList<Locatable>();

                //randomly add data to grid element//
                if (Math.random() > .5f) {
                    locationData[i][j].add(new LocatableA());
                }
                if (Math.random() > .5f) {
                    locationData[i][j].add(new LocatableB());
                }
                if (Math.random() > .5f) {
                    locationData[i][j].add(new LocatableC());
                }
            }
        }
    }

    @Override
    public Collection<Locatable> get(int row, int col) {
        return locationData[row][col];
    }
}
