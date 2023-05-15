package dev.chrisjosue.groceryrestapi.dto.responses;

import dev.chrisjosue.groceryrestapi.entity.invoice.Invoice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class InvoiceResponse {
    private Long id;
    private Date date;
    private String customerName;
    private String employeeName;
    private List<DetailDto> invoiceDetails;
    private Double total;
}
