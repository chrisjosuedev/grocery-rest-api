package dev.chrisjosue.groceryrestapi.entity.person;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Employee extends Person {

    @Column(name = "employee_code")
    private String employeeCode;

    @Column(unique = true)
    @NotBlank(message = "Email is required.")
    @Email(message = "Email is invalid.")
    private String email;

    @Column(unique = true)
    @NotBlank(message = "Username is required.")
    /**
     * TODO:
     * @Username custom annotation.
     */
    private String username;

    @NotEmpty(message = "Password is required.")
    /**
     * TODO:
     * @Password custom annotation.
     */
    private char[] password;

    /**
     * TODO:
     * private String role;
     */

    @Column(name = "hire_date")
    @Temporal(TemporalType.DATE)
    @PastOrPresent(message = "Date is invalid, must be Present or Past.")
    private Date hireDate;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToOne
    @JoinColumn(name = "position_id")
    private JobPosition jobPosition;
}
