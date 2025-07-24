package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Jamaa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String Nom;

    private Long lastId;

    @OneToMany(mappedBy = "jamaa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Montakhib> montakhibs = new ArrayList<>();
}
