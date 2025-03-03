package com.accenture.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
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

    private Integer tarifBase;
    private Integer kilometrage;
    private Boolean actif;
    private Boolean retireParc;

}
