package com.accenture.service.vehicule;

import com.accenture.repository.entity.vehicules.Voiture;
import com.accenture.repository.vehicule.VoitureDao;
import com.accenture.service.dtoVehicule.VoitureRequestDto;
import com.accenture.service.dtoVehicule.VoitureResponseDto;
import com.accenture.service.mapperVehicule.VoitureMapper;
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
    public List<VoitureResponseDto> listeVoiture() {
        List<Voiture> listeV = voitureDao.findAll();
        return listeV.stream()
                .map(voiture -> voitureMapper.toVoitureResponseDto(voiture))
                .toList();
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
}
