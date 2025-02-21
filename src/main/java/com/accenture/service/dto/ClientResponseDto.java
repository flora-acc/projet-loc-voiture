package com.accenture.service.dto;

import com.accenture.model.Permis;
import com.accenture.repository.entity.Adresse;

import java.time.LocalDate;
import java.util.List;

public record ClientResponseDto(
        int id,
        String nom,
        String prenom,
        Adresse adresse,
        String email,
        LocalDate dateNaissance,
        List<Permis> permis
) {
}
