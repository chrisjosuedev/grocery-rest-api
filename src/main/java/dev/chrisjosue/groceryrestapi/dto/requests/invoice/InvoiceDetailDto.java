package dev.chrisjosue.groceryrestapi.dto.requests.invoice;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InvoiceDetailDto {
    @NotNull(message = "Amount is required.")
    @Min(value = 1, message = "Minimum required is 1.")
    private Integer amount;

    @NotNull(message = "Amount is required.")
    private Long articleId;
}
