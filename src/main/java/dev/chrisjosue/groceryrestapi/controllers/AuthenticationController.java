package dev.chrisjosue.groceryrestapi.controllers;

import dev.chrisjosue.groceryrestapi.dto.requests.auth.AuthDto;
import dev.chrisjosue.groceryrestapi.dto.requests.person.EmployeeDto;
import dev.chrisjosue.groceryrestapi.dto.responses.ResponseHandler;
import dev.chrisjosue.groceryrestapi.service.IEmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private IEmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity<Object> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        return ResponseHandler.responseBuilder(
                "Employee created and user assigned successfully.",
                HttpStatus.CREATED,
                employeeService.create(employeeDto)
        );
        /**
         * TODO:
         * Token
         */
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> login(@Valid @RequestBody AuthDto authDto) {
        // some code...
        return null;
    }
}
