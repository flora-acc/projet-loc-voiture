package com.accenture.service;



import com.accenture.exception.AdminException;
import com.accenture.exception.ClientException;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface AdminService {
    AdminResponseDto creerAdmin(AdminRequestDto adminRequestDto);

    List<AdminResponseDto> trouverTous();

    void supprimerAdmin(String email, String motDePasse) throws ClientException;

}
