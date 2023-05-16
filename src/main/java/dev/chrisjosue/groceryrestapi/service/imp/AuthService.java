package dev.chrisjosue.groceryrestapi.service.imp;

import com.cemiltokatli.passwordgenerate.Password;
import com.cemiltokatli.passwordgenerate.PasswordType;
import dev.chrisjosue.groceryrestapi.dto.requests.auth.PasswordDto;
import dev.chrisjosue.groceryrestapi.dto.requests.auth.SignInDto;
import dev.chrisjosue.groceryrestapi.dto.requests.mail.MailDto;
import dev.chrisjosue.groceryrestapi.dto.responses.AuthResponse;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import dev.chrisjosue.groceryrestapi.helpers.db.EmployeeHelper;
import dev.chrisjosue.groceryrestapi.helpers.db.TokenHelper;
import dev.chrisjosue.groceryrestapi.repository.EmployeeRepository;
import dev.chrisjosue.groceryrestapi.security.JwtService;
import dev.chrisjosue.groceryrestapi.service.IAuthService;
import dev.chrisjosue.groceryrestapi.service.IEmailSenderService;
import dev.chrisjosue.groceryrestapi.utils.exceptions.PasswordNotUpdatedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    /**
     * Reset password and Assign a new one
     */
    @Override
    public void resetPassword(String username) {
        Employee employeeFound = employeeHelper.getEmployeeByUsername(username);
        String generatedPassword = Password
                .createPassword(PasswordType.ALPHANUMERIC, 8)
                .generate()
                .concat("!");

        // Change current password and Create a new One
        employeeFound.setPassword(passwordEncoder.encode(generatedPassword));
        employeeFound.setIsPasswordUpdated(false);

        // Update Employee Data
        employeeRepository.save(employeeFound);

        // Set message in Body Email
        String message = "Your password was updated, please change it for a new one: " + generatedPassword;

        // Send Email
        MailDto mailDto = new MailDto(employeeFound.getEmail(), message);
        emailSenderService.sendEmail(mailDto);
    }

    @Override
    public void changePassword(PasswordDto passwordDto) {
        // TODO: Update Password
    }
}
