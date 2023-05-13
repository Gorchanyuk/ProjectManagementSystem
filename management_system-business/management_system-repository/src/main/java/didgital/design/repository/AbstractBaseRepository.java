package didgital.design.repository;

import didgital.design.entity.Entity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public abstract class AbstractBaseRepository<T extends Entity> implements BaseRepository<T>{

    protected  final Path path;
    protected final AtomicLong idGenerator;

    public AbstractBaseRepository(String path){
        this.path = Paths.get(path);
        List<T> entities = getAll();
        if (entities.size() == 0)
            idGenerator = new AtomicLong(0);
        else {
            long currentEndId = entities.get(entities.size() - 1).getId();
            idGenerator = new AtomicLong(currentEndId);
        }
    }

    @Override
    public T create(T entity) {
        entity.setId(idGenerator.incrementAndGet());
        List<T> entities = getAll();

        if (entities == null) {
            entities = new ArrayList<>();
        }
        entities.add(entity);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            outputStream.writeObject(entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public T update(Long id, T entityToUpdate) {
        List<T> entities = getAll();
        entityToUpdate.setId(id);

        for (int i = 0; i < entities.size(); i++) {
            if (id.equals(entities.get(i).getId())) {
                entities.set(i, entityToUpdate);
            }
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            outputStream.writeObject(entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entityToUpdate;
    }

    @Override
    public Optional<T> getById(Long id) {
        List<T> entities = getAll();

        if (entities == null) {
            return Optional.empty();
        }
        return entities.stream()
                .filter(entity -> id.equals(entity.getId()))
                .findFirst();
    }

    @Override
    public List<T> getAll() {
        List<T> entities = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(path))) {

            entities = (List<T>) inputStream.readObject();
        } catch (NoSuchFileException e) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public void deleteById(Long id) {
        List<T> entities = getAll();

        if (entities == null) {
            return;
        }

        entities = entities.stream()
                .filter(entity -> !id.equals(entity.getId()))
                .collect(Collectors.toList());

        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            outputStream.writeObject(entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
