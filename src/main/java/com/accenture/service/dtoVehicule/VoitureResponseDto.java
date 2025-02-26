package com.accenture.service.dtoVehicule;

import com.accenture.model.Carburant;
import com.accenture.model.Type;
import jakarta.validation.constraints.NotBlank;

public record VoitureResponseDto(
        String marque,
        String modele,
        String couleur,
        int nbPlaces,
        Carburant carburant,
        int nbPortes,
        String transmission,
        boolean climatisation,
        int nbBagages,
        Type type
) {
}
