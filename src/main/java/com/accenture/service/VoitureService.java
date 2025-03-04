package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.exception.VehiculeException;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.dto.VoitureRequestDto;
import com.accenture.service.dto.VoitureResponseDto;

import java.util.List;

public interface VoitureService {

    VoitureResponseDto ajouterVoiture(VoitureRequestDto voitureRequestDto);
    List<VoitureResponseDto> afficherVoitures();
    VoitureResponseDto trouverVoiture(int id) throws VehiculeException;
    List<VoitureResponseDto> filtrerVoitures(Boolean retireParc, Boolean actif);
    void supprimerVoiture(int id) throws VehiculeException;
}
