package com.example.demo.Dto;

import java.util.Date;

public record EmployerInputDTO(
        String nom,
        String prenom,
        String cin,
        Date dateNaissance,
        String telephone,
        String username,
        String password,
        String roles) {
}
