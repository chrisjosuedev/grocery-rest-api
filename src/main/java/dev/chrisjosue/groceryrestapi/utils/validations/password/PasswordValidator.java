package dev.chrisjosue.groceryrestapi.utils.validations.password;

import dev.chrisjosue.groceryrestapi.helpers.patterns.MyUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public void initialize(Password constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Validates that the input string meets the following criteria:
     * - Contains not space character.
     * - Has a minimum length of 8 characters.
     * - Contains special characters.
     * - Contains at least one uppercase letter.
     * - Contains at least one numeric digit.
     * The method takes a string value and a ConstraintValidatorContext object as inputs,
     * and returns a boolean value indicating whether the input string is valid or not.
     *
     * @param value                      the string value to be validated
     * @param constraintValidatorContext the context in which the validation is performed
     * @return true if the input string meets all the validation criteria, false otherwise
     */

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || "".equals(value)) return false;

        value = value.trim();
        return !value.contains(" ")
                && value.length() >= 8
                && MyUtils.matchWithString(value, "[^a-zA-Z0-9]+")
                && !value.equals(value.toLowerCase())
                && !MyUtils.matchWithString(value, ".*\\\\d+.*");
    }

}
