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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof InvoiceDetailDto invoiceObj) {
            boolean isEqual = this.articleId.equals(invoiceObj.articleId);
            if (isEqual) {
                int newQuantity = invoiceObj.amount + this.amount;
                invoiceObj.setAmount(newQuantity);
            }
            return isEqual;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + getArticleId().hashCode();
        return result;
    }

}
