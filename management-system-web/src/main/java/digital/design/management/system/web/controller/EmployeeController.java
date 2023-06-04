package digital.design.management.system.web.controller;

import digital.design.management.system.service.EmployeeService;
import digital.design.management.system.validator.EmployeeValidator;
import digital.design.management.system.dto.util.InputDataErrorResponse;
import digital.design.management.system.dto.employee.EmployeeDTO;
import digital.design.management.system.dto.employee.EmployeeOutDTO;
import io.swagger.v3.oas.annotations.Operation;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Находит всех сотрудников, но не более 100")
    public List<EmployeeOutDTO> getEmployees() {
        log.debug("GET request on .../employee");
        return employeeService.getEmployees();
    }

    @GetMapping(value = "/{uid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Поиск сотрудника по uid и статусу 'Активный'")
    public EmployeeOutDTO getEmployeeByUid(@PathVariable("uid") UUID uid) {
        log.debug("GET request on .../employee/{}",  uid);
        return employeeService.getEmployeeByUid(uid);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Ищет сотрудников по ключевому слову. Поиск очуществляется в полях имя, фамилия, отчество, ник " +
            "и почта, со статусом 'Активный'")
    public List<EmployeeOutDTO> getEmployeeBySearch(@RequestParam(value = "key", defaultValue = "") String key) {
        log.debug("GET request on .../employee/search, params: key={}",  key);
        return employeeService.getEmployeeByKeyWord(key);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Добавление нового сотрудника")
    public ResponseEntity<Object> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO,
                                                 BindingResult bindingResult) {
        log.debug("POST request on .../employee, params: employeeDTO={}",  employeeDTO);
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

    @DeleteMapping(value = "/{uid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Удаление сотрудника по его uid")
    public ResponseEntity<EmployeeOutDTO> deleteEmployee(@PathVariable("uid") UUID uid) {
        log.debug("DELETE request on .../employee/{}",  uid);
        EmployeeOutDTO employeeOutDTO = employeeService.deleteEmployee(uid);
        return new ResponseEntity<>(employeeOutDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{uid}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Обновление данных сотрудника")
    public ResponseEntity<Object> updateEmployee(@PathVariable("uid") UUID uid,
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
