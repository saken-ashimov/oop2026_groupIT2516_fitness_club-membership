package repositories.interfaces;
import java.util.List;

public interface IGenericRepository<T> {
    T getById(int id);
    List<T> getAll();
    boolean add(T entity);
}

