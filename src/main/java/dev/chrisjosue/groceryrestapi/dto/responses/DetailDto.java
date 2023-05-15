package dev.chrisjosue.groceryrestapi.dto.responses;

public interface DetailDto {
    Long getArticleId();
    String getArticleName();
    double getUnitPrice();
    int getQuantity();
    double getSubTotal();
}
