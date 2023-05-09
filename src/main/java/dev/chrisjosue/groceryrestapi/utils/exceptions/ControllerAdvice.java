package dev.chrisjosue.groceryrestapi.utils.exceptions;

import dev.chrisjosue.groceryrestapi.dto.responses.ErrorDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {
    /**
     *
     * @param ex Exception Custom
     * @return Error in Array List
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorDto> exceptionHandler(Exception ex) {
        List<String> listOfCustomErrors = new ArrayList<>();

        listOfCustomErrors.add(ex.getMessage());
        ErrorDto err = ErrorDto.builder()
                .errors(listOfCustomErrors)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();

        return new ResponseEntity<>(err, err.getHttpStatus());
    }

    /**
     *
     * @param ex BadCredentialsException Custom
     * @return Error in Array List
     */
    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ErrorDto> exceptionHandler(BadCredentialsException ex) {
        List<String> listOfCustomErrors = new ArrayList<>();

        listOfCustomErrors.add(ex.getMessage());
        ErrorDto err = ErrorDto.builder()
                .errors(listOfCustomErrors)
                .httpStatus(HttpStatus.FORBIDDEN)
                .build();

        return new ResponseEntity<>(err, err.getHttpStatus());
    }

    /**
     *
     * @param ex MyBusinessException Custom
     * @return Error in Array List
     */
    @ExceptionHandler(value = MyBusinessException.class)
    public ResponseEntity<ErrorDto> businessExceptionHandler(MyBusinessException ex) {
        List<String> listOfCustomErrors = new ArrayList<>();

        listOfCustomErrors.add(ex.getMessage());
        ErrorDto err = ErrorDto.builder()
                .errors(listOfCustomErrors)
                .httpStatus(ex.getHttpStatus())
                .build();

        return new ResponseEntity<>(err, ex.getHttpStatus());
    }

    /**
     *
     * @param ex ConstraintValidation if validation fails.
     * @return Error in Array List
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> constraintExceptionHandler(ConstraintViolationException ex) {
        List<String> listOfViolationExceptionErrors = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        ErrorDto err = ErrorDto.builder()
                .errors(listOfViolationExceptionErrors)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();

        return new ResponseEntity<>(err, err.getHttpStatus());
    }

    /**
     *
     * @param ex MethodArgumentTypeMismatchException in convert DataTypes
     * @return Errors in Array List
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDto> defaultExceptionHandler(MethodArgumentTypeMismatchException ex) {
        List<String> listOfClientErrors = new ArrayList<>();
        listOfClientErrors.add(ex.getMessage());

        ErrorDto err = ErrorDto.builder()
                .errors(listOfClientErrors)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();

        return new ResponseEntity<>(err, err.getHttpStatus());
    }


    /**
     *
     * @param ex MethodArgumentNotValidException in @Valid
     * @return Errors in Array List
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidateException(MethodArgumentNotValidException ex) {
        List<String> listOfMethodErrors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            listOfMethodErrors.add(error.getDefaultMessage());
        });
        ErrorDto err = ErrorDto.builder()
                .errors(listOfMethodErrors)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }


}
