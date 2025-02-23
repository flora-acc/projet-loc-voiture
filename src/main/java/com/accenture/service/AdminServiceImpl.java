package com.accenture.service;


import com.accenture.repository.AdminDao;
import com.accenture.repository.entity.Administrateur;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.mapper.AdminMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (adminDao.existsByEmail(adminRequestDto.email())) {
            throw new IllegalArgumentException("Email déjà utilisé !");
        }

        Administrateur admin = adminMapper.toAdministrateur(adminRequestDto);
        admin.setRole(ADMIN);
        String passwordChiffre = passwordEncoder.encode((admin.getMotDePasse()));
        admin.setMotDePasse(passwordChiffre);

        return adminMapper.toAdminResponseDto(adminDao.save(admin));
    }

}
