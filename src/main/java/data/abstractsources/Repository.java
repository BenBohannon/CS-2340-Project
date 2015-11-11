package data.abstractsources;

import java.util.Set;

/**
 * Created by brian on 9/17/15.
 */
public interface Repository <T> {
    Set<T> getAll();
    T get(Object id);
    T save(T entity);
    T delete(Object id);
    int size();
}
