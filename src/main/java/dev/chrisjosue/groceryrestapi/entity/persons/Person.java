package dev.chrisjosue.groceryrestapi.entity.persons;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "persons")
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person {
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

    /**
     * TODO:
     * Validate with @Genre or Service Layer
     */
    @NotBlank(message = "Genre is required;")
    private String genre;

    /**
     * TODO:
     * @Phone annotation
     * Validation with Twilio.
     */
    @NotBlank(message = "Phone is required.")
    private String phone;

    private boolean type;

    @Column(name = "is_active")
    private boolean isActive;
}
