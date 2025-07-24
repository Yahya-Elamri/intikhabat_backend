package com.example.demo.Config;

/*
 *
 * @author yahya
 */

import com.example.demo.Service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService,
            HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Check if the Authorization header is present and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extract the JWT token
            final String jwt = authHeader.substring(7);
            final String userEmail = jwtService.extractUsername(jwt);

            // If there is an authenticated user already, we skip further processing
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (userEmail != null && authentication == null) {
                // Load user details by email (from JWT)
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                // Validate if the JWT token is valid
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Extract roles/authorities from the JWT
                    List<GrantedAuthority> authorities = getAuthoritiesFromJwt(jwt);

                    System.out.println("----------- lautorization dyalek :"+authorities);
                    // Create a new Authentication token with roles/authorities
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            authorities
                    );

                    // Set the details (e.g., remote address, session info)
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the Authentication token in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            // Proceed with the filter chain (allow the request to go through)
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            // Handle any exceptions (e.g., token parsing errors)
            handlerExceptionResolver.resolveException(request, response, null, exception);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired JWT token");
            return;
        }
    }

    // Method to extract roles/authorities from the JWT
    private List<GrantedAuthority> getAuthoritiesFromJwt(String jwt) {
        // Extract roles from the JWT (assuming roles are stored in the "role" claim)
        String rolesClaim = jwtService.extractClaim(jwt, claims -> claims.get("role", String.class));

        // Split roles (if multiple roles are provided) and convert to authorities
        return Arrays.stream(rolesClaim.split(","))
                .map(role -> new SimpleGrantedAuthority(role))  // Add "ROLE_" prefix as per Spring Security convention
                .collect(Collectors.toList());
    }
}
