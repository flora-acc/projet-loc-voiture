package com.accenture.service;


import com.accenture.exception.AdminException;
import com.accenture.repository.AdminDao;
import com.accenture.repository.entity.Administrateur;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.mapper.AdminMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.accenture.model.Role.ADMIN;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminDao adminDao; // final car on ne touche plus au clientDao
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminDao adminDao, AdminMapper adminMapper, PasswordEncoder passwordEncoder) {
        this.adminDao = adminDao;
        this.adminMapper = adminMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /*********************************************
     METHODES PUBLIQUES
     *********************************************/

    @Override
    public List<AdminResponseDto> trouverTous() {
        return adminDao.findAll().stream()
                .map(admin -> adminMapper.toAdminResponseDto(admin))
                .toList();
    }

    @Override
    public AdminResponseDto creerAdmin(AdminRequestDto adminRequestDto) {

        if(adminRequestDto == null)
            throw new AdminException("le paramètre est obligatoire");

        if (adminDao.existsByEmail(adminRequestDto.email())) {
            throw new IllegalArgumentException("Email déjà utilisé !");
        }

        Administrateur admin = adminMapper.toAdministrateur(adminRequestDto);
        admin.setRole(ADMIN);
        String passwordChiffre = passwordEncoder.encode((admin.getMotDePasse()));
        admin.setMotDePasse(passwordChiffre);

        return adminMapper.toAdminResponseDto(adminDao.save(admin));
    }

    @Override
    public void supprimerAdmin(String email, String motDePasse) throws EntityNotFoundException {
        // Vérification des informations d'identification

        Administrateur admin = adminDao.findByEmail(email)
                .orElseThrow(() -> new AdminException("Ce compte ne correspond pas"));

        long nombreAdmins = adminDao.count(); if (nombreAdmins <= 1) {
            throw new AdminException("Il est nécessaire d'avoir au moins un compte administrateur.");
        }
        adminDao.delete(admin);
    }



}
