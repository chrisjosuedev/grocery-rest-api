package dev.chrisjosue.groceryrestapi.utils.validations.password;

import dev.chrisjosue.groceryrestapi.dto.requests.auth.RecoveryPasswordDto;
import dev.chrisjosue.groceryrestapi.dto.requests.person.UpdatePasswordDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {
    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        if (obj instanceof RecoveryPasswordDto recoveryPasswordDto) {
            return recoveryPasswordDto.getNewPassword().equals(recoveryPasswordDto.getConfirmPassword());
        } else if (obj instanceof UpdatePasswordDto updatePasswordDto) {
            return updatePasswordDto.getNewPassword().equals(updatePasswordDto.getConfirmPassword());
        }
        return false;
    }
}
