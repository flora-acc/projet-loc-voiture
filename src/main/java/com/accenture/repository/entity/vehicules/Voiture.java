package com.accenture.repository.entity.vehicules;

import com.accenture.model.Carburant;
import com.accenture.model.Type;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Voiture extends Vehicule {

    @NotBlank
    private int nbPlaces;
    @Enumerated(EnumType.STRING)
    private Carburant carburant;
    @NotBlank
    private int nbPortes;
    @NotBlank
    private String transmission;
    @NotBlank
    private boolean climatisation;// true = Oui, false = Non
    @NotBlank
    private int nbBagages;
    @Enumerated(EnumType.STRING)
    private Type type;



}
