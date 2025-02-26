package com.accenture.service.vehicule;

import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.dtoVehicule.VoitureRequestDto;
import com.accenture.service.dtoVehicule.VoitureResponseDto;

public interface VoitureService {
    VoitureResponseDto choisirVoiture(VoitureRequestDto voitureRequestDto);
}
