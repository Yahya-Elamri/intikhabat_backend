package com.example.demo.Controller.Authentification;

import com.example.demo.Dto.LoginReq;
import com.example.demo.Dto.LoginRes;
import com.example.demo.Entity.Employer;
import com.example.demo.Service.AuthenticationService;
import com.example.demo.Service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;

/**
 *
 * @author yahya
 */

@RestController
@RequestMapping("/auth")
class AuthController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginRes> authenticate(@RequestBody LoginReq loginUserDto, HttpServletResponse response) {
        try {
            Employer authenticatedUser = authenticationService.authenticate(loginUserDto);

            String jwtToken = jwtService.generateToken(
                    authenticatedUser,
                    authenticatedUser.getRoles(),
                    authenticatedUser.getId()
            );

            // Create cookie with token
            ResponseCookie cookie = ResponseCookie.from("authToken", jwtToken)
                    .httpOnly(false) // More secure: inaccessible via JS
                    .secure(false)   // Use HTTPS in production
                    .path("/")
                    .maxAge(60 * 60) // 1 hour in seconds
                    .sameSite("Strict") // Add SameSite for better CSRF protection
                    .build();

            // Set cookie in response header
            response.addHeader("Set-Cookie", cookie.toString());

            LoginRes loginResponse = new LoginRes(
                    true,
                    "Login successful",
                    jwtToken,
                    jwtService.getExpirationTime()
            );

            return ResponseEntity.ok(loginResponse);

        } catch (AuthenticationException e) {
            // Specific handling for failed login
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginRes(false, "Invalid credentials", null, 0));
        } catch (Exception e) {
            // General error handling
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginRes(false, "Server error", null, 0));
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;

            String roles = jwtService.extractRole(token);
            if (roles.isEmpty()) throw new Exception();

            // Split roles into an array
            String[] rolesArray = roles.split(",");
            return ResponseEntity.ok().body(Map.of("roles", rolesArray));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Token verification failed"));
        }
    }

    @PostMapping("/disconnect")
    public ResponseEntity<String> disconnect(HttpServletResponse response) {
        // Clear the SecurityContext
        SecurityContextHolder.clearContext();

        // Expire the JWT cookie
        Cookie cookie = new Cookie("authToken", null);
        cookie.setHttpOnly(false);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.status(200)
                .body("User disconnected successfully, cookie expired.");
    }

}