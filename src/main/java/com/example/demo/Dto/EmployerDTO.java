package com.example.demo.Dto;

import com.example.demo.Entity.Role;

import java.util.Date;
import java.util.Set;

public record EmployerDTO(
        Long id,
        String nom,
        String prenom,
        String cin,
        Date dateNaissance,
        String telephone,
        String username,
        Set<Role> roles) {
}
