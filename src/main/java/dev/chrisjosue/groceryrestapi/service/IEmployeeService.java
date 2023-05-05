package dev.chrisjosue.groceryrestapi.service;

import dev.chrisjosue.groceryrestapi.dto.requests.person.EmployeeDto;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;

public interface IEmployeeService {
    Employee create(EmployeeDto employeeDto);
}
