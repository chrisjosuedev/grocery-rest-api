package dev.chrisjosue.groceryrestapi.security;

import dev.chrisjosue.groceryrestapi.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        // Verify if Authorization Token is valid (JWT within the header).
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        // Extract JWT and get Username from it.
        jwt = authHeader.substring(7);

        // Find Token, if Exists revoke and set expired to true
        tokenRepository.findByToken(jwt).ifPresent(
                token -> {
                    token.setRevoked(true);
                    token.setExpired(true);
                    tokenRepository.save(token);
                }
        );
    }
}
