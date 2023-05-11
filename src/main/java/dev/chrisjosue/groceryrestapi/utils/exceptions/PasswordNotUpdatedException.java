package dev.chrisjosue.groceryrestapi.utils.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class PasswordNotUpdatedException extends RuntimeException {
    private HttpStatus httpStatus;

    public PasswordNotUpdatedException(String message) {
        super(message);
        this.httpStatus = HttpStatus.FORBIDDEN;
    }
}
