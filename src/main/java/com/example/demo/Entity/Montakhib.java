package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Montakhib {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(nullable = false, length = 20, unique = true)
    private String cin;

    @Column(name = "date_naissance", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    @Column(name="lieu_naissance",nullable = false, length = 100)
    private String lieuNaissance;

    @Column(nullable = false, length = 100)
    private String adresse;


    @Column(nullable = false, length = 100)
    private Sex sex;


    @Column(nullable = false, length = 100)
    private Education education;


    @Column(nullable = false, length = 100)
    private SituationFamiliale situationFamiliale;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "jamaa_id")
    @JsonBackReference
    private Jamaa jamaa;
}