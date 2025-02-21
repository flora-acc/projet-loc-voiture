package com.accenture.service.mapper;

import com.accenture.repository.entity.Administrateur;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AdresseMapper.class)
public interface AdminMapper {

    Administrateur toAdministrateur(AdminRequestDto adminRequestDto);
    AdminResponseDto toAdminResponseDto (Administrateur admin);
}
