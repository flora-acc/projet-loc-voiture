package com.accenture.repository.dao;

import com.accenture.repository.entity.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoitureDao extends JpaRepository<Voiture, Integer> {
}
