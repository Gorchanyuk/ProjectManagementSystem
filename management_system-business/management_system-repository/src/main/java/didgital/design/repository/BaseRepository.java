package didgital.design.repository;


import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {

    T create(T object);

    T update (Long id, T object);

    Optional<T> getById(Long id);

    List<T> getAll();

    void deleteById(Long id);
}
