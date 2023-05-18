package dev.chrisjosue.groceryrestapi.service.imp;

import dev.chrisjosue.groceryrestapi.dto.requests.auth.RecoveryPasswordDto;
import dev.chrisjosue.groceryrestapi.dto.requests.auth.SignInDto;
import dev.chrisjosue.groceryrestapi.dto.requests.mail.MailDto;
import dev.chrisjosue.groceryrestapi.dto.responses.AuthResponse;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import dev.chrisjosue.groceryrestapi.entity.token.TokenType;
import dev.chrisjosue.groceryrestapi.helpers.db.EmployeeHelper;
import dev.chrisjosue.groceryrestapi.helpers.db.TokenHelper;
import dev.chrisjosue.groceryrestapi.helpers.patterns.MyUtils;
import dev.chrisjosue.groceryrestapi.repository.EmployeeRepository;
import dev.chrisjosue.groceryrestapi.security.JwtService;
import dev.chrisjosue.groceryrestapi.service.IAuthService;
import dev.chrisjosue.groceryrestapi.service.IEmailSenderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final JwtService jwtService;
    private final TokenHelper tokenHelper;
    private final EmployeeHelper employeeHelper;
    private final EmployeeRepository employeeRepository;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final IEmailSenderService emailSenderService;

    @Override
    public AuthResponse signIn(SignInDto signInDto) {
        // Auth Manager manage Login, throws an Exception if Auth Fails
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDto.getUsername(),
                        signInDto.getPassword()
                )
        );

        Employee employee = employeeHelper.getEmployeeByUsername(signInDto.getUsername());

        // Generate JWT
        String jwt = jwtService.generateToken(employee);

        // Revoke Previous Tokens
        tokenHelper.revokeAllUserTokens(employee);

        // Save token
        tokenHelper.saveToken(jwt, employee, TokenType.BEARER);

        return AuthResponse.builder()
                .token(jwt)
                .build();
    }

    /**
     * Reset password and Assign a new one
     */
    @Override
    public AuthResponse resetPassword(String username, HttpServletRequest request) {
        Employee employeeFound = employeeHelper.getEmployeeByUsername(username);
        String generateRecoveryToken = UUID.randomUUID().toString();
        String recoveryLink = MyUtils.getSiteURL(request) + "/auth/forgot-password/recovery?token=" + generateRecoveryToken;

        // Update Employee Data and Save Token
        employeeRepository.save(employeeFound);

        // Revoke old Tokens and Save new Token
        tokenHelper.revokeAllUserTokens(employeeFound);
        tokenHelper.saveToken(generateRecoveryToken, employeeFound, TokenType.RESET_TOKEN);

        // Set Message
        String message = "<p>Hello User,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Here is your Recovery Token:</p>"
                + "<p><a href=\"" + recoveryLink + "\">Recovery my password.</a></p>"
                + "<br>";

        // Send Email
        MailDto mailDto = new MailDto(employeeFound.getEmail(), message);
        emailSenderService.sendEmail(mailDto);

        return AuthResponse.builder()
                .token(generateRecoveryToken)
                .build();
    }

    @Override
    public void changePassword(RecoveryPasswordDto recoveryPasswordDto, String token) {
        Employee employeeRequest = employeeHelper.getEmployeeByRecoveryToken(token);

        // Update Password
        employeeRequest.setPassword(passwordEncoder.encode(recoveryPasswordDto.getNewPassword()));
        employeeRepository.save(employeeRequest);

        // Revoke previous tokens.
        tokenHelper.revokeAllUserTokens(employeeRequest);
    }
}
