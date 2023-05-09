package dev.chrisjosue.groceryrestapi.entity.person;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

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
    @Length(min = 13, max = 13, message = "DNI must be 13 characters length.")
    @Pattern(regexp = "^[0-9]+$", message = "DNI is invalid.")
    private String dni;

    @Column(name = "first_name")
    @NotBlank(message = "First Name is required.")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "First Name is required.")
    private String lastName;

    @NotNull(message = "Genre is required.")
    private boolean genre;

    @NotNull(message = "Phone is required.")
    private String phone;

    private boolean type;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive;
}
