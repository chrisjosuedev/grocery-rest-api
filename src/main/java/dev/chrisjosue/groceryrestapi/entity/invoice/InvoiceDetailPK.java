package dev.chrisjosue.groceryrestapi.entity.invoice;

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
public class InvoiceDetailPK implements Serializable {
    private Long invoiceId;
    private Long articleId;
}
