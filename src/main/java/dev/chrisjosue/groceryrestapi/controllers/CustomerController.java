package dev.chrisjosue.groceryrestapi.controllers;

import dev.chrisjosue.groceryrestapi.dto.requests.person.PersonDto;
import dev.chrisjosue.groceryrestapi.dto.requests.person.PersonUpdateDto;
import dev.chrisjosue.groceryrestapi.dto.responses.ResponseDataDto;
import dev.chrisjosue.groceryrestapi.dto.responses.ResponseHandler;
import dev.chrisjosue.groceryrestapi.entity.person.Person;
import dev.chrisjosue.groceryrestapi.service.ICustomersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@Validated
@RequestMapping("/customers")
@RequiredArgsConstructor
@Tag(name = "Customers",
        description = "Store data such as personal details, contact information, preferences, and transaction history.")
public class CustomerController {
    private final ICustomersService customersService;

    @Operation(summary = "Get Customer List.",
            description = """
                    Retrieves the complete list of clients available in the system, providing comprehensive information about each client.
                    """,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Customer List OK.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Customer Data is incorrect",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "User without authentication.",
                    content = @Content),
    })
    @GetMapping
    public ResponseEntity<Object> findAllCustomers(
            @RequestParam(required = false, name = "limit")
            @Min(value = 0, message = "From must be positive number.") Integer limit,
            @RequestParam(required = false, name = "from")
            @Min(value = 0, message = "From must be greater than 0.") Integer from
    ) {
        List<Person> allCustomers = customersService.findAll(limit, from);

        // Data
        ResponseDataDto<Person> dataResponse = ResponseDataDto.<Person>builder()
                .count(allCustomers.size())
                .listFound(allCustomers)
                .build();

        return ResponseHandler.responseBuilder(
                "All customers registered.",
                HttpStatus.OK,
                dataResponse
        );
    }

    @Operation(summary = "Get Customer By Id.",
            description = """
                    Allows for the retrieval of a particular client's information by providing the client ID as a parameter.
                    """,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Customer Found OK.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class))}),
            @ApiResponse(responseCode = "403",
                    description = "User without authentication.",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Customer not found.",
                    content = @Content),
    })
    @GetMapping("/{customerId}")
    public ResponseEntity<Object> findCustomerById(@PathVariable("customerId") Long customerId) {
        return ResponseHandler.responseBuilder(
                "Customer Found.",
                HttpStatus.OK,
                customersService.findById(customerId)
        );
    }

    @Operation(summary = "Create a new Customer.",
            description = """
                    Allows the creation of a new client by providing the necessary client data in the request body.
                    """,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Customer CREATED.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Customer data is incorrect.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "User without authentication.",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<Object> createCustomer(@Valid @RequestBody PersonDto personDto) {
        return ResponseHandler.responseBuilder(
                "Customer created and user assigned successfully.",
                HttpStatus.CREATED,
                customersService.create(personDto)
        );
    }

    @Operation(summary = "Update Customer.",
            description = """
                    Modification of an existing client's data by providing the client ID as a parameter and the updated client information in the request body.
                    """,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Customer Updated OK.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class))}),
            @ApiResponse(responseCode = "403",
                    description = "User without authentication.",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Customer data is incorrect.",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Customer not found.",
                    content = @Content)
    })
    @PutMapping("/{customerId}")
    public ResponseEntity<Object> updateCustomer(
            @PathVariable("customerId") Long customerId,
            @Valid @RequestBody PersonUpdateDto personUpdateDto) {
        return ResponseHandler.responseBuilder(
                "Customer updated successfully.",
                HttpStatus.OK,
                customersService.update(customerId, personUpdateDto)
        );
    }

    @Operation(summary = "Delete Customer.",
            description = """
                    Customer's data will be disabled from the system, ensuring proper management of customer records and data integrity.
                    """,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Customer Removed OK."),
            @ApiResponse(responseCode = "403",
                    description = "User without authentication.",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Customer not found.",
                    content = @Content)
    })
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Object> removeCustomer(@PathVariable("customerId") Long customerId) {
        customersService.remove(customerId);
        return ResponseHandler.responseBuilder(
                "Customer disable successfully.",
                HttpStatus.OK,
                Collections.EMPTY_LIST
        );
    }
}
