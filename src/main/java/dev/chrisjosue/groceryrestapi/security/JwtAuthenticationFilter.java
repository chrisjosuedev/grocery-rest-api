package dev.chrisjosue.groceryrestapi.security;

import dev.chrisjosue.groceryrestapi.helpers.db.EmployeeHelper;
import dev.chrisjosue.groceryrestapi.repository.TokenRepository;
import dev.chrisjosue.groceryrestapi.utils.exceptions.PasswordNotUpdatedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final EmployeeHelper employeeHelper;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Verify if Authorization Token is valid (JWT within the header).
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT and get Username from it.
        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);

        // Verify if Username is in Subject and is not already Authenticated.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // First, check credentials
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Second, Check if password is updated. If not, throws an Exception.
            if (employeeHelper.isPasswordUpdated(userDetails.getUsername()) == null
                    && !employeeHelper.isFirstSession(userDetails.getUsername()))
                throw new PasswordNotUpdatedException("Must update password to continue.");

            // Third, verify if Token already exists and is expired or revoked.
            boolean isTokenValid = tokenRepository.findByToken(jwt)
                    .map(token -> !token.isExpired() && !token.isRevoked())
                    .orElse(false);

            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                /* Creates a WebAuthenticationDetails object that contains
                 * the details of the current request, such as the client's IP address,
                 * user agent information, and the request URL.
                 */
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Update Security Context Holder
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Call Filter
        filterChain.doFilter(request, response);
    }
}
