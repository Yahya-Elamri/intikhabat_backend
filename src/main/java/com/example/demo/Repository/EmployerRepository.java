package com.example.demo.Repository;

import com.example.demo.Entity.Employer;
import com.example.demo.Entity.Montakhib;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployerRepository extends JpaRepository<Employer,Long> {
    Optional<Employer> findByUsername(String Username);
    Optional<Employer> findByCin(String cin);

    @Query("""
        SELECT u FROM Employer u 
        WHERE LOWER(u.nom) LIKE LOWER(CONCAT('%', :nom, '%')) 
        AND LOWER(u.prenom) LIKE LOWER(CONCAT('%', :prenom, '%'))
    """)
    List<Employer> searchByNomAndPrenom(@Param("nom") String nom, @Param("prenom") String prenom);}
