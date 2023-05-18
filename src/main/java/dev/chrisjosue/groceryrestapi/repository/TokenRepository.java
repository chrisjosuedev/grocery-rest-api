package dev.chrisjosue.groceryrestapi.repository;

import dev.chrisjosue.groceryrestapi.entity.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("""
            SELECT t from Token t INNER JOIN Employee e on t.employee.id = e.id
            WHERE e.id = :employeeId and (t.revoked = false OR t.expired = false)
            """)
    List<Token> findAllValidTokensByEmployee(Long employeeId);

    Optional<Token> findByToken(String token);

    Optional<Token> findByTokenAndRevokedIsFalseAndExpiredFalse(String token);
}
