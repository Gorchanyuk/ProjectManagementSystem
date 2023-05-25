package digital.design.management.system.repository;


import digital.design.management.system.ProjectTeamId;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.entity.ProjectTeam;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectTeamRepository extends JpaRepository<ProjectTeam, ProjectTeamId> {


    @EntityGraph(value = "project_employee", type = EntityGraph.EntityGraphType.LOAD)
    List<ProjectTeam> findAllByProjectId_Uid(UUID uid);

    @Modifying
    @Transactional
    @EntityGraph(value = "project_employee", type = EntityGraph.EntityGraphType.LOAD)
    void deleteAllByEmployeeId(Employee employee);

    @EntityGraph(value = "project_employee", type = EntityGraph.EntityGraphType.LOAD)
    Optional<ProjectTeam> findByProjectId_UidAndEmployeeId_Uid(UUID projectId, UUID employeeId);

    @Modifying
    @Transactional
    @EntityGraph(value = "project_employee", type = EntityGraph.EntityGraphType.LOAD)
    void deleteByProjectId_UidAndEmployeeId_Uid(UUID projectId, UUID employeeId);
}
