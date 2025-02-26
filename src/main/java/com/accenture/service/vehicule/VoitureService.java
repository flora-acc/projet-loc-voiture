package com.accenture.service.vehicule;

import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.dtoVehicule.VoitureRequestDto;
import com.accenture.service.dtoVehicule.VoitureResponseDto;

import java.util.List;

public interface VoitureService {
    VoitureResponseDto ajouterVoiture(VoitureRequestDto voitureRequestDto);
    List<VoitureResponseDto> listeVoiture();
    List<VoitureResponseDto> filtrerVoitures(Boolean retireParc, Boolean actif);
}
