package dev.chrisjosue.groceryrestapi.repository;

import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUsernameAndIsActiveIsTrue(String username);

    Optional<Employee> findByUsernameOrEmailAndIsActiveIsTrue(String username, String email);
}
