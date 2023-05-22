package digital.design.management.system.repository;


import digital.design.management.system.ProjectTeamId;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.entity.ProjectTeam;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectTeamRepository extends JpaRepository<ProjectTeam, ProjectTeamId> {


    @EntityGraph(value = "project_employee", type = EntityGraph.EntityGraphType.LOAD)
    List<ProjectTeam> findAllByProjectId_Uid(UUID uid);

    @EntityGraph(value = "project_employee", type = EntityGraph.EntityGraphType.LOAD)
    void deleteAllByEmployeeId(Employee employee);

}
