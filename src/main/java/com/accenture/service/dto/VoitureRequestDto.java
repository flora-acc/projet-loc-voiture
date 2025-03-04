package com.accenture.service.dto;

import com.accenture.model.Carburant;
import com.accenture.model.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VoitureRequestDto( // ce qu'on demande à l'utilisateur en entrée
                                 @NotBlank
                                 String marque,
                                 @NotBlank
                                 String modele,
                                 @NotBlank
                                 String couleur,
                                 @NotNull
                                 Integer nbPlaces,
                                 Carburant carburant,
                                @NotNull
                                 Integer nbPortes,
                                @NotBlank
                                String transmission,
                                Boolean climatisation,
                                @NotNull
                                 Integer nbBagages,
                                Type type,
                                 @NotNull
                                 Integer tarifBase,
                                 @NotNull
                                 Integer kilometrage,
                                 @NotNull
                                 Boolean retireParc,
                                 @NotNull
                                 Boolean actif
                                 ) {
}
