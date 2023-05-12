package dev.chrisjosue.groceryrestapi.dto.requests.invoice;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InvoiceDetailDto {
    @NotBlank(message = "Amount is required.")
    @NotNull(message = "Amount can not be null.")
    @Min(value = 1, message = "Minimum required is 1.")
    private Integer amount;

    @NotBlank(message = "Article is required.")
    @NotNull(message = "Amount can not be null.")
    private Long articleId;
}
