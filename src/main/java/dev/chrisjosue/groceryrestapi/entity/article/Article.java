package dev.chrisjosue.groceryrestapi.entity.article;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "articles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Article Name is required.")
    @Length(min = 5, message = "Minimun name length must be greater than 5.")
    private String articleName;

    @NotBlank(message = "Description is required.")
    private String description;

    @Min(value = 0, message = "Stock must greater than 0.")
    private int stock;

    @Column(name = "unit_price")
    @NotNull(message = "Unit price is required.")
    @PositiveOrZero(message = "Unit price must be greater or equal to zero.")
    private Double unitPrice;

    @Column(name = "is_enabled", columnDefinition = "boolean default true")
    private Boolean isEnabled;
}
