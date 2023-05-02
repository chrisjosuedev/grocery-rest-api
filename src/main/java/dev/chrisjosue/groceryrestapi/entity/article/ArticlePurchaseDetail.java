package dev.chrisjosue.groceryrestapi.entity.article;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "articles_purchase_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePurchaseDetail {
    @EmbeddedId
    private PurchaseDetailPK id;

    @PositiveOrZero(message = "Amount must be greater or equal to zero.")
    @NotNull(message = "Amount is required.")
    private Integer amount;

    @PositiveOrZero(message = "Price must be greater or equal to zero.")
    @NotNull(message = "Price is required.")
    private Double price;

    @JsonManagedReference
    @ManyToOne(optional = false)
    @MapsId("articlePurchaseId")
    @JoinColumn(name = "article_purchase_id")
    private ArticlePurchase articlePurchase;

    @ManyToOne(optional = false)
    @MapsId("articleId")
    @JoinColumn(name = "article_id")
    private Article article;
}
