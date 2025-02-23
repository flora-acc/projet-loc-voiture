package com.accenture.repository.entity;

import com.accenture.model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Administrateur extends UtilisateurConnecte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

}
