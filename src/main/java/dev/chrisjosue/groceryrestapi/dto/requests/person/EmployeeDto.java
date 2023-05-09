package dev.chrisjosue.groceryrestapi.dto.requests.person;

import dev.chrisjosue.groceryrestapi.entity.person.Role;
import dev.chrisjosue.groceryrestapi.utils.validations.password.Password;
import dev.chrisjosue.groceryrestapi.utils.validations.username.Username;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class EmployeeDto extends PersonDto {
    @Column(unique = true)
    @NotBlank(message = "Email is required.")
    @Email(message = "Email is invalid.")
    private String email;

    @Column(unique = true)
    @Username(message = "Username is invalid", min = 10)
    private String username;

    @Password
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "hire_date")
    @Temporal(TemporalType.DATE)
    @PastOrPresent(message = "Date is invalid, must be Present or Past.")
    private Date hireDate;
}
