package dev.chrisjosue.groceryrestapi.dto.requests.person;

import dev.chrisjosue.groceryrestapi.utils.validations.password.Password;
import dev.chrisjosue.groceryrestapi.utils.validations.password.PasswordMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@PasswordMatch
public class UpdatePasswordDto {
    @NotNull(message = "Old Password must not be null.")
    @NotBlank(message = "Old Password is required.")
    private String oldPassword;
    @NotNull(message = "Password must not be null.")
    @NotBlank(message = "New Password is required.")
    @Password(message = "Password is invalid.")
    private String newPassword;

    @NotNull(message = "Confirm password must not be null.")
    @NotBlank(message = "Confirmation password is required.")
    private String confirmPassword;
}
