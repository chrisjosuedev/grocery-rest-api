package dev.chrisjosue.groceryrestapi.helpers.db;

import dev.chrisjosue.groceryrestapi.dto.requests.person.EmployeeDto;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import dev.chrisjosue.groceryrestapi.repository.EmployeeRepository;
import dev.chrisjosue.groceryrestapi.utils.exceptions.MyBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EmployeeHelper {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Get Employee from Principal
     */
    public Employee getLoggedEmployee(Principal principal) {
        return employeeRepository
                .findByUsernameAndIsActiveIsTrue(principal.getName())
                .orElseThrow(() -> new MyBusinessException("Employee not found.", HttpStatus.FORBIDDEN));
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
     * Find If Employee Password is already updated.
     *
     * @Params Employee
     * @Return Employee if password was updated, null otherwise.
     */
    public Employee isPasswordUpdated(String username) {
        return employeeRepository
                .findByUsernameAndIsActiveIsTrue(username)
                .filter(Employee::getIsPasswordUpdated)
                .orElse(null);
    }

    /**
     * Build an Employee from EmployeeDTO
     *
     * @Params productDTO
     * @Return Article Built.
     */
    public Employee employeeFromDto(EmployeeDto employeeDto) {
        return Employee.builder()
                .dni(employeeDto.getDni())
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .genre(employeeDto.isGenre())
                .phone(employeeDto.getPhone())
                .email(employeeDto.getEmail())
                .username(employeeDto.getUsername())
                .password(passwordEncoder.encode(employeeDto.getPassword()))
                .role(employeeDto.getRole())
                .hireDate(employeeDto.getHireDate())
                .isPasswordUpdated(false)
                .isActive(true)
                .type(true)
                .build();
    }

}
