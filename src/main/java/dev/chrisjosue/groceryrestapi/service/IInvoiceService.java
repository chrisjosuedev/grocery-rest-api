package dev.chrisjosue.groceryrestapi.service;

import dev.chrisjosue.groceryrestapi.dto.requests.invoice.InvoiceDto;
import dev.chrisjosue.groceryrestapi.dto.responses.InvoiceResponse;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;

public interface IInvoiceService {
    InvoiceResponse registerPurchase(InvoiceDto invoiceDto, Employee employee);
}
