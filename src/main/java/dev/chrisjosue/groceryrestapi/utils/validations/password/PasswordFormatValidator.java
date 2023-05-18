package dev.chrisjosue.groceryrestapi.utils.validations.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;

public class PasswordFormatValidator implements ConstraintValidator<Password, String> {

    @Override
    public void initialize(Password constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Validates that the input string.
     *
     * @param value                      the string value to be validated
     * @param constraintValidatorContext the context in which the validation is performed
     * @return true if the input string meets all the validation criteria, false otherwise
     */

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || "".equals(value)) return false;
        PasswordValidator validator = new PasswordValidator(
                new LengthRule(8, 64),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule(),
                new RepeatCharacterRegexRule(3)
        );

        RuleResult result = validator.validate(new PasswordData(value));
        return result.isValid();
    }

}
