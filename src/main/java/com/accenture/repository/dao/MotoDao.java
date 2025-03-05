package com.accenture.repository.dao;

import com.accenture.repository.entity.Moto;
import com.accenture.repository.entity.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MotoDao extends JpaRepository<Moto, Integer> {

    Optional<Moto> findById(int id);
}
