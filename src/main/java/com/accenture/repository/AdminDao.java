package com.accenture.repository;

import com.accenture.repository.entity.Administrateur;
import com.accenture.repository.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminDao extends JpaRepository<Administrateur, Integer> {
    boolean existsByEmail(String email);
    Optional<Client> findByEmail(String email);
}
