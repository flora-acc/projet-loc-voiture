package com.accenture.service.dtoVehicule;

import com.accenture.model.Carburant;
import com.accenture.model.Type;
import jakarta.validation.constraints.NotBlank;

public record VoitureRequestDto( // ce qu'on demande à l'utilisateur en entrée
                                 @NotBlank
                                 String marque,
                                 @NotBlank
                                 String modele,
                                 @NotBlank
                                 String couleur,
                                 @NotBlank
                                 int nbPlaces,
                                 @NotBlank
                                 Carburant carburant,
                                @NotBlank
                                int nbPortes,
                                @NotBlank
                                String transmission,
                                @NotBlank
                                boolean climatisation,
                                @NotBlank
                                int nbBagages,
                                @NotBlank
                                Type type
                                 ) {
}
