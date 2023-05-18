package dev.chrisjosue.groceryrestapi.service.imp;

import dev.chrisjosue.groceryrestapi.dto.requests.mail.MailDto;
import dev.chrisjosue.groceryrestapi.dto.requests.person.EmployeeDto;
import dev.chrisjosue.groceryrestapi.dto.requests.person.EmployeeUpdateDto;
import dev.chrisjosue.groceryrestapi.dto.requests.person.UpdatePasswordDto;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import dev.chrisjosue.groceryrestapi.helpers.db.EmployeeHelper;
import dev.chrisjosue.groceryrestapi.helpers.db.TokenHelper;
import dev.chrisjosue.groceryrestapi.helpers.patterns.MyUtils;
import dev.chrisjosue.groceryrestapi.repository.EmployeeRepository;
import dev.chrisjosue.groceryrestapi.service.IEmailSenderService;
import dev.chrisjosue.groceryrestapi.service.IEmployeeService;
import dev.chrisjosue.groceryrestapi.utils.exceptions.MyBusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final IEmailSenderService emailSenderService;

    private final PasswordEncoder passwordEncoder;
    private final EmployeeHelper employeeHelper;
    private final TokenHelper tokenHelper;

    @Override
    public List<Employee> findAll(Integer limit, Integer from) {
        List<Employee> allEmployee = employeeRepository.findAllByIsActiveIsTrue();

        if (limit == null || from == null) return allEmployee;

        allEmployee = employeeRepository.findAllByIsActiveIsTrue(PageRequest.of(from, limit));
        return allEmployee;
    }

    @Override
    public Employee findById(Long id) {
        Employee employeeFound = employeeHelper.findById(id);
        if (employeeFound == null)
            throw new MyBusinessException("Employee not found with given id.", HttpStatus.NOT_FOUND);

        return employeeFound;
    }

    @Override
    public Employee create(EmployeeDto employeeDto) {
        if (employeeHelper.employeeAlreadyExists(employeeDto.getDni(), employeeDto.getUsername(), employeeDto.getEmail()))
            throw new MyBusinessException("Already exists a Employee with given credentials", HttpStatus.BAD_REQUEST);

        String initSecuredPassword = MyUtils.generateSecurePassword();

        Employee newEmployee = employeeHelper.employeeFromDto(employeeDto, initSecuredPassword);
        employeeRepository.save(newEmployee);

        // Send Credentials
        String message = "<p>Hello User,</p>"
                + "<p>Your access credentials:</p>"
                + "<p><b>Username: </b>" + newEmployee.getUsername() + "</p>"
                + "<p><b>Password: </b>" + initSecuredPassword + "</p>"
                + "<br>"
                + "Remember Update your password.";
        MailDto mailDto = new MailDto(newEmployee.getEmail(), "ACCESS CREDENTIALS", message);
        emailSenderService.sendEmail(mailDto);

        return newEmployee;
    }

    @Override
    public Employee update(Employee loggedEmployee, EmployeeUpdateDto employeeUpdateDto) {
        loggedEmployee.setDni(employeeUpdateDto.getDni());
        loggedEmployee.setFirstName(employeeUpdateDto.getFirstName());
        loggedEmployee.setLastName(employeeUpdateDto.getLastName());
        loggedEmployee.setGenre(employeeUpdateDto.isGenre());
        loggedEmployee.setPhone(employeeUpdateDto.getPhone());
        loggedEmployee.setEmail(employeeUpdateDto.getEmail());
        loggedEmployee.setRole(employeeUpdateDto.getRole());
        loggedEmployee.setHireDate(employeeUpdateDto.getHireDate());

        employeeRepository.save(loggedEmployee);
        return loggedEmployee;
    }

    @Override
    public void updatePassword(Employee loggedEmployee, UpdatePasswordDto updatePasswordDto) {
        boolean matchPassword = employeeHelper.isPasswordMatch(loggedEmployee, updatePasswordDto.getOldPassword());
        if (!matchPassword)
            throw new MyBusinessException("Employee Old Password do not match.", HttpStatus.BAD_REQUEST);

        loggedEmployee.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));
        employeeRepository.save(loggedEmployee);
    }

    @Override
    public void disable(Long id) {
        Employee employeeFound = employeeHelper.findById(id);
        if (employeeFound == null)
            throw new MyBusinessException("Employee not found with given id.", HttpStatus.NOT_FOUND);
        employeeFound.setIsActive(false);
        employeeRepository.save(employeeFound);

        // Disable and Revoke Access Tokens
        tokenHelper.revokeAllUserTokens(employeeFound);
    }
}
