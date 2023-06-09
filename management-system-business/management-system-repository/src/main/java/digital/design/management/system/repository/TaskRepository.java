package digital.design.management.system.repository;

import digital.design.management.system.entity.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    @EntityGraph(value = "task", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Task> findByUid(UUID uid);


    @Override
    @EntityGraph(value = "task", type = EntityGraph.EntityGraphType.LOAD)
    List<Task> findAll(Specification<Task> specification, Sort sort);

    List<Task> findAllByTaskParent(Task task);


}
