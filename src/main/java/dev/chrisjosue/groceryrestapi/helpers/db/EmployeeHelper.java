package dev.chrisjosue.groceryrestapi.helpers.db;

import dev.chrisjosue.groceryrestapi.dto.requests.person.EmployeeDto;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import dev.chrisjosue.groceryrestapi.entity.token.Token;
import dev.chrisjosue.groceryrestapi.repository.EmployeeRepository;
import dev.chrisjosue.groceryrestapi.repository.TokenRepository;
import dev.chrisjosue.groceryrestapi.utils.exceptions.MyBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmployeeHelper {
    private final EmployeeRepository employeeRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Get Employee Info by Recovery Token
     */
    public Employee getEmployeeByRecoveryToken(String token) {
        Token recoveryTokenFound = tokenRepository
                .findByTokenAndRevokedIsFalseAndExpiredFalse(token)
                .orElseThrow(() -> new MyBusinessException("Token not found.", HttpStatus.FORBIDDEN));

        return findById(recoveryTokenFound.getEmployee().getId());
    }


    /**
     * Find If Employee By ID Exists.
     *
     * @Params ID
     * @Return Employee if exists, null otherwise
     */
    public Employee findById(Long id) {
        return employeeRepository
                .findByIdAndIsActiveIsTrue(id)
                .orElse(null);
    }

    /**
     * Get Employee from Principal
     */
    public Employee getLoggedEmployee(Principal principal) {
        return employeeRepository
                .findByUsernameAndIsActiveIsTrue(principal.getName())
                .orElseThrow(() -> new MyBusinessException("Employee not found.", HttpStatus.FORBIDDEN));
    }

    /**
     * Get Employee By Username
     */
    public Employee getEmployeeByUsername(String username) {
        return employeeRepository
                .findByUsernameAndIsActiveIsTrue(username)
                .orElseThrow(() -> new MyBusinessException("Employee with given username not found.", HttpStatus.FORBIDDEN));
    }

    /**
     * Find If Employee Data already exists based in Email, Username or DNI.
     *
     * @Params username, email
     * @Return Boolean if already exists, false otherwise
     */
    public boolean employeeAlreadyExists(String dni, String username, String email) {
        Optional<Employee> employeeUserFound = employeeRepository
                .findByDniOrUsernameOrEmailAndIsActiveIsTrue(dni, username, email);
        return employeeUserFound.isPresent();
    }

    /**
     * Check if Old Password Match
     */
    public boolean isPasswordMatch(Employee employeeRequest, String password) {
        return passwordEncoder.matches(password, employeeRequest.getPassword());
    }

    /**
     * Build an Employee from EmployeeDTO
     *
     * @Params employeeDTO
     * @Return Employee Built.
     */
    public Employee employeeFromDto(EmployeeDto employeeDto, String securedPassword) {

        return Employee.builder()
                .dni(employeeDto.getDni())
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .genre(employeeDto.isGenre())
                .phone(employeeDto.getPhone())
                .email(employeeDto.getEmail())
                .username(employeeDto.getUsername())
                .password(passwordEncoder.encode(securedPassword))
                .role(employeeDto.getRole())
                .hireDate(employeeDto.getHireDate())
                .isActive(true)
                .type(true)
                .build();
    }


}
