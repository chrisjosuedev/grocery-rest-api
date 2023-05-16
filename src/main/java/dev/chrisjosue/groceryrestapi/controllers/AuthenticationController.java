package dev.chrisjosue.groceryrestapi.controllers;

import dev.chrisjosue.groceryrestapi.dto.requests.auth.PasswordDto;
import dev.chrisjosue.groceryrestapi.dto.requests.auth.SignInDto;
import dev.chrisjosue.groceryrestapi.dto.responses.ResponseHandler;
import dev.chrisjosue.groceryrestapi.service.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

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

    /**
     *
     * @param username
     * @return no data, send new password to user

     * Only ADMIN users can update password of users. A User can contact
     * an Admin User y the Admin will generate a new one.
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword(
            @RequestParam("username") String username) {
        authService.resetPassword(username);
        return ResponseHandler.responseBuilder(
                "Temporal password generated, review your email.",
                HttpStatus.OK,
                Collections.EMPTY_LIST
        );
    }

    /**
     * Change Password
     */
    @PostMapping("/change-password")
    public ResponseEntity<Object> changePassword(@Valid @RequestBody PasswordDto passwordDto) {
        authService.changePassword(passwordDto);
        return ResponseHandler.responseBuilder(
                "Password updated successfully. You can logging again.",
                HttpStatus.OK,
                Collections.EMPTY_LIST
        );
    }
}
