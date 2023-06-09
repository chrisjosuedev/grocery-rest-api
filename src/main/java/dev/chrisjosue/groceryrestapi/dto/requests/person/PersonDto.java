package dev.chrisjosue.groceryrestapi.dto.requests.person;

import dev.chrisjosue.groceryrestapi.utils.validations.phone.Phone;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
    @NotBlank(message = "DNI is required.")
    private String dni;

    @NotBlank(message = "First Name is required.")
    private String firstName;

    @NotBlank(message = "Last Name is required.")
    private String lastName;

    @NotNull(message = "Genre is required.")
    private boolean genre;

    @NotNull(message = "Phone is required.")
    @Phone(message = "Phone is invalid.")
    private String phone;
}
