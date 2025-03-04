package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.exception.VehiculeException;
import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.Voiture;
import com.accenture.repository.dao.VoitureDao;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.dto.VoitureRequestDto;
import com.accenture.service.dto.VoitureResponseDto;
import com.accenture.service.mapper.VoitureMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoitureServiceImpl implements VoitureService{

    private final VoitureDao voitureDao;
    private final VoitureMapper voitureMapper;

    public VoitureServiceImpl(VoitureDao voitureDao, VoitureMapper voitureMapper) {
        this.voitureDao = voitureDao;
        this.voitureMapper = voitureMapper;
    }

    @Override
    public VoitureResponseDto ajouterVoiture(VoitureRequestDto dto) {
        Voiture voiture = voitureMapper.toVoiture(dto);

        Voiture voitureEnreg = voitureDao.save(voiture);
        return voitureMapper.toVoitureResponseDto(voitureEnreg);
    }

    @Override
    public List<VoitureResponseDto> afficherVoitures() {
        List<Voiture> listeV = voitureDao.findAll();
        return listeV.stream()
                .map(voiture -> voitureMapper.toVoitureResponseDto(voiture))
                .toList();
    }

    @Override
    public VoitureResponseDto trouverVoiture(int id) throws VehiculeException {
        Voiture voiture = voitureDao.findById(id)
                .orElseThrow(() -> new VehiculeException("Cet id ne correspond à aucune voiture."));

        return voitureMapper.toVoitureResponseDto(voiture);
    }

    public List<VoitureResponseDto> filtrerVoitures(Boolean retireParc,Boolean actif) {
        List<Voiture> voitures = voitureDao.findAll();

        if (retireParc != null) { // Vérifie que retireParc est bien précisé
            voitures = voitures.stream()
                    .filter(v -> v.getRetireParc() == retireParc) // compare si l'état de la voiture (true/false) correspond à celui demandé par l'utilisateur.
                    .collect(Collectors.toList());
        }
        // Filtrer les voitures actives (uniquement sur la liste déjà filtrée par retireParc)
        if (actif != null) {
            voitures = voitures.stream()
                    .filter(v -> v.getActif() == actif)
                    .collect(Collectors.toList());
        }
        // Conversion en DTO
        return voitures.stream().map(voitureMapper::toVoitureResponseDto).collect(Collectors.toList());
    }

    @Override
    public void supprimerVoiture(int id) throws VehiculeException {
        Voiture voiture = voitureDao.findById(id)
                .orElseThrow(() -> new VehiculeException("Cet id ne correspond pas."));

            voitureDao.delete(voiture);
    }
}
