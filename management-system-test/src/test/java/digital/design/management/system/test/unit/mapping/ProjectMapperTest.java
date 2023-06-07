package digital.design.management.system.test.unit.mapping;

import digital.design.management.system.common.enumerate.StatusProject;
import digital.design.management.system.dto.project.ProjectDTO;
import digital.design.management.system.dto.project.ProjectOutDTO;
import digital.design.management.system.entity.Project;
import digital.design.management.system.mapping.impl.ProjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ProjectMapperTest {

    private static ProjectMapper mapper;

    @BeforeAll
    public static void init() {
        mapper = new ProjectMapper();
    }

    @Test
    public void shouldReturnNewProject() {
        Project project = mapper.dtoToEntity(getProjectDTO());

        Assertions.assertNotNull(project.getCode());
        Assertions.assertNotNull(project.getName());
        Assertions.assertNotNull(project.getDescription());
    }

    @Test
    public void shouldReturnUpdatedProject() {
        Project project = getProject();
        Project projectUpdated = mapper.dtoToEntity(getProjectDTO(), getProject());

        Assertions.assertNotEquals(project, projectUpdated);
    }

    @Test
    public void shouldReturnProjectOutDto(){
        ProjectOutDTO outDTO = mapper.entityToOutDto(getProject());

        Assertions.assertNotNull(outDTO.getUid());
        Assertions.assertNotNull(outDTO.getStatus());
        Assertions.assertNotNull(outDTO.getCode());
        Assertions.assertNotNull(outDTO.getName());
        Assertions.assertNotNull(outDTO.getDescription());
    }

    @Test
    public void shouldReturnProjectDto(){
        ProjectDTO dto = mapper.entityToDto(getProject());

        Assertions.assertNotNull(dto.getCode());
        Assertions.assertNotNull(dto.getName());
        Assertions.assertNotNull(dto.getDescription());
    }

    private ProjectDTO getProjectDTO() {
        return ProjectDTO.builder()
                .code("someNewCode")
                .name("someNewName")
                .description("someNewDescription")
                .build();
    }

    private Project getProject(){
        return Project.builder()
                .id(1L)
                .uid(UUID.randomUUID())
                .code("code")
                .name("name")
                .description("description")
                .status(StatusProject.DRAFT)
                .build();
    }
}
