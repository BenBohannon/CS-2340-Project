package data;

import java.util.Collection;

/**
 * Created by brian on 9/17/15.
 */
public interface Repository <T> {
    public Collection<T> getAll();
    public T get(Object id);
    public T update(T entity);
    public T delete(Object id);
}
