package dev.chrisjosue.groceryrestapi.controllers;

import dev.chrisjosue.groceryrestapi.dto.requests.invoice.InvoiceDto;
import dev.chrisjosue.groceryrestapi.dto.responses.InvoiceResponse;
import dev.chrisjosue.groceryrestapi.dto.responses.ResponseHandler;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import dev.chrisjosue.groceryrestapi.helpers.db.EmployeeHelper;
import dev.chrisjosue.groceryrestapi.service.IInvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Orders",
        description = """
                Stores and generates data related to the registration of product billing. It serves as a record of the financial transaction between the business and the customer for the products purchased.
                """)
public class InvoiceController {
    private final EmployeeHelper employeeHelper;
    private final IInvoiceService invoiceService;

    @Operation(summary = "Get Invoice By Id.",
            description = """
                    Retrieve a specific invoice by providing the invoice ID as a parameter. The system will return the invoice details associated with the provided ID.""",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Customer List OK.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvoiceResponse.class))}),
            @ApiResponse(responseCode = "403",
                    description = "User without authentication.",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Invoice Not Found.",
                    content = @Content)
    })
    @GetMapping("/{invoiceId}")
    public ResponseEntity<Object> getInvoiceById(@PathVariable("invoiceId") Long invoiceId) {
        return ResponseHandler.responseBuilder(
                "Invoice found.",
                HttpStatus.OK,
                invoiceService.findInvoiceById(invoiceId)
        );
    }

    @Operation(summary = "Create new Invoice.",
            description = """
                    Creation of a new invoice by sending the necessary invoice data in the request body. The system will generate a unique identifier for the invoice and store it in the database.
                    """,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Invoice Created.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvoiceResponse.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Invoice Data is incorrect.",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Invoice Details not Found.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "User without authentication.",
                    content = @Content)
    })
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
