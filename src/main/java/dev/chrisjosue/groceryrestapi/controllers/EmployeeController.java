package dev.chrisjosue.groceryrestapi.controllers;

import dev.chrisjosue.groceryrestapi.dto.requests.person.EmployeeDto;
import dev.chrisjosue.groceryrestapi.dto.responses.ResponseHandler;
import dev.chrisjosue.groceryrestapi.service.IEmployeeService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
/**
 * TODO:
 * Only ADMIN users can add a new Employee
 * @RolesAllowed("ADMIN")
 */
public class EmployeeController {

    private final IEmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Object> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        return ResponseHandler.responseBuilder(
                "Employee created and user assigned successfully.",
                HttpStatus.CREATED,
                employeeService.create(employeeDto)
        );
    }
}
