package dev.chrisjosue.groceryrestapi.utils.validations.username;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<Username, String> {
    private int min;

    @Override
    public void initialize(Username constraintAnnotation) {
        this.min = constraintAnnotation.min();
    }

    /**
     * Checks if a string value is a valid username.
     * A valid username should:
     * - Not be null or empty.
     * - Not contain any spaces.
     * - Only contain letters (uppercase and lowercase), digits, period (.) and underscore (_).
     *
     * @param value the value to be validated as a username
     * @param constraintValidatorContext the validation context
     * @return true if the value is a valid username, false otherwise
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || "".equals(value)) return false;

        value = value.trim();
        return !value.contains(" ")
                && value.length() >= min
                && !value.matches(".*[^A-Za-z0-9._].*");
    }
}
