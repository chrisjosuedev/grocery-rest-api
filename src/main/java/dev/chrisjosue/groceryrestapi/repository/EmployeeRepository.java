package dev.chrisjosue.groceryrestapi.repository;

import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByIsActiveIsTrue();

    List<Employee> findAllByIsActiveIsTrue(Pageable pageable);

    Optional<Employee> findByIdAndIsActiveIsTrue(Long id);

    Optional<Employee> findByUsernameAndIsActiveIsTrue(String username);

    Optional<Employee> findByDniOrUsernameOrEmailAndIsActiveIsTrue(String dni, String username, String email);
}
