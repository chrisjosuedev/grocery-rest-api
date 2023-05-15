package dev.chrisjosue.groceryrestapi.repository;

import dev.chrisjosue.groceryrestapi.dto.responses.DetailDto;
import dev.chrisjosue.groceryrestapi.entity.invoice.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDetailsRepository extends JpaRepository<InvoiceDetail, Long> {
    @Query("""
        SELECT i.article.id as articleId, i.article.articleName as articleName,
        i.unitPrice as unitPrice, i.amount as quantity,
        (i.unitPrice * i.amount) as subTotal
        FROM InvoiceDetail i
        WHERE i.invoice.id = :id
    """)
    List<DetailDto> getInvoiceDetails(Long id);

    @Query("SELECT SUM(i.unitPrice * i.amount) FROM InvoiceDetail i WHERE i.invoice.id = :id")
    Double getTotal(Long id);
}
