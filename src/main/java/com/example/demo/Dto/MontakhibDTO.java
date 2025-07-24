package com.example.demo.Dto;

import com.example.demo.Entity.Education;
import com.example.demo.Entity.Sex;
import com.example.demo.Entity.SituationFamiliale;

import java.util.Date;

public record MontakhibDTO(
        Long id,
        String nom,
        String prenom,
        String cin,
        Date dateNaissance,  // ISO-8601 format (yyyy-MM-dd)
        String lieuNaissance,
        String adresse,
        Sex sex,
        Education education,
        SituationFamiliale situationFamiliale,
        JamaaDTO jamaa
) {}