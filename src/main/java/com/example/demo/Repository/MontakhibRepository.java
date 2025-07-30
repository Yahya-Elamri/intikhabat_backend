package com.example.demo.Repository;

import com.example.demo.Entity.Montakhib;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MontakhibRepository extends JpaRepository<Montakhib, Long> {

    // Optimized query with fetch join
    @Query("SELECT m FROM Montakhib m JOIN FETCH m.jamaa WHERE m.id = :id")
    Optional<Montakhib> findByIdWithJamaa(@Param("id") Long id);

    // Case-insensitive search
    List<Montakhib> findByCinIgnoreCase(String cin);

    List<Montakhib> findByJamaaNom(String Nom);

    // Existence check by CIN
    boolean existsByCin(String cin);

    @Query("""
        SELECT u FROM Montakhib u 
        WHERE LOWER(u.nom) LIKE LOWER(CONCAT('%', :nom, '%')) 
        AND LOWER(u.prenom) LIKE LOWER(CONCAT('%', :prenom, '%'))
    """)
    List<Montakhib> searchByNomAndPrenom(@Param("nom") String nom, @Param("prenom") String prenom);

    // For update operations
    boolean existsByCinAndIdNot(String cin, Long id);
}
