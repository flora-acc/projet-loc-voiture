package com.accenture.service.dto;

import com.accenture.model.Carburant;
import com.accenture.model.Type;

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
        Type type,
        Integer tarifBase,
        Integer kilometrage,
        Boolean retireParc,
        Boolean actif
) {
}
