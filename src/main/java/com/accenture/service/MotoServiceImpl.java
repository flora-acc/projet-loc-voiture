package com.accenture.service;


import com.accenture.exception.ClientException;
import com.accenture.exception.VehiculeException;
import com.accenture.repository.dao.MotoDao;
import com.accenture.repository.entity.Moto;
import com.accenture.repository.entity.Voiture;
import com.accenture.service.dto.MotoRequestDto;
import com.accenture.service.dto.MotoResponseDto;
import com.accenture.service.dto.VoitureResponseDto;
import com.accenture.service.mapper.MotoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoServiceImpl implements MotoService {

    private final MotoDao motoDao;
    private final MotoMapper motoMapper;

    public MotoServiceImpl(MotoDao motoDao, MotoMapper motoMapper) {
        this.motoDao = motoDao;
        this.motoMapper = motoMapper;
    }

    @Override
    public MotoResponseDto ajouterMoto(MotoRequestDto motoRequestDto) throws VehiculeException {

        if(motoRequestDto == null)
            throw new VehiculeException("Informations à compléter");
        Moto moto = motoMapper.toMoto(motoRequestDto);

        Moto motoEnreg = motoDao.save(moto);
        return motoMapper.toMotoResponseDto(motoEnreg);
    }

    @Override
    public List<MotoResponseDto> afficherMotos() {
        List<Moto> listeM = motoDao.findAll();
        return listeM.stream()
                .map(moto -> motoMapper.toMotoResponseDto(moto))
                .toList();
    }
}
