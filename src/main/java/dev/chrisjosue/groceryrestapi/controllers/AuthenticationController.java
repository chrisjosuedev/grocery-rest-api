package dev.chrisjosue.groceryrestapi.controllers;

import dev.chrisjosue.groceryrestapi.dto.requests.auth.SignInDto;
import dev.chrisjosue.groceryrestapi.service.IEmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IEmployeeService employeeService;

    @PostMapping("/signin")
    public ResponseEntity<Object> login(@Valid @RequestBody SignInDto signInDto) {
        // some code...
        return null;
    }
}
