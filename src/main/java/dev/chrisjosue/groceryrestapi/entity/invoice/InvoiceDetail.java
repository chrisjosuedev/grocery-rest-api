package dev.chrisjosue.groceryrestapi.entity.invoice;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.chrisjosue.groceryrestapi.entity.article.Article;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "invoice_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDetail {
    @EmbeddedId
    private InvoiceDetailPK id;

    @NotNull(message = "Amount is required.")
    @Min(value = 1, message = "Minimum required is 1.")
    private Integer amount;

    @NotNull(message = "Price is required.")
    private Double unitPrice;

    @JsonManagedReference
    @ManyToOne(optional = false)
    @MapsId("invoiceId")
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne(optional = false)
    @MapsId("articleId")
    @JoinColumn(name = "article_id")
    private Article article;
}
