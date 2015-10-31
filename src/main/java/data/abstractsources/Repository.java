package data.abstractsources;

import java.util.List;
import java.util.Set;

/**
 * Created by brian on 9/17/15.
 */
public interface Repository <T> {
    public Set<T> getAll();
    public T get(Object id);
    public T save(T entity);
    public T delete(Object id);
    public int size();
}
