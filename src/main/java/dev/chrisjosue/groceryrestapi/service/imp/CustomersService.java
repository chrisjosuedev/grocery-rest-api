package dev.chrisjosue.groceryrestapi.service.imp;

import dev.chrisjosue.groceryrestapi.dto.requests.person.PersonDto;
import dev.chrisjosue.groceryrestapi.dto.requests.person.PersonUpdateDto;
import dev.chrisjosue.groceryrestapi.entity.person.Person;
import dev.chrisjosue.groceryrestapi.helpers.db.CustomerHelper;
import dev.chrisjosue.groceryrestapi.repository.CustomerRepository;
import dev.chrisjosue.groceryrestapi.service.ICustomersService;
import dev.chrisjosue.groceryrestapi.utils.exceptions.MyBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomersService implements ICustomersService {
    private final CustomerRepository customerRepository;
    private final CustomerHelper customerHelper;

    @Override
    public List<Person> findAll(Integer limit, Integer from) {
        List<Person> allCustomers = customerRepository.findAllByIsActiveIsTrueAndTypeIsFalse();

        if (limit == null || from == null) return allCustomers;

        allCustomers = customerRepository.findAllByIsActiveIsTrueAndTypeIsFalse(PageRequest.of(from, limit));
        return allCustomers;
    }

    @Override
    public Person findById(Long id) {
        Person customerFound = customerHelper.findById(id);
        if (customerFound == null)
            throw new MyBusinessException("Customer not found with given id.", HttpStatus.NOT_FOUND);

        return customerFound;
    }

    @Override
    public Person create(PersonDto personDto) {
        if (customerHelper.customerAlreadyExists(personDto.getDni(), personDto.getPhone()))
            throw new MyBusinessException("Already exists a Customer with given credentials", HttpStatus.BAD_REQUEST);

        Person newCustomer = customerHelper.customerFromDto(personDto);
        customerRepository.save(newCustomer);

        return newCustomer;
    }

    @Override
    public Person update(Long id, PersonUpdateDto personUpdateDto) {
        Person customerFound = customerHelper.findById(id);
        if (customerFound == null)
            throw new MyBusinessException("Customer not found with given id.", HttpStatus.NOT_FOUND);

        customerFound.setDni(personUpdateDto.getDni());
        customerFound.setFirstName(personUpdateDto.getFirstName());
        customerFound.setLastName(personUpdateDto.getLastName());
        customerFound.setGenre(personUpdateDto.isGenre());
        customerFound.setPhone(personUpdateDto.getPhone());

        return customerFound;
    }

    @Override
    public void remove(Long id) {
        Person customerFound = customerHelper.findById(id);
        if (customerFound == null)
            throw new MyBusinessException("Customer not found with given id.", HttpStatus.NOT_FOUND);

        customerFound.setIsActive(false);
        customerRepository.save(customerFound);
    }
}
