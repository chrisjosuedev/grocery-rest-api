package dev.chrisjosue.groceryrestapi.dto.responses;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class ErrorDto {
    private HttpStatus httpStatus;
    private List<String> errors;
}
