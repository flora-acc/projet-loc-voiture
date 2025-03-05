package com.accenture.service.dto;


import com.accenture.model.Cylindree;
import com.accenture.model.Type;
import com.accenture.model.TypeMoto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MotoRequestDto (@NotBlank
                             String marque,
                             @NotBlank
                             String modele,
                             @NotBlank
                             String couleur,
                             Cylindree cylindree,
                             @NotNull
                             Integer poids,
                              @NotNull
                              Integer puissanceKw,
                              @NotBlank
                              String hauteurSelle,
                             @NotBlank
                             String transmission,
                             TypeMoto type,
                             @NotNull
                             Integer tarifBase,
                             @NotNull
                             Integer kilometrage,
                             @NotNull
                             Boolean retireParc,
                             @NotNull
                             Boolean actif) {

}
