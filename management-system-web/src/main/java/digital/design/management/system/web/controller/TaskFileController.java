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
@Tag(name = "Работа с файлами задач", description = "Контроллер для управления файлами для задач")
public class TaskFileController {

    private final StorageService storageService;

    @Autowired

    public TaskFileController(@Qualifier("taskFileService") StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping(value = "/task/download_file/{fileUid}",
            produces = MediaType.ALL_VALUE)
    @Operation(summary = "Возвращает ссылку для скачивания файла")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileUid") UUID uid) {
        log.debug("GET request on .../task/download_file/{}", uid);
        Resource file = storageService.downloadFile(uid);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping(value = "/task/{taskUid}/file",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.ALL_VALUE)
    @Operation(summary = "Сохраняет файл на сервер")
    public ResponseEntity<FileDTO> fileUpload(@PathVariable("taskUid") UUID uid,
                                                 @RequestParam("file") MultipartFile file) {
        log.debug("POST request on .../task/{}/file", uid);
        FileDTO fileDTO = storageService.fileUpload(file, uid);

        return ResponseEntity.ok().body(fileDTO);
    }

    @GetMapping(value = "/task/{taskUid}/file",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Находит все все файлы прикрепленные к заданной задаче")
    public List<FileDTO> getAllFiles(@PathVariable("taskUid") UUID uid){
        log.debug("GET request on .../task/{}/file", uid);
        return storageService.getAllFiles(uid);
    }

    @PutMapping(value = "/task/file/{fileUid}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.ALL_VALUE)
    @Operation(summary = "Заменяет файл с указанным именем на новый файл, формат должен совпадать")
    public ResponseEntity<FileDTO> replaceFile(@PathVariable("fileUid") UUID uid,
                                                    @RequestParam("file") MultipartFile file) {
        log.debug("PUT request on .../task/file/{}", uid);
        FileDTO fileDTO = storageService.fileReplace(uid, file);
        return ResponseEntity.ok().body(fileDTO);
    }

    @DeleteMapping(value = "/task/file/{fileUid}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Удаляет файл")
    public ResponseEntity<FileDTO> deleteFile(@PathVariable("fileUid") UUID uid){

        log.debug("DELETE request on .../task/file/{}", uid);
        FileDTO fileDTO = storageService.deleteFile(uid);
        return ResponseEntity.ok().body(fileDTO);
    }
}
