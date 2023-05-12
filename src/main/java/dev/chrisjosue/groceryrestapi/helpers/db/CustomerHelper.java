package dev.chrisjosue.groceryrestapi.helpers.db;

import dev.chrisjosue.groceryrestapi.dto.requests.person.PersonDto;
import dev.chrisjosue.groceryrestapi.entity.person.Person;
import dev.chrisjosue.groceryrestapi.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerHelper {
    private final CustomerRepository customerRepository;

    /**
     * Find If Customer By Id Exists.
     *
     * @Params ID
     * @Return Customer if exists, null otherwise
     */
    public Person findById(Long id) {
        return customerRepository
                .findByIdAndIsActiveIsTrueAndTypeIsFalse(id)
                .orElse(null);
    }

    /**
     * Find If Customer Data already exists based in DNI or Phone.
     *
     * @Params DNI, Phone
     * @Return Boolean if already exists, false otherwise
     */
    public boolean customerAlreadyExists(String dni, String phone) {
        Optional<Person> customerFound = customerRepository
                .findByDniOrPhoneAndIsActiveIsTrueAndTypeIsFalse(dni, phone);
        return customerFound.isPresent();
    }

    /**
     * Build a Customer from CustomerDTO
     *
     * @Params customerDTO
     * @Return Customer Built.
     */
    public Person customerFromDto(PersonDto personDto) {
        return Person.builder()
                .dni(personDto.getDni())
                .firstName(personDto.getFirstName())
                .lastName(personDto.getLastName())
                .genre(personDto.isGenre())
                .phone(personDto.getPhone())
                .isActive(true)
                .type(false)
                .build();
    }
}
