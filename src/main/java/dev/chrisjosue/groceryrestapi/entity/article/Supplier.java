package dev.chrisjosue.groceryrestapi.entity.article;

import dev.chrisjosue.groceryrestapi.utils.validations.phone.Phone;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "suppliers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
    @Id
    @NotBlank(message = "Tax Identifier Number is required.")
    private String tin;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email is invalid.")
    private String email;

    @NotBlank(message = "Phone is required.")
    @Phone(message = "Phone is invalid.")
    private String phone;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive;
}
