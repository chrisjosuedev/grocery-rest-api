package dev.chrisjosue.groceryrestapi.helpers.db;

import dev.chrisjosue.groceryrestapi.dto.responses.DetailDto;
import dev.chrisjosue.groceryrestapi.dto.responses.InvoiceResponse;
import dev.chrisjosue.groceryrestapi.entity.invoice.Invoice;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import dev.chrisjosue.groceryrestapi.entity.person.Person;
import dev.chrisjosue.groceryrestapi.helpers.patterns.MyUtils;
import dev.chrisjosue.groceryrestapi.repository.InvoiceDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InvoiceHelper {

    private final InvoiceDetailsRepository invoiceDetailsRepository;

    /**
     * Build an Invoice from InvoiceDTO
     */
    public Invoice invoiceFromDto(Employee employee, Person customer) {
        return Invoice.builder()
                .employee(employee)
                .person(customer)
                .build();
    }

    /**
     * Build an Invoice Data response to Generate Invoice
     */
    public InvoiceResponse invoiceResponse(Invoice newInvoice) {
        List<DetailDto> invoiceDetails = invoiceDetailsRepository.getInvoiceDetails(newInvoice.getId());
        Double totalInvoice = invoiceDetailsRepository.getTotal(newInvoice.getId());
        return InvoiceResponse.builder()
                .id(newInvoice.getId())
                .date(newInvoice.getDate())
                .customerName(newInvoice.getPerson().getFirstName() + " " + newInvoice.getPerson().getLastName())
                .employeeName(newInvoice.getEmployee().getFirstName() + " " + newInvoice.getEmployee().getLastName())
                .invoiceDetails(invoiceDetails)
                .total(MyUtils.round(totalInvoice, 2))
                .build();
    }
}
