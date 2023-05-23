package digital.design.management.system.web.controller;

import digital.design.management.system.common.exception.SuchUsernameAlreadyExistException;
import digital.design.management.system.validator.EmployeeValidator;
import digital.design.management.system.common.util.InputDataErrorResponse;
import digital.design.management.system.dto.employee.EmployeeDTO;
import digital.design.management.system.dto.employee.EmployeeOutDTO;
import digital.design.management.system.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import digital.design.management.system.common.exception.EmployeeDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
@Tag(name = "Сотрудники", description = "Контроллер для управления сотрудниками")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeValidator employeeValidator;
    private final ResourceBundle resourceBundle;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Находит всех сотрудников, но не более 100")
    public List<EmployeeOutDTO> getEmployees() {

        return employeeService.getEmployees();
    }

    @GetMapping(value = "/{uid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Поиск сотрудника по uid и статусу 'Активный'")
    public EmployeeOutDTO getEmployeeByUid(@PathVariable("uid") UUID uid) {

        return employeeService.getEmployeeByUid(uid);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Ищет сотрудников по ключевому слову. Поиск очуществляется в полях имя, фамилия, отчество, ник " +
            "и почта, со статусом 'Активный'")
    public List<EmployeeOutDTO> getEmployeeBySearch(@RequestParam(value = "key", defaultValue = "") String key) {
        return employeeService.getEmployeeByKeyWord(key);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Добавление нового сотрудника")
    public ResponseEntity<Object> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO,
                                                 BindingResult bindingResult) {

        employeeValidator.validate(employeeDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            List<InputDataErrorResponse> infoErrors = bindingResult.getFieldErrors().stream()
                    .map(e -> InputDataErrorResponse.builder()
                            .defaultMessage(e.getDefaultMessage())
                            .field(e.getField())
                            .build())
                    .toList();

            return new ResponseEntity<>(infoErrors, HttpStatus.FORBIDDEN);
        }
        EmployeeOutDTO employeeOutDTO = employeeService.createEmployee(employeeDTO);
        return new ResponseEntity<>(employeeOutDTO, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{uid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Удаление сотрудника по его uid")
    public ResponseEntity<EmployeeOutDTO> deleteEmployee(@PathVariable("uid") UUID uid) {

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

        if (bindingResult.hasErrors()) {
            List<InputDataErrorResponse> infoErrors = bindingResult.getFieldErrors().stream()
                    .map(e -> InputDataErrorResponse.builder()
                            .defaultMessage(e.getDefaultMessage())
                            .field(e.getField())
                            .build())
                    .toList();

            return new ResponseEntity<>(infoErrors, HttpStatus.FORBIDDEN);
        }
        EmployeeOutDTO employeeOutDTO = employeeService.updateEmployee(uid, employeeDTO);
        return new ResponseEntity<>(employeeOutDTO, HttpStatus.ACCEPTED);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(EmployeeDoesNotExistException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "uid",
                resourceBundle.getString("EMPLOYEE_DOES_NOT_EXIST")
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(SuchUsernameAlreadyExistException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "username",
                resourceBundle.getString("SUCH_USERNAME_ALREADY_EXIST")
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }


}
