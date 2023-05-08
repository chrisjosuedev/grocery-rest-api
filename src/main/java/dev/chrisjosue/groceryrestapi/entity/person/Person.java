package dev.chrisjosue.groceryrestapi.entity.person;

import dev.chrisjosue.groceryrestapi.utils.validations.phone.Phone;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "persons")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "DNI is required.")
    private String dni;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "Genre is required.")
    private boolean genre;

    @NotBlank(message = "Phone is required.")
    @Phone(message = "Phone is invalid.")
    private String phone;

    private boolean type;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive;
}
