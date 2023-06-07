package digital.design.management.system.repository;

import digital.design.management.system.entity.TaskFile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskFileRepository extends JpaRepository<TaskFile, Long> {

    Optional<TaskFile> findByUid(UUID uid);

    @EntityGraph(value = "task_file", type = EntityGraph.EntityGraphType.LOAD)
    List<TaskFile> findAllByTaskId_Uid(UUID taskUid);

    @Modifying
    @Transactional
    @EntityGraph(value = "task_file", type = EntityGraph.EntityGraphType.LOAD)
    void deleteByFilename(String filename);
}
