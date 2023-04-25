package dev.chrisjosue.groceryrestapi.entity.articles;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "articles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Description is required.")
    private String description;

    private int stock;

    @Column(name = "unit_price")
    @NotNull(message = "Unit price is required.")
    @PositiveOrZero(message = "Unit price must be greater or equal to zero.")
    private double unitPrice;

    private boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Type type;
}
