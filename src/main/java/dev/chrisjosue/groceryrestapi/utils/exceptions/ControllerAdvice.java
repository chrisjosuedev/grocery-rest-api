package dev.chrisjosue.groceryrestapi.utils.exceptions;

import dev.chrisjosue.groceryrestapi.dto.responses.ErrorDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {

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
        List<String> listOfClientErrors = new ArrayList<>();
        System.out.println(ex.getMessage());
        listOfClientErrors.add(ex.getMessage().split(":")[1].trim());

        ErrorDto err = ErrorDto.builder()
                .errors(listOfClientErrors)
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
