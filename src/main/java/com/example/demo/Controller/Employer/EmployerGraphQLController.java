package com.example.demo.Controller.Employer;


import com.example.demo.Dto.EmployerDTO;
import com.example.demo.Service.EmployerService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EmployerGraphQLController {

    private final EmployerService employerService;

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('Admin', 'Search')")
    public List<EmployerDTO> getAllEmployers() {
        return employerService.getAllEmployers();
    }

    @QueryMapping
    public EmployerDTO getEmployerById(@Argument Long id) {
        return employerService.getEmployerById(id);
    }

    @QueryMapping
    public EmployerDTO getEmployerByCin(@Argument String cin) {
        return employerService.findByCin(cin);
    }

    @QueryMapping
    public List<EmployerDTO> getEmployerByNomAndPrenom(@Argument String nom, @Argument String prenom) {
        return employerService.findByNomAndPrenom(nom, prenom);
    }
}