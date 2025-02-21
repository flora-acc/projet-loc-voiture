package com.accenture.service.mapper;

import com.accenture.repository.entity.Adresse;
import com.accenture.service.dto.AdresseRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdresseMapper {

    @Mapping(target = "id", ignore = true) // On ignore l'ID pour éviter le problème Hibernate
    Adresse toAdresse(AdresseRequestDto adresseRequestDto);
}
