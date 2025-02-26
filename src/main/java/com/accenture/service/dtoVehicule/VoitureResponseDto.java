package com.accenture.service.dtoVehicule;

import com.accenture.model.Carburant;
import com.accenture.model.Type;
import jakarta.validation.constraints.NotBlank;

public record VoitureResponseDto(
        Integer id,
        String marque,
        String modele,
        String couleur,
        Integer nbPlaces,
        Carburant carburant,
        Integer nbPortes,
        String transmission,
        Boolean climatisation,
        Integer nbBagages,
        Type type
) {
}
