package digital.design.management.system.web.controller;

import digital.design.management.system.service.EmployeeService;
import digital.design.management.system.validator.EmployeeValidator;
import digital.design.management.system.dto.util.InputDataErrorResponse;
import digital.design.management.system.dto.employee.EmployeeDTO;
import digital.design.management.system.dto.employee.EmployeeOutDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
@Tag(name = "Сотрудники", description = "Контроллер для управления сотрудниками")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeValidator employeeValidator;

    @Operation(summary = "Получить всех сотрудников",
            description = "Находит всех сотрудников, но не более 100")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeOutDTO> getEmployees() {
        log.debug("GET request on .../employee");
        return employeeService.getEmployees();
    }

    @Operation(summary = "Получение одного сотрудника",
            description = "Получение сотрудника по uid и статусу 'Активный'")
    @GetMapping(value = "/{uid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeOutDTO getEmployeeByUid(@Parameter(description = "uid сотрудника, которого необходимо получить")
                                           @PathVariable("uid") UUID uid) {
        log.debug("GET request on .../employee/{}", uid);
        return employeeService.getEmployeeByUid(uid);
    }

    @Operation(summary = "Поиск сотрудников",
            description = "Ищет сотрудников по ключевому слову. Поиск очуществляется в полях имя, фамилия, отчество, " +
                    "ник и почта, со статусом 'Активный'")
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeOutDTO> getEmployeeBySearch(@Parameter(description = "Ключевое слово или его часть")
                                                    @RequestParam(value = "key", defaultValue = "") String key) {
        log.debug("GET request on .../employee/search, params: key={}", key);
        return employeeService.getEmployeeByKeyWord(key);
    }

    @Operation(summary = "Создание сотрудника",
            description = "Создает сотрудника и добавляет в БД")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO,
                                                 BindingResult bindingResult) {
        log.debug("POST request on .../employee, params: employeeDTO={}", employeeDTO);
        employeeValidator.validate(employeeDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            List<InputDataErrorResponse> infoErrors = bindingResult.getFieldErrors().stream()
                    .map(e -> InputDataErrorResponse.builder()
                            .defaultMessage(e.getDefaultMessage())
                            .field(e.getField())
                            .build())
                    .toList();

            log.warn("POST request on .../employee contains errors: {}", infoErrors.stream().map(InputDataErrorResponse::getField).toList());
            return new ResponseEntity<>(infoErrors, HttpStatus.FORBIDDEN);
        }
        EmployeeOutDTO employeeOutDTO = employeeService.createEmployee(employeeDTO);
        log.debug("POST request on .../employee is complete");
        return new ResponseEntity<>(employeeOutDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Удаление сотрудника",
            description = "Меняет статус сотрудника с указанным uid на 'Удаленный'")
    @DeleteMapping(value = "/{uid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeOutDTO> deleteEmployee(@Parameter(description = "uid сотрудника, которого необходимо удалить")
                                                         @PathVariable("uid") UUID uid) {
        log.debug("DELETE request on .../employee/{}", uid);
        EmployeeOutDTO employeeOutDTO = employeeService.deleteEmployee(uid);
        return new ResponseEntity<>(employeeOutDTO, HttpStatus.OK);
    }

    @Operation(summary = "Обновление сотрудника",
            description = "Обновление информации о сотруднике")
    @PutMapping(value = "/{uid}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateEmployee(@Parameter(description = "uid сотрудника, которого нужно обновить")
                                                 @PathVariable("uid") UUID uid,
                                                 @Valid @RequestBody EmployeeDTO employeeDTO,
                                                 BindingResult bindingResult) {
        log.debug("PUT request on .../employee/{}", uid);
        if (bindingResult.hasErrors()) {
            List<InputDataErrorResponse> infoErrors = bindingResult.getFieldErrors().stream()
                    .map(e -> InputDataErrorResponse.builder()
                            .defaultMessage(e.getDefaultMessage())
                            .field(e.getField())
                            .build())
                    .toList();
            log.warn("PUT request on .../employee/{} contains errors: {}", uid, infoErrors.stream().map(InputDataErrorResponse::getField).toList());
            return new ResponseEntity<>(infoErrors, HttpStatus.FORBIDDEN);
        }
        EmployeeOutDTO employeeOutDTO = employeeService.updateEmployee(uid, employeeDTO);
        log.debug("PUT request on .../employee is complete");
        return new ResponseEntity<>(employeeOutDTO, HttpStatus.ACCEPTED);
    }

}
