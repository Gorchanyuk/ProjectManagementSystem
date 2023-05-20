package digital.design.management.system.repository;


import digital.design.management.system.enumerate.StatusProject;
import digital.design.management.system.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {

    Optional<Project> findByCode(String code);

    Optional<Project> findByUid(UUID uid);

    @Query("SELECT p FROM Project p WHERE (p.code ILIKE %:keyword% " +
            "OR p.name ILIKE %:keyword%) " +
            "AND p.status in :statuses")
    List<Project> findByKeyWordAndStatus(@Param("keyword") String keyword,
                                         @Param("statuses") List<StatusProject> statuses);
}
