package com.example.demo.Dto;
import com.example.demo.Entity.Education;
import com.example.demo.Entity.Sex;
import com.example.demo.Entity.SituationFamiliale;

public record MontakhibInputDTO(
        String nom,
        String prenom,
        String cin,
        String dateNaissance,
        String lieuNaissance,
        String adresse,
        Sex sex,
        Education education,
        SituationFamiliale situationFamiliale,
        Long jamaaId
) {}