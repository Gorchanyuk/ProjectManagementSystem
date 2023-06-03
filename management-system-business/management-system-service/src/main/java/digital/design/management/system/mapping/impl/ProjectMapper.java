package digital.design.management.system.mapping.impl;

import digital.design.management.system.dto.project.ProjectDTO;
import digital.design.management.system.dto.project.ProjectOutDTO;
import digital.design.management.system.entity.Project;
import digital.design.management.system.mapping.Mapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;


@Component
@Log4j2
public class ProjectMapper implements Mapper<Project, ProjectDTO, ProjectOutDTO> {

    @Override
    public Project dtoToEntity(ProjectDTO dto){
        log.debug("Mapping ProjectDTO to Project");
        return Project.builder()
                .code(dto.getCode())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    @Override
    public Project dtoToEntity(ProjectDTO dto, Project project) {
        log.debug("Mapping an ProjectDTO to an Project in a given project");
        project.setCode(dto.getCode());
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        return project;
    }

    @Override
    public ProjectOutDTO entityToOutDto(Project project){
        log.debug("Mapping Project to ProjectOutDTO");
        return ProjectOutDTO.builder()
                .uid(project.getUid())
                .status(project.getStatus())
                .code(project.getCode())
                .name(project.getName())
                .description(project.getDescription())
                .build();
    }

    @Override
    public ProjectDTO entityToDto(Project entity) {
        return ProjectDTO.builder()
                .code(entity.getCode())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}
