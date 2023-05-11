package dev.chrisjosue.groceryrestapi.controllers;

import dev.chrisjosue.groceryrestapi.dto.requests.person.EmployeeDto;
import dev.chrisjosue.groceryrestapi.dto.requests.person.EmployeeUpdateDto;
import dev.chrisjosue.groceryrestapi.dto.responses.ResponseDataDto;
import dev.chrisjosue.groceryrestapi.dto.responses.ResponseHandler;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import dev.chrisjosue.groceryrestapi.helpers.db.EmployeeHelper;
import dev.chrisjosue.groceryrestapi.service.IEmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@RestController
@Validated
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final IEmployeeService employeeService;
    private final EmployeeHelper employeeHelper;

    @GetMapping
    public ResponseEntity<Object> findAllEmployees(
            @RequestParam(required = false, name = "limit")
            @Min(value = 0, message = "From must be positive number.") Integer limit,
            @RequestParam(required = false, name = "from")
            @Positive(message = "From must be greater than 0.") Integer from
    ) {
        List<Employee> allEmployees = employeeService.findAll(limit, from);

        // Data
        ResponseDataDto<Employee> dataResponse = ResponseDataDto.<Employee>builder()
                .count(allEmployees.size())
                .listFound(allEmployees)
                .build();

        return ResponseHandler.responseBuilder(
                "All employees registered.",
                HttpStatus.OK,
                dataResponse
        );
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Object> findEmployeeById(@PathVariable("employeeId") Long employeeId) {
        return ResponseHandler.responseBuilder(
                "Employee Found.",
                HttpStatus.OK,
                employeeService.findById(employeeId)
        );
    }

    @PostMapping
    public ResponseEntity<Object> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        return ResponseHandler.responseBuilder(
                "Employee created and user assigned successfully.",
                HttpStatus.CREATED,
                employeeService.create(employeeDto)
        );
    }

    @PutMapping
    public ResponseEntity<Object> updateEmployee(Principal principal, @Valid @RequestBody EmployeeUpdateDto employeeUpdateDto) {
        Employee loggedEmployee = employeeHelper.getLoggedEmployee(principal);
        return ResponseHandler.responseBuilder(
                "Employee updated successfully.",
                HttpStatus.OK,
                employeeService.update(loggedEmployee, employeeUpdateDto)
        );
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Object> disableEmployee(@PathVariable("employeeId") Long employeeId) {
        employeeService.disable(employeeId);
        return ResponseHandler.responseBuilder(
                "Employee disable successfully.",
                HttpStatus.OK,
                Collections.EMPTY_LIST
        );
    }
}
