package dev.chrisjosue.groceryrestapi.controllers;

import dev.chrisjosue.groceryrestapi.dto.requests.auth.RecoveryPasswordDto;
import dev.chrisjosue.groceryrestapi.dto.requests.auth.SignInDto;
import dev.chrisjosue.groceryrestapi.dto.responses.AuthResponse;
import dev.chrisjosue.groceryrestapi.dto.responses.ResponseHandler;
import dev.chrisjosue.groceryrestapi.service.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "This section is dedicated to handling operations related to user authentication within the system.")
public class AuthenticationController {
    private final IAuthService authService;

    @Operation(summary = "Sign In User",
            description = """
                    Used for user authentication. With the provided data, access and administrator permissions are granted.\s
                    """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Login OK.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))}),
            @ApiResponse(responseCode = "400",
                    description = "User Information is incorrect.",
                    content = @Content)
    })
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
    @Operation(summary = "Request New Password",
            description = """
                     Allows users to change their password by providing the token received via email as a parameter.
                     """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Password Updated OK.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Token Received is incorrect.",
                    content = @Content)
    })
    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword(
            @RequestParam("username") String username,
            HttpServletRequest request) {
        return ResponseHandler.responseBuilder(
                "Temporal password generated, review your email.",
                HttpStatus.OK,
                authService.resetPassword(username, request)
        );
    }

    /**
     * Change Password
     */
    @Operation(summary = "Recovery Password",
            description = """
                     Accessible only by the ADMIN, allows the ADMIN to initiate a password reset process for a specific user.\s
                     This endpoint requires the parameter of the user for whom the password reset is requested""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK."),
            @ApiResponse(responseCode = "400",
                    description = "Password Format is incorrect.",
                    content = @Content)
    })
    @PostMapping("/forgot-password/recovery")
    public ResponseEntity<Object> changePassword(
            @RequestParam("token") String token,
            @Valid @RequestBody RecoveryPasswordDto passwordDto) {
        authService.changePassword(passwordDto, token);
        return ResponseHandler.responseBuilder(
                "Password updated successfully. You can logging again with new password.",
                HttpStatus.OK,
                Collections.EMPTY_LIST
        );
    }
}
