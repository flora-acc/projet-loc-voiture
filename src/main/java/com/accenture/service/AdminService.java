package com.accenture.service;



import com.accenture.exception.AdminException;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;

import java.util.List;

public interface AdminService {
    AdminResponseDto creerAdmin(AdminRequestDto adminRequestDto);

    List<AdminResponseDto> trouverTous();

    void supprimerAdmin(AdminRequestDto adminRequestDto) throws AdminException;
}
