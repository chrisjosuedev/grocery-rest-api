package dev.chrisjosue.groceryrestapi.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDataDto<T> {
    private Integer count;
    private List<T> listFound;
}
