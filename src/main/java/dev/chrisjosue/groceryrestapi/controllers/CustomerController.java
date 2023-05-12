package dev.chrisjosue.groceryrestapi.controllers;

import dev.chrisjosue.groceryrestapi.dto.requests.person.PersonDto;
import dev.chrisjosue.groceryrestapi.dto.requests.person.PersonUpdateDto;
import dev.chrisjosue.groceryrestapi.dto.responses.ResponseDataDto;
import dev.chrisjosue.groceryrestapi.dto.responses.ResponseHandler;
import dev.chrisjosue.groceryrestapi.entity.person.Person;
import dev.chrisjosue.groceryrestapi.service.ICustomersService;
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
public class CustomerController {
    private final ICustomersService customersService;

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

    @GetMapping("/{customerId}")
    public ResponseEntity<Object> findCustomerById(@PathVariable("customerId") Long customerId) {
        return ResponseHandler.responseBuilder(
                "Customer Found.",
                HttpStatus.OK,
                customersService.findById(customerId)
        );
    }

    @PostMapping
    public ResponseEntity<Object> createCustomer(@Valid @RequestBody PersonDto personDto) {
        return ResponseHandler.responseBuilder(
                "Customer created and user assigned successfully.",
                HttpStatus.CREATED,
                customersService.create(personDto)
        );
    }

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
