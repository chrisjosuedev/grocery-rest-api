package dev.chrisjosue.groceryrestapi.controllers;

import dev.chrisjosue.groceryrestapi.dto.requests.person.EmployeeDto;
import dev.chrisjosue.groceryrestapi.dto.requests.person.EmployeeUpdateDto;
import dev.chrisjosue.groceryrestapi.dto.requests.person.UpdatePasswordDto;
import dev.chrisjosue.groceryrestapi.dto.responses.ResponseDataDto;
import dev.chrisjosue.groceryrestapi.dto.responses.ResponseHandler;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import dev.chrisjosue.groceryrestapi.helpers.db.EmployeeHelper;
import dev.chrisjosue.groceryrestapi.service.IEmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
@Tag(name = "Employees",
        description = "The following section pertains to employees., allowing saving employees (Only ADMIN users).")
public class EmployeeController {
    private final IEmployeeService employeeService;
    private final EmployeeHelper employeeHelper;

    @Operation(summary = "Get Employee List.",
            description = """
                    Allows you to retrieve a paginated list of employees.\s
                    The "from" parameter indicates the starting index of the employee list, and the "limit" parameter determines the maximum number of employees to be returned per page.\s
                    """,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Employees List OK.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Employee Data is incorrect",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "User without authentication.",
                    content = @Content),
    })
    @GetMapping
    public ResponseEntity<Object> findAllEmployees(
            @RequestParam(required = false, name = "limit")
            @Min(value = 0, message = "From must be positive number.") Integer limit,
            @RequestParam(required = false, name = "from")
            @Min(value = 0, message = "From must be greater than 0.") Integer from
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

    @Operation(summary = "Get Employee By Id.",
            description = """
                    Accepts the employee ID as a parameter and returns the specific employee associated with that ID.
                    """,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Employee Found OK.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "403",
                    description = "User without authentication.",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Employee not found.",
                    content = @Content),
    })
    @GetMapping("/{employeeId}")
    public ResponseEntity<Object> findEmployeeById(@PathVariable("employeeId") Long employeeId) {
        return ResponseHandler.responseBuilder(
                "Employee Found.",
                HttpStatus.OK,
                employeeService.findById(employeeId)
        );
    }

    @Operation(summary = "Create a new Employee.",
            description = """
                    Exclusively accessible to an administrator user. Provide the necessary employee data to create a new employee. """,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Employee CREATED.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Employee data is incorrect.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "User without authentication.",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<Object> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        return ResponseHandler.responseBuilder(
                "Employee created and user assigned successfully. Credentials sent to User Email.",
                HttpStatus.CREATED,
                employeeService.create(employeeDto)
        );
    }

    @Operation(summary = "Update Employee.",
            description = """
                    Update using the corresponding endpoint, allowing authorized users to modify employee information """,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Employee Updated OK.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "403",
                    description = "User without authentication.",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Employee data is incorrect.",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Employee not found.",
                    content = @Content),
    })
    @PutMapping
    public ResponseEntity<Object> updateEmployee(Principal principal, @Valid @RequestBody EmployeeUpdateDto employeeUpdateDto) {
        Employee loggedEmployee = employeeHelper.getLoggedEmployee(principal);
        return ResponseHandler.responseBuilder(
                "Employee updated successfully.",
                HttpStatus.OK,
                employeeService.update(loggedEmployee, employeeUpdateDto)
        );
    }

    @Operation(summary = "Update Password Employee.",
            description = """
                    Updating Password providing the required information and a valid Bearer token, authorized users can securely update an employee's password while ensuring proper authentication and authorization.
                    """,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Password Updated OK."),
            @ApiResponse(responseCode = "400",
                    description = "Password data is incorrect.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "User without authentication.",
                    content = @Content),
    })
    @PutMapping("/change-password")
    public ResponseEntity<Object> updateEmployeePassword(Principal principal,
                                                 @Valid @RequestBody UpdatePasswordDto updatePasswordDto) {
        Employee loggedEmployee = employeeHelper.getLoggedEmployee(principal);
        employeeService.updatePassword(loggedEmployee, updatePasswordDto);
        return ResponseHandler.responseBuilder(
                "Password updated successfully.",
                HttpStatus.OK,
                Collections.EMPTY_LIST
        );
    }

    @Operation(summary = "Delete Employee.",
            description = """
                    Deleting Employee an authorized administrator can remove the employee from the system, ensuring proper management of employee records and maintaining data integrity.
                    """,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Employee Removed OK."),
            @ApiResponse(responseCode = "403",
                    description = "User without authentication.",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Employee not found.",
                    content = @Content),
    })
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
