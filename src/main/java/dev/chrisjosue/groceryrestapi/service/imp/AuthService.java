package dev.chrisjosue.groceryrestapi.service.imp;

import dev.chrisjosue.groceryrestapi.dto.requests.auth.SignInDto;
import dev.chrisjosue.groceryrestapi.dto.responses.AuthResponse;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import dev.chrisjosue.groceryrestapi.helpers.db.EmployeeHelper;
import dev.chrisjosue.groceryrestapi.helpers.db.TokenHelper;
import dev.chrisjosue.groceryrestapi.repository.EmployeeRepository;
import dev.chrisjosue.groceryrestapi.security.JwtService;
import dev.chrisjosue.groceryrestapi.service.IAuthService;
import dev.chrisjosue.groceryrestapi.utils.exceptions.PasswordNotUpdatedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final JwtService jwtService;
    private final TokenHelper tokenHelper;
    private final EmployeeHelper employeeHelper;
    private final EmployeeRepository employeeRepository;
    private final AuthenticationManager authManager;

    @Override
    public AuthResponse signIn(SignInDto signInDto) {
        // Auth Manager manage Login, throws an Exception if Auth Fails
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDto.getUsername(),
                        signInDto.getPassword()
                )
        );

        Employee employee = employeeRepository
                .findByUsernameAndIsActiveIsTrue(signInDto.getUsername())
                .orElseThrow();

        // Check if password is updated.
        if (employeeHelper.isPasswordUpdated(employee.getUsername()) == null)
            throw new PasswordNotUpdatedException("Must update password to continue.");

        // Generate JWT
        String jwt = jwtService.generateToken(employee);

        // Revoke Previous Tokens
        tokenHelper.revokeAllUserTokens(employee);

        // Save token
        tokenHelper.saveToken(jwt, employee);

        return AuthResponse.builder()
                .token(jwt)
                .build();
    }
}
