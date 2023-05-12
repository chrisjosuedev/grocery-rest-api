package dev.chrisjosue.groceryrestapi.controllers;

import dev.chrisjosue.groceryrestapi.dto.requests.invoice.InvoiceDto;
import dev.chrisjosue.groceryrestapi.dto.responses.ResponseHandler;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import dev.chrisjosue.groceryrestapi.helpers.db.EmployeeHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final EmployeeHelper employeeHelper;

    @PostMapping("/create")
    public ResponseEntity<Object> createInvoice(Principal principal,
                                                @Valid @RequestBody InvoiceDto invoiceDto) {
        Employee loggedEmployee = employeeHelper.getLoggedEmployee(principal);
        return ResponseHandler.responseBuilder(
                "Invoice generated.",
                HttpStatus.CREATED,
                null
        );
    }
}
