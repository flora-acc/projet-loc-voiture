package com.accenture.repository.dao;

import com.accenture.repository.entity.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminDao extends JpaRepository<Administrateur, Integer> {

    boolean existsByEmail(String email);

    Optional<Administrateur> findByEmail(String email);

}
