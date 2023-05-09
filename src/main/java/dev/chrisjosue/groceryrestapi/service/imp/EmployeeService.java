package dev.chrisjosue.groceryrestapi.service.imp;

import dev.chrisjosue.groceryrestapi.dto.requests.person.EmployeeDto;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import dev.chrisjosue.groceryrestapi.helpers.db.EmployeeHelper;
import dev.chrisjosue.groceryrestapi.repository.EmployeeRepository;
import dev.chrisjosue.groceryrestapi.service.IEmployeeService;
import dev.chrisjosue.groceryrestapi.utils.exceptions.MyBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeHelper employeeHelper;

    @Override
    public Employee create(EmployeeDto employeeDto) {
        if (employeeHelper.employeeAlreadyExists(employeeDto.getDni(), employeeDto.getUsername(), employeeDto.getEmail()))
            throw new MyBusinessException("Already exists a Employee with given credentials", HttpStatus.BAD_REQUEST);

        Employee newEmployee = employeeHelper.employeeFromDto(employeeDto);
        employeeRepository.save(newEmployee);

        return newEmployee;
    }
}
