package com.accenture.repository.entity;

import com.accenture.model.Permis;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client extends UtilisateurConnecte{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adresse_id")
    private Adresse adresse;

//    @NotNull(message = "La date de naissance est obligatoire")
    private LocalDate dateNaissance;

    private LocalDate dateInscription = LocalDate.now(); // Générée automatiquement

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Permis> permis;

    private boolean desactive = false; // Par défaut, actif



    // date d'inscription n'est pas saisie, elle sera générée ultérieurement.
}
