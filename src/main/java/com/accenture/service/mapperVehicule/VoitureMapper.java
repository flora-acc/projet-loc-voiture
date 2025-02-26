package com.accenture.service.mapperVehicule;

import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.vehicules.Voiture;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.dtoVehicule.VoitureRequestDto;
import com.accenture.service.dtoVehicule.VoitureResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoitureMapper {

    Voiture toVoiture(VoitureRequestDto voitureRequestDto);
    VoitureResponseDto toVoitureResponseDto (Voiture voiture);
}
