package com.accenture.service;



import com.accenture.exception.AdminException;
import com.accenture.exception.ClientException;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;

import java.util.List;

public interface AdminService {

    AdminResponseDto creerAdmin(AdminRequestDto adminRequestDto) throws AdminException;;
    List<AdminResponseDto> trouverTous();
    AdminResponseDto modifierAdminPartiellement(String email, String motDePasse, AdminRequestDto adminRequestDto) throws AdminException;
    void supprimerAdmin(String email, String motDePasse) throws AdminException;

}
