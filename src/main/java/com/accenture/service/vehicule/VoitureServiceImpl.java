package com.accenture.service.vehicule;

import com.accenture.repository.entity.vehicules.Voiture;
import com.accenture.repository.vehicule.VoitureDao;
import com.accenture.service.dtoVehicule.VoitureRequestDto;
import com.accenture.service.dtoVehicule.VoitureResponseDto;
import com.accenture.service.mapperVehicule.VoitureMapper;
import org.springframework.stereotype.Service;

@Service
public class VoitureServiceImpl implements VoitureService{

    private final VoitureDao voitureDao;
    private final VoitureMapper voitureMapper;

    public VoitureServiceImpl(VoitureDao voitureDao, VoitureMapper voitureMapper) {
        this.voitureDao = voitureDao;
        this.voitureMapper = voitureMapper;
    }

    @Override
    public VoitureResponseDto choisirVoiture(VoitureRequestDto dto) {
        Voiture voiture = voitureMapper.toVoiture(dto);

        Voiture voitureEnreg = voitureDao.save(voiture);
        return voitureMapper.toVoitureResponseDto(voitureEnreg);
    }
}
