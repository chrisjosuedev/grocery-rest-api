package dev.chrisjosue.groceryrestapi.helpers.db;

import dev.chrisjosue.groceryrestapi.dto.requests.person.EmployeeDto;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import dev.chrisjosue.groceryrestapi.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmployeeHelper {
    private final EmployeeRepository employeeRepository;

    /**
     * Find Employee by Username
     * @Params username, email
     * @Return Boolean if already exists, false otherwise
     */
    public boolean findEmployeeByUsernameOrEmail(String username, String email) {
        Optional<Employee> employeeUserFound = employeeRepository.findByUsernameOrEmailAndIsActiveIsTrue(username, email);
        return employeeUserFound.isPresent();
    }

    /**
     * Build an Employee from EmployeeDTO
     * @Params productDTO
     * @Return Article Built.
     */
    public Employee employeeFromDto(EmployeeDto employeeDto) {
        /**
         * TODO:
         * BCrypt Password
         */
        return Employee.builder()
                .dni(employeeDto.getDni())
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .genre(employeeDto.isGenre())
                .phone(employeeDto.getPhone())
                .email(employeeDto.getEmail())
                .username(employeeDto.getUsername())
                .password(employeeDto.getPassword())
                .role(employeeDto.getRole())
                .hireDate(employeeDto.getHireDate())
                .build();
    }

}
