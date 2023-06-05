package digital.design.management.system.service.impl;

import digital.design.management.system.dto.file.FileDTO;
import digital.design.management.system.entity.Task;
import digital.design.management.system.entity.TaskFile;
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
    @Override
    public FileDTO fileUpload(MultipartFile file, UUID uid) {
        String filename = fileService.saveNewFile(file, dir);
        Task task = taskService.findByUid(uid);
        TaskFile taskFile = TaskFile.builder()
                .taskId(task)
                .filename(filename)
                .build();
        taskFileRepository.save(taskFile);
        log.info("Task file: {} upload and save", filename);
        return FileDTO.builder().fileName(filename).build();
    }

    @Override
    public List<FileDTO> getAllFiles(UUID uid) {
        log.info("All files by task with uid:{} found", uid);
        return taskFileRepository.findAllByTaskId_Uid(uid).stream()
                .map(file-> FileDTO.builder()
                        .fileName(file.getFilename())
                        .build())
                .toList();
    }

    @Override
    public FileDTO fileReplace(String filename, MultipartFile file) {
        fileService.save(file, dir, filename);
        log.info("Task file: {} replace", filename);
        return FileDTO.builder().fileName(filename).build();
    }

    @Override
    public FileDTO deleteFile(String filename) {
        fileService.deleteFile(dir, filename);
        taskFileRepository.deleteByFilename(filename);
        log.info("Task file: {} delete", filename);
        return FileDTO.builder().fileName(filename).build();
    }

    @Override
    public Resource downloadFile(String filename) {
        Resource resource = fileService.downloadFile(dir, filename);
        log.info("Task file: {} for download found", filename);
        return resource;
    }
}
