package dev.chrisjosue.groceryrestapi.helpers.db;

import dev.chrisjosue.groceryrestapi.dto.requests.person.EmployeeDto;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import dev.chrisjosue.groceryrestapi.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EmployeeHelper {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Find If Employee Data already exists based in Email, Username or DNI.
     * @Params username, email
     * @Return Boolean if already exists, false otherwise
     */
    public boolean employeeAlreadyExists(String dni, String username, String email) {
        Optional<Employee> employeeUserFound = employeeRepository
                .findByDniOrUsernameOrEmailAndIsActiveIsTrue(dni, username, email);
        return employeeUserFound.isPresent();
    }

    /**
     * Build an Employee from EmployeeDTO
     * @Params productDTO
     * @Return Article Built.
     */
    public Employee employeeFromDto(EmployeeDto employeeDto) {
        return Employee.builder()
                .dni(employeeDto.getDni())
                .employeeCode(UUID.randomUUID().toString())
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .genre(employeeDto.isGenre())
                .phone(employeeDto.getPhone())
                .email(employeeDto.getEmail())
                .username(employeeDto.getUsername())
                .password(passwordEncoder.encode(employeeDto.getPassword()))
                .role(employeeDto.getRole())
                .hireDate(employeeDto.getHireDate())
                .isActive(true)
                .type(true)
                .isPasswordUpdated(false)
                .build();
    }

}
