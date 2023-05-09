package dev.chrisjosue.groceryrestapi.service.imp;

import dev.chrisjosue.groceryrestapi.dto.requests.auth.SignInDto;
import dev.chrisjosue.groceryrestapi.dto.responses.AuthResponse;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import dev.chrisjosue.groceryrestapi.repository.EmployeeRepository;
import dev.chrisjosue.groceryrestapi.security.JwtService;
import dev.chrisjosue.groceryrestapi.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final JwtService jwtService;
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

        Employee employee = employeeRepository.findByUsernameAndIsActiveIsTrue(signInDto.getUsername())
                .orElseThrow();

        return AuthResponse.builder()
                .token(jwtService.generateToken(employee))
                .build();
    }
}
