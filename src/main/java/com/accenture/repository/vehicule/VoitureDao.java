package com.accenture.repository.vehicule;

import com.accenture.repository.entity.Administrateur;
import com.accenture.repository.entity.vehicules.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoitureDao extends JpaRepository<Voiture, Integer> {
}
