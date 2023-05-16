package dev.chrisjosue.groceryrestapi.dto.requests.auth;

import dev.chrisjosue.groceryrestapi.utils.validations.password.Password;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PasswordDto {
    @NotBlank(message = "Old password is required.")
    private String oldPassword;

    @NotBlank(message = "New Password is required.")
    @Password(message = "Password is invalid")
    private String newPassword;

    @NotBlank(message = "Confirmation password is required.")
    private String confirmPassword;
}
