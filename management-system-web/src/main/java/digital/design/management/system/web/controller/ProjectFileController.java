package digital.design.management.system.web.controller;

import digital.design.management.system.dto.file.FileDTO;
import digital.design.management.system.dto.file.FileTokenDTO;
import digital.design.management.system.dto.util.ConflictUploadFile;
import digital.design.management.system.dto.util.InputDataErrorResponse;
import digital.design.management.system.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@Tag(name = "Работа с файлами проекта", description = "Контроллер для управления файлами для проекта")
@ApiResponses({
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400",
                content = @Content(schema = @Schema(implementation = InputDataErrorResponse.class)))
})
public class ProjectFileController {

    private final FileService fileService;

    @Autowired
    public ProjectFileController(@Qualifier("projectFileService") FileService fileService) {
        this.fileService = fileService;
    }

    @Operation(summary = "Скачать файл",
            description = "Возвращает ссылку для скачивания файла")
    @GetMapping(value = "/project/download_file/{fileUid}",
            produces = MediaType.ALL_VALUE)
    public ResponseEntity<Resource> downloadFile(@Parameter(description = "uid файла который нужно скачать")
                                                 @PathVariable("fileUid") UUID uid) {
        Resource file = fileService.downloadFile(uid);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @Operation(summary = "Загрузить файл",
            description = "Загружает файл на сервер и добавляет запись в БД",
            responses = {
                    @ApiResponse(responseCode = "409",
                            content = @Content(schema = @Schema(implementation = ConflictUploadFile.class)))
            })
    @PostMapping(value = "/project/{projectUid}/file",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.ALL_VALUE)
    public ResponseEntity<FileDTO> fileUpload(@Parameter(description = "uid проекта к которому нужно прикрепить файл")
                                              @PathVariable("projectUid") UUID uid,
                                              @Parameter(description = "Файл")
                                              @RequestParam("file") MultipartFile file) {
        FileDTO fileDTO = fileService.fileUpload(file, uid);

        return ResponseEntity.ok().body(fileDTO);
    }

    @PostMapping(value = "/project/file/confirm", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileDTO> confirmUploadFile(@RequestBody FileTokenDTO tokenDTO){
        FileDTO fileDTO = fileService.confirmUploadFile(tokenDTO);
        return ResponseEntity.ok().body(fileDTO);
    }

    @Operation(summary = "Получить все файлы проекта",
            description = "Находит все файлы, прикрепленные к заданному проекту")
    @GetMapping(value = "/project/{projectUid}/file",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FileDTO> getAllFiles(@Parameter(description = "uid проекта, файлы которого необходимо найти")
                                     @PathVariable("projectUid") UUID uid) {
        return fileService.getAllFiles(uid);
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
        FileDTO fileDTO = fileService.fileReplace(uid, file);
        return ResponseEntity.ok().body(fileDTO);
    }

    @Operation(summary = "Удалить файл",
            description = "Удаляет файл, по uid")
    @DeleteMapping(value = "/project/file/{fileUid}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileDTO> deleteFile(@Parameter(description = "uid файла, который нужно удалить")
                                              @PathVariable("fileUid") UUID uid) {
        FileDTO fileDTO = fileService.deleteFile(uid);
        return ResponseEntity.ok().body(fileDTO);
    }
}