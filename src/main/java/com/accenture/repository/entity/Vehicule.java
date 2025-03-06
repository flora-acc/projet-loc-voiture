package com.accenture.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Utilisation d'une seule table pour tous les types de véhicules
public abstract class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String marque;
    @NotBlank
    private String modele;
    @NotBlank
    private String couleur;
    @NotBlank
    private String transmission;

    private Integer tarifBase;
    private Integer kilometrage;
    private Boolean actif;
    private Boolean retireParc;

}
