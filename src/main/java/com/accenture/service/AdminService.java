package com.accenture.service;



import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;

import java.util.List;

public interface AdminService {
    AdminResponseDto inscrireAdmin(AdminRequestDto adminRequestDto);

    List<AdminResponseDto> trouverTous();
}
