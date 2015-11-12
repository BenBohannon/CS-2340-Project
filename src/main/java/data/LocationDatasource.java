package data;

import model.map.Locatable;

import java.util.Collection;

/**
 * Created by brian on 9/13/15.
 */
public interface LocationDatasource {
    /**
     * get locatable at location
     * @param row row of locatable
     * @param col col of locatable
     * @return locatable
     */
    Collection<Locatable> get(int row, int col);

    /**
     * saves locatable
     * @param row row of object
     * @param col col of object
     * @param locatable locatable object to be saved
     */
    void save(int row, int col, Locatable locatable);

    /**
     * saves all locatables
     * @param row row of locatables
     * @param col col of locatables
     * @param locatables collection of locatables
     */
    void saveAll(int row, int col, Collection<Locatable> locatables);
}
