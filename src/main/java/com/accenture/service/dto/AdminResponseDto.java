package com.accenture.service.dto;

public record AdminResponseDto(
        String nom,
        String prenom,
        String role,
        String email
) {
}
