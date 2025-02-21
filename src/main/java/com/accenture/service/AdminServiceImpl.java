package com.accenture.service;


import com.accenture.repository.AdminDao;
import com.accenture.repository.entity.Administrateur;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.mapper.AdminMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminDao adminDao; // final car on ne touche plus au clientDao
    private final AdminMapper adminMapper;

    public AdminServiceImpl(AdminDao adminDao, AdminMapper adminMapper) {
        this.adminDao = adminDao;
        this.adminMapper = adminMapper;
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
    public AdminResponseDto inscrireAdmin(AdminRequestDto adminRequestDto) {
        if (adminDao.existsByEmail(adminRequestDto.email())) {
            throw new IllegalArgumentException("Email déjà utilisé !");
        }

        Administrateur admin = adminMapper.toAdministrateur(adminRequestDto);

        return adminMapper.toAdminResponseDto(adminDao.save(admin));
    }
}
