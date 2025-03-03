package com.accenture.repository.entity;

import com.accenture.model.Carburant;
import com.accenture.model.Type;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Voiture extends Vehicule {

    @NotNull
    private Integer nbPlaces;
    @Enumerated(EnumType.STRING)
    private Carburant carburant;
    @NotNull
    private Integer nbPortes;
    @NotBlank
    private String transmission;
    private Boolean climatisation;// true = Oui, false = Non
    @NotNull
    private Integer nbBagages;
    @Enumerated(EnumType.STRING)
    private Type type;



}
