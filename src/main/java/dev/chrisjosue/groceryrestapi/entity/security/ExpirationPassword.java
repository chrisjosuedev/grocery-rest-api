package dev.chrisjosue.groceryrestapi.entity.security;

import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "expiration_password")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpirationPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean updated;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
