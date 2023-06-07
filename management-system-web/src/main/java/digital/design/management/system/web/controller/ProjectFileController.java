package digital.design.management.system.web.controller;

import digital.design.management.system.dto.file.FileDTO;
import digital.design.management.system.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@Tag(name = "Работа с файлами проекта", description = "Контроллер для управления файлами для проекта")
public class ProjectFileController {

    private final StorageService storageService;

    @Autowired
    public ProjectFileController(@Qualifier("projectFileService") StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping(value = "/project/download_file/{filename}",
            produces = MediaType.ALL_VALUE)
    @Operation(summary = "Возвращает ссылку для скачивания файла")
    public ResponseEntity<Resource> downloadFile(@PathVariable("filename") String filename) {
        log.debug("GET request on .../project/download_file/{}", filename);
        Resource file = storageService.downloadFile(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping(value = "/project/{projectUid}/file",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.ALL_VALUE)
    @Operation(summary = "Сохраняет файл на сервер")
    public ResponseEntity<FileDTO> fileUpload(@PathVariable("projectUid") UUID uid,
                                              @RequestParam("file") MultipartFile file) {
        log.debug("POST request on .../project/{}/file", uid);
        FileDTO fileDTO = storageService.fileUpload(file, uid);

        return ResponseEntity.ok().body(fileDTO);
    }

    @GetMapping(value = "/project/{projectUid}/file",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Находит все все файлы прикрепленные к заданному проекту")
    public List<FileDTO> getAllFiles(@PathVariable("projectUid") UUID uid) {
        log.debug("GET request on .../project/{}/file", uid);
        return storageService.getAllFiles(uid);
    }

    @PutMapping(value = "/project/file/{filename}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.ALL_VALUE)
    @Operation(summary = "Заменяет файл с указанным именем на новый файл, формат должен совпадать")
    public ResponseEntity<FileDTO> replaceFile(@PathVariable("filename") String filename,
                                               @RequestParam("file") MultipartFile file) {
        log.debug("PUT request on .../project/file/{}", filename);
        FileDTO fileDTO = storageService.fileReplace(filename, file);
        return ResponseEntity.ok().body(fileDTO);
    }

    @DeleteMapping(value = "/project/file/{filename}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Удаляет файл")
    public ResponseEntity<FileDTO> deleteFile(@PathVariable("filename") String filename) {

        log.debug("DELETE request on .../project/file/{}", filename);
        FileDTO fileDTO = storageService.deleteFile(filename);
        return ResponseEntity.ok().body(fileDTO);
    }
}