package digital.design.management.system.repository;


import digital.design.management.system.ProjectTeamId;
import digital.design.management.system.entity.ProjectTeam;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectTeamRepository extends JpaRepository<ProjectTeam, ProjectTeamId> {


    @EntityGraph(value = "project.allEmployee", type = EntityGraph.EntityGraphType.LOAD)
    List<ProjectTeam> findAllByProjectId_Uid( UUID uid);
}
