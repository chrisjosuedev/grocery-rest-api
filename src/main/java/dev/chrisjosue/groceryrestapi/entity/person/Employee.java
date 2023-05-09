package dev.chrisjosue.groceryrestapi.entity.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.chrisjosue.groceryrestapi.utils.validations.password.Password;
import dev.chrisjosue.groceryrestapi.utils.validations.username.Username;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "employees")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Employee extends Person implements UserDetails {

    @Column(name = "employee_code")
    private String employeeCode;

    @Column(unique = true)
    @NotBlank(message = "Email is required.")
    @Email(message = "Email is invalid.")
    private String email;

    @Column(unique = true)
    @Username(message = "Username is invalid", min = 10)
    private String username;

    @Password(message = "Password is not valid.")
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "hire_date")
    @Temporal(TemporalType.DATE)
    @PastOrPresent(message = "Date is invalid, must be Present or Past.")
    private Date hireDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
