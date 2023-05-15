package dev.chrisjosue.groceryrestapi.dto.requests.invoice;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class InvoiceDto {
    @NotNull(message = "Customer is required.")
    private Long customerId;

    @NotEmpty(message = "Product Invoice Details can not be empty.")
    private List<@Valid InvoiceDetailDto> productDetails;
}
