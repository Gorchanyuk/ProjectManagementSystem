package digital.design.management.system.service.impl;

import digital.design.management.system.common.exception.StorageFileNotFoundException;
import digital.design.management.system.config.FilesDirProperty;
import digital.design.management.system.dto.file.FileConfirmDTO;
import digital.design.management.system.dto.file.FileDTO;
import digital.design.management.system.dto.file.FileTokenDTO;
import digital.design.management.system.entity.Task;
import digital.design.management.system.entity.TaskFile;
import digital.design.management.system.mapping.impl.FileDtoMapper;
import digital.design.management.system.repository.TaskFileRepository;
import digital.design.management.system.service.StorageService;
import digital.design.management.system.service.FileService;
import digital.design.management.system.service.TaskService;
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
public class TaskFileService implements FileService {

    private final FilesDirProperty property;
    private final TaskFileRepository taskFileRepository;
    private final TaskService taskService;
    private final StorageService storageService;
    private final FileDtoMapper fileMapper;
    private final FileUtil fileUtil;

    public TaskFile findByUid(UUID uid) {
        log.debug("Search for the task file with uid: {}", uid);
        TaskFile file = taskFileRepository.findByUid(uid)
                .orElseThrow(StorageFileNotFoundException::new);
        log.info("Task file with uid: {} found", uid);
        return file;
    }

    @Override
    public FileDTO fileUpload(MultipartFile file, UUID taskUid) {

        try {
            String hashcode = fileUtil.getFileHash(file);
            if (!taskFileRepository.findAllByHashcode(hashcode).isEmpty()) {
                storageService.saveFileInTempDir(file, taskUid, hashcode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UUID fileUid = UUID.randomUUID();
        String filename = storageService.saveNewFile(file, property.getTaskDir(), fileUid);

        String hashcode = fileUtil.getFileHash(file);
        return createFileAndSave(taskUid, fileUid, filename, hashcode);
    }

    @Override
    public List<FileDTO> getAllFiles(UUID taskUid) {
        log.info("All files by task with uid:{} found", taskUid);
        return taskFileRepository.findAllByTaskId_Uid(taskUid).stream()
                .map(file -> FileDTO.builder()
                        .uid(file.getUid())
                        .fileName(file.getFilename())
                        .build())
                .toList();
    }

    @Override
    public FileDTO fileReplace(UUID uid, MultipartFile file) {
        TaskFile taskFile = findByUid(uid);
        String filename = taskFile.getFilename();
        storageService.save(file, property.getTaskDir(), filename);
        log.info("Task file: {} replace", filename);
        return fileMapper.getDto(taskFile);
    }

    @Override
    public FileDTO deleteFile(UUID uid) {
        TaskFile taskFile = findByUid(uid);
        String filename = taskFile.getFilename();
        storageService.deleteFile(property.getTaskDir(), filename);
        taskFileRepository.deleteByFilename(filename);
        log.info("Task file: {} delete", filename);
        return fileMapper.getDto(taskFile);
    }

    @Override
    public FileDTO confirmUploadFile(FileTokenDTO tokenDTO) {

        FileConfirmDTO dto = storageService.moveFile(tokenDTO.getToken(), property.getTaskDir());
        return createFileAndSave(dto.getProjectUid(), dto.getUid(), dto.getFileName(), dto.getHashcode());
    }

    @Override
    public Resource downloadFile(UUID uid) {
        TaskFile taskFile = findByUid(uid);
        String filename = taskFile.getFilename();
        Resource resource = storageService.downloadFile(property.getTaskDir(), filename);
        log.info("Task file: {} for download found", filename);
        return resource;
    }

    private FileDTO createFileAndSave(UUID taskUid, UUID fileUid, String filename, String hashcode) {
        Task task = taskService.findByUid(taskUid);
        TaskFile taskFile = TaskFile.builder()
                .taskId(task)
                .uid(fileUid)
                .filename(filename)
                .hashcode(hashcode)
                .build();
        taskFileRepository.save(taskFile);
        log.info("Project file: {} upload and save", filename);
        return fileMapper.getDto(taskFile);
    }
}
