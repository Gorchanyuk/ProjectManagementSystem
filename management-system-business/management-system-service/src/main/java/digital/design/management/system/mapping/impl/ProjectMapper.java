package digital.design.management.system.mapping.impl;

import digital.design.management.system.dto.project.ProjectDTO;
import digital.design.management.system.dto.project.ProjectOutDTO;
import digital.design.management.system.entity.Project;
import digital.design.management.system.mapping.Mapper;
import org.springframework.stereotype.Component;


@Component
public class ProjectMapper implements Mapper<Project, ProjectDTO, ProjectOutDTO> {

    @Override
    public Project dtoToEntity(ProjectDTO dto){
        return Project.builder()
                .code(dto.getCode())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    @Override
    public Project dtoToEntity(ProjectDTO dto, Project project) {
        project.setCode(dto.getCode());
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        return project;
    }

    @Override
    public ProjectOutDTO entityToOutDto(Project project){
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
