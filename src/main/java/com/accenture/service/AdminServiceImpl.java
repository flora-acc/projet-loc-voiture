package com.accenture.service;


import com.accenture.exception.AdminException;
import com.accenture.exception.ClientException;
import com.accenture.repository.dao.AdminDao;
import com.accenture.repository.entity.Administrateur;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.AdminMapper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.accenture.model.Role.ADMIN;

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    private final AdminDao adminDao; // final car on ne touche plus au adminDao
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
    public void supprimerAdmin(String email, String motDePasse) throws AdminException {
        // Vérification des informations d'identification
        Administrateur admin = adminDao.findByEmail(email)
                .orElseThrow(() -> new AdminException("Ce compte ne correspond pas"));

        long nombreAdmins = adminDao.count();
        if (nombreAdmins <= 1) {
            throw new AdminException("Il est nécessaire d'avoir au moins un compte administrateur.");
        }

        if (admin.getMotDePasse().equals(motDePasse)) {
            adminDao.delete(admin);
        }
        else throw new ClientException("Identifiants incorrects");

        adminDao.delete(admin);
    }

    @Override
    public AdminResponseDto modifierAdminPartiellement(String email, String motDePasse, AdminRequestDto adminRequestDto) throws AdminException {

        Administrateur adminExistant = adminDao.findByEmail(email)
                .orElseThrow(() -> new AdminException("Ce compte ne correspond pas"));

        Administrateur nouveau = adminMapper.toAdministrateur(adminRequestDto);

        remplacer(nouveau, adminExistant);
        Administrateur adminEnreg = adminDao.save(adminExistant);
        return adminMapper.toAdminResponseDto(adminEnreg);

    }

    /*********************************************
     METHODES PRIVEES
     *********************************************/

    private static void remplacer(Administrateur admin, Administrateur adminExistant) { // si ce qui m'est fourni n'est pas null, je remplace l'existant par le nouveau de la méthode PATCH
        if (admin.getNom() != null)
            adminExistant.setNom(admin.getNom());
        if (admin.getPrenom() != null)
            adminExistant.setPrenom(admin.getPrenom());
        if(admin.getEmail() != null)
            adminExistant.setEmail(admin.getEmail());
        if(admin.getMotDePasse() != null)
            adminExistant.setMotDePasse(admin.getMotDePasse());
    }

}
