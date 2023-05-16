package dev.chrisjosue.groceryrestapi.controllers;

import dev.chrisjosue.groceryrestapi.dto.requests.invoice.InvoiceDto;
import dev.chrisjosue.groceryrestapi.dto.responses.ResponseHandler;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import dev.chrisjosue.groceryrestapi.helpers.db.EmployeeHelper;
import dev.chrisjosue.groceryrestapi.service.IInvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Validated
@RequestMapping("/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final EmployeeHelper employeeHelper;
    private final IInvoiceService invoiceService;

    @GetMapping("/{invoiceId}")
    public ResponseEntity<Object> getInvoiceById(@PathVariable("invoiceId") Long invoiceId) {
        return ResponseHandler.responseBuilder(
                "Invoice found.",
                HttpStatus.OK,
                invoiceService.findInvoiceById(invoiceId)
        );
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createInvoice(Principal principal,
                                                @Valid @RequestBody InvoiceDto invoiceDto) {
        Employee loggedEmployee = employeeHelper.getLoggedEmployee(principal);
        return ResponseHandler.responseBuilder(
                "Invoice generated.",
                HttpStatus.CREATED,
                invoiceService.registerPurchase(invoiceDto, loggedEmployee)
        );
    }
}
