package dev.chrisjosue.groceryrestapi.repository;

import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import dev.chrisjosue.groceryrestapi.entity.person.Person;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Person, Long> {
    List<Person> findAllByIsActiveIsTrueAndTypeIsFalse();

    List<Person> findAllByIsActiveIsTrueAndTypeIsFalse(Pageable pageable);

    Optional<Person> findByIdAndIsActiveIsTrueAndTypeIsFalse(Long id);

    Optional<Person> findByDniOrPhoneAndIsActiveIsTrueAndTypeIsFalse(String dni, String phone);
}
