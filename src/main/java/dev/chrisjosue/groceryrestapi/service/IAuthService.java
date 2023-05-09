package dev.chrisjosue.groceryrestapi.service;

import dev.chrisjosue.groceryrestapi.dto.requests.auth.SignInDto;
import dev.chrisjosue.groceryrestapi.dto.responses.AuthResponse;

public interface IAuthService {
    AuthResponse signIn(SignInDto signInDto);
}
