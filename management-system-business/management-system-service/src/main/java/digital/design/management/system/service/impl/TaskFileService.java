package digital.design.management.system.service.impl;

import digital.design.management.system.common.exception.StorageFileNotFoundException;
import digital.design.management.system.dto.file.FileDTO;
import digital.design.management.system.entity.Task;
import digital.design.management.system.entity.TaskFile;
import digital.design.management.system.mapping.impl.FileDtoMapper;
import digital.design.management.system.repository.TaskFileRepository;
import digital.design.management.system.service.FileService;
import digital.design.management.system.service.StorageService;
import digital.design.management.system.service.TaskService;
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
public class TaskFileService implements StorageService {

    @Value("${task.files.dir}")
    private String dir;

    private final TaskFileRepository taskFileRepository;
    private final TaskService taskService;
    private final FileService fileService;
    private final FileDtoMapper fileMapper;

    public TaskFile findByUid(UUID uid){
        log.debug("Search for the task file with uid: {}", uid);
        TaskFile file = taskFileRepository.findByUid(uid)
                .orElseThrow(StorageFileNotFoundException::new);
        log.info("Task file with uid: {} found", uid);
        return file;
    }

    @Override
    public FileDTO fileUpload(MultipartFile file, UUID taskUid) {
        UUID fileUid = UUID.randomUUID();
        String filename = fileService.saveNewFile(file, dir, fileUid);
        Task task = taskService.findByUid(taskUid);
        TaskFile taskFile = TaskFile.builder()
                .taskId(task)
                .uid(fileUid)
                .filename(filename)
                .build();
        taskFileRepository.save(taskFile);
        log.info("Task file: {} upload and save", filename);
        return fileMapper.getDto(taskFile);
    }

    @Override
    public List<FileDTO> getAllFiles(UUID taskUid) {
        log.info("All files by task with uid:{} found", taskUid);
        return taskFileRepository.findAllByTaskId_Uid(taskUid).stream()
                .map(file-> FileDTO.builder()
                        .uid(file.getUid())
                        .fileName(file.getFilename())
                        .build())
                .toList();
    }

    @Override
    public FileDTO fileReplace(UUID uid, MultipartFile file) {
        TaskFile taskFile = findByUid(uid);
        String filename = taskFile.getFilename();
        fileService.save(file, dir, filename);
        log.info("Task file: {} replace", filename);
        return fileMapper.getDto(taskFile);
    }

    @Override
    public FileDTO deleteFile(UUID uid) {
        TaskFile taskFile = findByUid(uid);
        String filename = taskFile.getFilename();
        fileService.deleteFile(dir, filename);
        taskFileRepository.deleteByFilename(filename);
        log.info("Task file: {} delete", filename);
        return fileMapper.getDto(taskFile);
    }

    @Override
    public Resource downloadFile(UUID uid) {
        TaskFile taskFile = findByUid(uid);
        String filename = taskFile.getFilename();
        Resource resource = fileService.downloadFile(dir, filename);
        log.info("Task file: {} for download found", filename);
        return resource;
    }
}
