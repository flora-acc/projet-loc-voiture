package com.accenture.service.dto;

import com.accenture.model.Cylindree;
import com.accenture.model.Type;
import com.accenture.model.TypeMoto;

public record MotoResponseDto  (Integer id,
    String marque,
    String modele,
    String couleur,
    Cylindree cylindree,
    Integer poids,
    String transmission,
    Boolean climatisation,
    Integer nbBagages,
    TypeMoto typeMoto,
    Integer tarifBase,
    Integer kilometrage,
    Boolean retireParc,
    Boolean actif) {
}
