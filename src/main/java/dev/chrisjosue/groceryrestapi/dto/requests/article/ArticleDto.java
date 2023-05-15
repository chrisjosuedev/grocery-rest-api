package dev.chrisjosue.groceryrestapi.dto.requests.article;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
public class ArticleDto {
    @NotBlank(message = "Article Name is required.")
    @Length(min = 5, message = "Minimum name length must be greater than 5.")
    private String articleName;

    @NotBlank(message = "Description is required.")
    private String description;

    @NotNull(message = "Stock is required.")
    @Min(value = 0, message = "Stock must greater than 0.")
    private int stock;

    @NotNull(message = "Unit price is required.")
    @PositiveOrZero(message = "Unit price must be greater or equal to zero.")
    private Double unitPrice;
}
