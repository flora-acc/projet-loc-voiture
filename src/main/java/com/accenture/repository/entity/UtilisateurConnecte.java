package com.accenture.repository.entity;

import com.accenture.model.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class UtilisateurConnecte {
    private String nom;
    private String prenom;

    @Column(unique = true, nullable = false) // Ajout de l'unicité
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    private Role role; // Exemple : SUPER_ADMIN, GESTIONNAIRE, etc.

}
