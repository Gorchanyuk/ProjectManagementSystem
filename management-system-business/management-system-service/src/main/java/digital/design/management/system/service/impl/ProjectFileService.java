package digital.design.management.system.service.impl;

import digital.design.management.system.common.exception.StorageFileNotFoundException;
import digital.design.management.system.dto.file.FileDTO;
import digital.design.management.system.entity.Project;
import digital.design.management.system.entity.ProjectFile;
import digital.design.management.system.mapping.impl.FileDtoMapper;
import digital.design.management.system.repository.ProjectFileRepository;
import digital.design.management.system.service.ProjectService;
import digital.design.management.system.service.FileService;
import digital.design.management.system.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectFileService implements StorageService {

    @Value("${project.files.dir}")
    private String dir;

    private final ProjectFileRepository projectFileRepository;
    private final ProjectService projectService;
    private final FileService fileService;
    private final FileDtoMapper fileMapper;

    public ProjectFile findByUid(UUID uid) {
        log.debug("Search for the project file with uid: {}", uid);
        ProjectFile file = projectFileRepository.findByUid(uid)
                .orElseThrow(StorageFileNotFoundException::new);
        log.info("Project file with uid: {} found", uid);
        return file;
    }

    @Override
    public FileDTO fileUpload(MultipartFile file, UUID projectUid) {
        UUID fileUid = UUID.randomUUID();
        String filename = fileService.saveNewFile(file, dir, fileUid);
        Project project = projectService.findByUid(projectUid);
        ProjectFile projectFile = ProjectFile.builder()
                .projectId(project)
                .uid(fileUid)
                .filename(filename)
                .build();
        projectFileRepository.save(projectFile);
        log.info("Project file: {} upload and save", filename);
        return fileMapper.getDto(projectFile);
    }


    @Override
    public List<FileDTO> getAllFiles(UUID projectUid) {
        log.info("All files by project with uid:{} found", projectUid);
        return projectFileRepository.findAllByProjectId_Uid(projectUid).stream()
                .map(file -> FileDTO.builder()
                        .uid(file.getUid())
                        .fileName(file.getFilename())
                        .build())
                .toList();
    }

    @Override
    public FileDTO fileReplace(UUID uid, MultipartFile file) {
        ProjectFile projectFile = findByUid(uid);
        String filename = projectFile.getFilename();
        fileService.save(file, dir, filename);
        log.info("Project file: {} replace", filename);
        return fileMapper.getDto(projectFile);
    }

    @Override
    public FileDTO deleteFile(UUID uid) {
        ProjectFile projectFile = findByUid(uid);
        String filename = projectFile.getFilename();
        fileService.deleteFile(dir, filename);
        projectFileRepository.deleteByFilename(filename);
        log.info("Project file: {} delete", filename);
        return fileMapper.getDto(projectFile);
    }

    @Override
    public Resource downloadFile(UUID uid) {
        ProjectFile projectFile = findByUid(uid);
        String filename = projectFile.getFilename();
        Resource resource = fileService.downloadFile(dir, filename);
        log.info("Project file: {} for download found", filename);
        return resource;
    }
}
