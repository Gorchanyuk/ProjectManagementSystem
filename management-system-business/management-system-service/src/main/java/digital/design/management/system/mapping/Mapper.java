package digital.design.management.system.mapping;

public interface Mapper<T, R1, R2>{

    T dtoToEntity(R1 dto);

    T dtoToEntity(R1 dto, T entity);

    R2 entityToOutDto(T entity);
}
