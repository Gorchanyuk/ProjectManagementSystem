package digital.design.management.system.test.util;

import digital.design.management.system.common.enumerate.RoleEmployee;
import digital.design.management.system.common.enumerate.StatusTask;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.entity.Project;
import digital.design.management.system.entity.ProjectTeam;
import digital.design.management.system.entity.Task;
import digital.design.management.system.repository.ProjectTeamRepository;
import digital.design.management.system.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class GenerateTask {

    @Autowired
    private GenerateProject generateProject;

    @Autowired
    private GenerateEmployee generateEmployee;

    @Autowired
    private ProjectTeamRepository projectTeamRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public List<Task> addTasks(int count) {
        List<Task> tasks = new ArrayList<>();

        Employee author = generateEmployee.addEmployees(1).get(0);
        Project project = generateProject.addProjects(1).get(0);
        Employee performer = generateEmployee.addEmployees(1).get(0);
        ProjectTeam projectTeamAuthor = new ProjectTeam(project, author, RoleEmployee.DEVELOPER);
        ProjectTeam projectTeamPerformer = new ProjectTeam(project, performer, RoleEmployee.DEVELOPER);
        projectTeamRepository.saveAll(List.of(projectTeamAuthor, projectTeamPerformer));
        for (int i = 0; i < count; i++) {
            Task task = createTask(project, author, performer);
            tasks.add(task);
        }
        taskRepository.saveAll(tasks);
        return tasks;
    }

    private Task createTask(Project project, Employee author, Employee performer) {
        return Task.builder()
                .name("name")
                .uid(UUID.randomUUID())
                .taskPerformer(performer)
                .executionTime(10)
                .deadline(LocalDate.now().plusDays(2))
                .project(project)
                .author(author)
                .dateOfCreated(LocalDate.now())
                .dateOfUpdate(LocalDate.now())
                .status(StatusTask.NEW)
                .build();
    }
}
