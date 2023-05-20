package dev.chrisjosue.groceryrestapi.helpers.patterns;

import dev.chrisjosue.groceryrestapi.dto.responses.DetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailDtoImp implements DetailDto {
    private Long articleId;
    private String articleName;
    private double unitPrice;
    private int quantity;
    private double subTotal;

    @Override
    public Long getArticleId() {
        return this.articleId;
    }

    @Override
    public String getArticleName() {
        return this.articleName;
    }

    @Override
    public double getUnitPrice() {
        return this.unitPrice;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public double getSubTotal() {
        return 0;
    }
}
