package dev.chrisjosue.groceryrestapi.service;

import dev.chrisjosue.groceryrestapi.dto.requests.auth.RecoveryPasswordDto;
import dev.chrisjosue.groceryrestapi.dto.requests.auth.SignInDto;
import dev.chrisjosue.groceryrestapi.dto.responses.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthService {
    AuthResponse signIn(SignInDto signInDto);
    AuthResponse resetPassword(String username, HttpServletRequest request);
    void changePassword(RecoveryPasswordDto recoveryPasswordDto, String token);
}
