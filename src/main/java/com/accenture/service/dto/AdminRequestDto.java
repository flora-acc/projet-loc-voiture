package com.accenture.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


public record AdminRequestDto(     // ce qu'on demande à l'utilisateur en entrée
                              @NotBlank
                              String nom,
                              @NotBlank
                              String prenom,
                              @NotBlank
                              String role,
                              @Email
                              @NotBlank
                              String email,
                              @NotBlank
                              String motDePasse
) {
}
