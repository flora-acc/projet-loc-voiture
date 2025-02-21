package com.accenture.service.dto;


import jakarta.validation.constraints.NotBlank;

public record AdresseRequestDto( // ce qu'on demande à l'utilisateur en entrée

                                        @NotBlank
                                        String rue,
                                        @NotBlank
                                        String codePostal,
                                        @NotBlank
                                        String ville)
{
}
