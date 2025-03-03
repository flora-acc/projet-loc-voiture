package com.accenture.service;

import com.accenture.service.dto.VoitureRequestDto;
import com.accenture.service.dto.VoitureResponseDto;

import java.util.List;

public interface VoitureService {
    VoitureResponseDto ajouterVoiture(VoitureRequestDto voitureRequestDto);
    List<VoitureResponseDto> afficherVoitures();
    List<VoitureResponseDto> filtrerVoitures(Boolean retireParc, Boolean actif);
}
