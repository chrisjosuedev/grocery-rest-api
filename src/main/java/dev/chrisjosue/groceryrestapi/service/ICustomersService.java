package dev.chrisjosue.groceryrestapi.service;

import dev.chrisjosue.groceryrestapi.dto.requests.person.PersonDto;
import dev.chrisjosue.groceryrestapi.dto.requests.person.PersonUpdateDto;
import dev.chrisjosue.groceryrestapi.entity.person.Person;

import java.util.List;

public interface ICustomersService {
    List<Person> findAll(Integer limit, Integer from);

    Person findById(Long id);

    Person create(PersonDto personDto);

    Person update(Long personId, PersonUpdateDto personUpdateDto);

    void remove(Long id);
}
