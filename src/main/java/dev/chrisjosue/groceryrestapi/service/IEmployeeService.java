package dev.chrisjosue.groceryrestapi.service;

import dev.chrisjosue.groceryrestapi.dto.requests.person.EmployeeDto;
import dev.chrisjosue.groceryrestapi.dto.requests.person.EmployeeUpdateDto;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;

import java.util.List;

public interface IEmployeeService {
    List<Employee> findAll(Integer limit, Integer from);

    Employee findById(Long id);

    Employee create(EmployeeDto employeeDto);

    Employee update(Employee loggedEmployee, EmployeeUpdateDto employeeUpdateDto);

    void disable(Long id);
}
