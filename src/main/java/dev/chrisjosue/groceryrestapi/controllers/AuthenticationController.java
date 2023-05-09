package dev.chrisjosue.groceryrestapi.controllers;

import dev.chrisjosue.groceryrestapi.dto.requests.auth.SignInDto;
import dev.chrisjosue.groceryrestapi.dto.responses.ResponseHandler;
import dev.chrisjosue.groceryrestapi.service.IAuthService;
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
    private final IAuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<Object> login(@Valid @RequestBody SignInDto signInDto) {
        return ResponseHandler.responseBuilder(
                "User Logged successfully.",
                HttpStatus.OK,
                authService.signIn(signInDto)
        );
    }
}
