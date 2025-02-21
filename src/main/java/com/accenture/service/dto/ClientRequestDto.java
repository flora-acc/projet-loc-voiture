package com.accenture.service.dto;

import com.accenture.model.Permis;
import com.accenture.repository.entity.Adresse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record ClientRequestDto(     // ce qu'on demande à l'utilisateur en entrée
                    @NotBlank
                    String nom,
                    @NotBlank
                    String prenom,
                    AdresseRequestDto adresse,
                    @Email
                    @NotBlank
                    String email,
                    @NotBlank
                    String motDePasse,
                    @NotNull
                    LocalDate dateNaissance,
                    List<Permis> permis           // Peut être vide
) {

}
