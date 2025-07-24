package com.example.demo.Service;

import com.example.demo.Dto.LoginReq;
import com.example.demo.Entity.Employer;
import com.example.demo.Repository.EmployerRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final EmployerRepository employerRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            EmployerRepository employerRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.employerRepository = employerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Employer authenticate(LoginReq input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.username(),
                        input.password()
                )
        );

        return employerRepository.findByUsername(input.username())
                .orElseThrow();
    }
}
