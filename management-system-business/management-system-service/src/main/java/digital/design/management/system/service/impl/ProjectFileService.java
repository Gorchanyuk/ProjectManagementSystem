package digital.design.management.system.service.impl;

import digital.design.management.system.dto.file.FileDTO;
import digital.design.management.system.entity.Project;
import digital.design.management.system.entity.ProjectFile;
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
public class ProjectFileService implements StorageService{

    @Value("${project.files.dir}")
    private String dir;

    private final ProjectFileRepository projectFileRepository;
    private final ProjectService projectService;
    private final FileService fileService;

    public FileDTO fileUpload(MultipartFile file, UUID uid) {

        String filename = fileService.saveNewFile(file, dir);
        Project project = projectService.findByUid(uid);
        ProjectFile projectFile = ProjectFile.builder()
                .projectId(project)
                .filename(filename)
                .build();
        projectFileRepository.save(projectFile);
        log.info("Project file: {} upload and save", filename);
        return FileDTO.builder().fileName(filename).build();
    }



    @Override
    public List<FileDTO> getAllFiles(UUID uid) {
        log.info("All files by project with uid:{} found", uid);
        return projectFileRepository.findAllByProjectId_Uid(uid).stream()
                .map(file-> FileDTO.builder()
                        .fileName(file.getFilename())
                        .build())
                .toList();
    }

    @Override
    public FileDTO fileReplace(String filename, MultipartFile file) {
        fileService.save(file, dir, filename);
        log.info("Project file: {} replace", filename);
        return FileDTO.builder().fileName(filename).build();
    }

    @Override
    public FileDTO deleteFile(String filename) {
        fileService.deleteFile(dir, filename);
        projectFileRepository.deleteByFilename(filename);
        log.info("Project file: {} delete", filename);
        return FileDTO.builder().fileName(filename).build();
    }

    @Override
    public Resource downloadFile(String filename) {
        Resource resource = fileService.downloadFile(dir, filename);
        log.info("Project file: {} for download found", filename);
        return resource;
    }
}
