package digital.design.management.system.web.controller;

import digital.design.management.system.dto.file.FileDTO;
import digital.design.management.system.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Скачать файл",
            description = "Возвращает ссылку для скачивания файла")
    @GetMapping(value = "/project/download_file/{fileUid}",
            produces = MediaType.ALL_VALUE)
    public ResponseEntity<Resource> downloadFile(@Parameter(description = "uid файла который нужно скачать")
                                                 @PathVariable("fileUid") UUID uid) {
        log.debug("GET request on .../project/download_file/{}", uid);
        Resource file = storageService.downloadFile(uid);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @Operation(summary = "Загрузить файл",
            description = "Загружает файл на сервер и добавляет запись в БД")
    @PostMapping(value = "/project/{projectUid}/file",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.ALL_VALUE)
    public ResponseEntity<FileDTO> fileUpload(@Parameter(description = "uid проекта к которому нужно прикрепить файл")
                                              @PathVariable("projectUid") UUID uid,
                                              @Parameter(description = "Файл")
                                              @RequestParam("file") MultipartFile file) {
        log.debug("POST request on .../project/{}/file", uid);
        FileDTO fileDTO = storageService.fileUpload(file, uid);

        return ResponseEntity.ok().body(fileDTO);
    }

    @Operation(summary = "Получить все файлы проекта",
            description = "Находит все файлы, прикрепленные к заданному проекту")
    @GetMapping(value = "/project/{projectUid}/file",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FileDTO> getAllFiles(@Parameter(description = "uid проекта, файлы которого необходимо найти")
                                     @PathVariable("projectUid") UUID uid) {
        log.debug("GET request on .../project/{}/file", uid);
        return storageService.getAllFiles(uid);
    }

    @Operation(summary = "Заменить файл",
            description = "Заменяет файл с указанным uid на новый файл, формат должен совпадать")
    @PutMapping(value = "/project/file/{fileUid}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.ALL_VALUE)
    public ResponseEntity<FileDTO> replaceFile(@Parameter(description = "uid файла который нужно заменить")
                                               @PathVariable("fileUid") UUID uid,
                                               @Parameter(description = "Файл")
                                               @RequestParam("file") MultipartFile file) {
        log.debug("PUT request on .../project/file/{}", uid);
        FileDTO fileDTO = storageService.fileReplace(uid, file);
        return ResponseEntity.ok().body(fileDTO);
    }

    @Operation(summary = "Удалить файл",
            description = "Удаляет файл, по uid")
    @DeleteMapping(value = "/project/file/{fileUid}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileDTO> deleteFile(@Parameter(description = "uid файла, который нужно удалить")
                                              @PathVariable("fileUid") UUID uid) {

        log.debug("DELETE request on .../project/file/{}", uid);
        FileDTO fileDTO = storageService.deleteFile(uid);
        return ResponseEntity.ok().body(fileDTO);
    }
}