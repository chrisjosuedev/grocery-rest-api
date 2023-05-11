package dev.chrisjosue.groceryrestapi.helpers.db;

import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import dev.chrisjosue.groceryrestapi.entity.token.Token;
import dev.chrisjosue.groceryrestapi.entity.token.TokenType;
import dev.chrisjosue.groceryrestapi.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenHelper {
    private final TokenRepository tokenRepository;

    /**
     * Save Generated Token by Employee Login
     * @Params jwt, employee
     */
    public void saveToken(String jwt, Employee employee) {
        Token saveGeneratedToken = Token.builder()
                .token(jwt)
                .revoked(false)
                .expired(false)
                .tokenType(TokenType.BEARER)
                .employee(employee)
                .build();
        tokenRepository.save(saveGeneratedToken);
    }

    /**
     * Revoke all valid tokens by Employee.
     */
    public void revokeAllUserTokens(Employee employee) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByEmployee(employee.getId());

        if (validUserTokens.isEmpty()) return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

}
