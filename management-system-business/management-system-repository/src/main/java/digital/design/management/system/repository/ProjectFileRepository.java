package digital.design.management.system.repository;

import digital.design.management.system.entity.ProjectFile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectFileRepository extends JpaRepository<ProjectFile, Long> {

    Optional<ProjectFile> findByUid(UUID uid);

    @EntityGraph(value = "project_file", type = EntityGraph.EntityGraphType.LOAD)
    List<ProjectFile> findAllByProjectId_Uid(UUID projectUid);

    @Modifying
    @Transactional
    @EntityGraph(value = "project_file", type = EntityGraph.EntityGraphType.LOAD)
    void deleteByFilename(String filename);

    List<ProjectFile> findAllByHashcode(String hashcode);
}
