package dev.chrisjosue.groceryrestapi.entity.articles;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class PurchaseDetailPK implements Serializable {
    private Long articlePurchaseId;
    private Long articleId;
}
