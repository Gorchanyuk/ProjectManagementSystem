package digital.design.management.system.service.impl;

import digital.design.management.system.common.exception.StorageFileNotFoundException;
import digital.design.management.system.config.FilesDirProperty;
import digital.design.management.system.dto.file.FileConfirmDTO;
import digital.design.management.system.dto.file.FileDTO;
import digital.design.management.system.dto.file.FileTokenDTO;
import digital.design.management.system.entity.Project;
import digital.design.management.system.entity.ProjectFile;
import digital.design.management.system.mapping.impl.FileDtoMapper;
import digital.design.management.system.repository.ProjectFileRepository;
import digital.design.management.system.service.ProjectService;
import digital.design.management.system.service.StorageService;
import digital.design.management.system.service.FileService;
import digital.design.management.system.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectFileService implements FileService {

    private final FilesDirProperty property;
    private final ProjectFileRepository projectFileRepository;
    private final ProjectService projectService;
    private final StorageService storageService;
    private final FileDtoMapper fileMapper;
    private final FileUtil fileUtil;

    public ProjectFile findByUid(UUID uid) {
        log.debug("Search for the project file with uid: {}", uid);
        ProjectFile file = projectFileRepository.findByUid(uid)
                .orElseThrow(StorageFileNotFoundException::new);
        log.info("Project file with uid: {} found", uid);
        return file;
    }

    @Override
    public FileDTO fileUpload(MultipartFile file, UUID projectUid) {

        try {
            String hashcode = fileUtil.getFileHash(file);
            if (!projectFileRepository.findAllByHashcode(hashcode).isEmpty()) {
                storageService.saveFileInTempDir(file, projectUid, hashcode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UUID fileUid = UUID.randomUUID();
        String filename = storageService.saveNewFile(file, property.getProjectDir(), fileUid);

        String hashcode = fileUtil.getFileHash(file);
        return createFileAndSave(projectUid, fileUid, filename, hashcode);
    }

    @Override
    public FileDTO confirmUploadFile(FileTokenDTO tokenDTO) {

        FileConfirmDTO dto = storageService.moveFile(tokenDTO.getToken(), property.getProjectDir());
        return createFileAndSave(dto.getProjectUid(), dto.getUid(), dto.getFileName(), dto.getHashcode());
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
        storageService.save(file, property.getProjectDir(), filename);
        log.info("Project file: {} replace", filename);
        return fileMapper.getDto(projectFile);
    }

    @Override
    public FileDTO deleteFile(UUID uid) {
        ProjectFile projectFile = findByUid(uid);
        String filename = projectFile.getFilename();
        storageService.deleteFile(property.getProjectDir(), filename);
        projectFileRepository.deleteByFilename(filename);
        log.info("Project file: {} delete", filename);
        return fileMapper.getDto(projectFile);
    }

    @Override
    public Resource downloadFile(UUID uid) {
        ProjectFile projectFile = findByUid(uid);
        String filename = projectFile.getFilename();
        Resource resource = storageService.downloadFile(property.getProjectDir(), filename);
        log.info("Project file: {} for download found", filename);
        return resource;
    }

    private FileDTO createFileAndSave(UUID projectUid, UUID fileUid, String filename, String hashcode) {
        Project project = projectService.findByUid(projectUid);
        ProjectFile projectFile = ProjectFile.builder()
                .projectId(project)
                .uid(fileUid)
                .filename(filename)
                .hashcode(hashcode)
                .build();
        projectFileRepository.save(projectFile);
        log.info("Project file: {} upload and save", filename);
        return fileMapper.getDto(projectFile);
    }
}
