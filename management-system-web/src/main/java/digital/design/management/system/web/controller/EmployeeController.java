package digital.design.management.system.web.controller;

import digital.design.management.system.common.exception.EntityDoesNotExistException;
import digital.design.management.system.common.exception.SuchUsernameAlreadyExistException;
import digital.design.management.system.common.util.EmployeeValidator;
import digital.design.management.system.common.util.InputDataErrorResponse;
import digital.design.management.system.dto.employee.EmployeeDTO;
import digital.design.management.system.dto.employee.EmployeeOutDTO;
import digital.design.management.system.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeValidator employeeValidator;

    @GetMapping
    public List<EmployeeOutDTO> getEmployees() {

        return employeeService.getEmployees();
    }

    @GetMapping("/{uid}")
    public EmployeeOutDTO getEmployeeByUid(@PathVariable("uid") UUID uid) {

        return employeeService.getEmployeeByUid(uid);
    }

    @GetMapping("/search")
    public List<EmployeeOutDTO> getEmployeeBySearch(@RequestParam(value = "key", defaultValue = "") String key) {
        return employeeService.getEmployeeByKeyWord(key);
    }

    @PostMapping("")
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

    @DeleteMapping("/{uid}")

    public ResponseEntity<EmployeeOutDTO> deleteEmployee(@PathVariable("uid") UUID uid) {

        EmployeeOutDTO employeeOutDTO = employeeService.deleteEmployee(uid);
        return new ResponseEntity<>(employeeOutDTO, HttpStatus.OK);
    }

    @PutMapping("/{uid}")
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
    private ResponseEntity<InputDataErrorResponse> handleException(EntityDoesNotExistException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "uid",
                "Сотрудник с таким uid не найден"
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<InputDataErrorResponse> handleException(SuchUsernameAlreadyExistException e) {
        InputDataErrorResponse response = new InputDataErrorResponse(
                "username",
                "Сотрудник с такой учетной записью уже существует"
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }


}
