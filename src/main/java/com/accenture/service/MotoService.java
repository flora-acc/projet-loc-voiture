package com.accenture.service;

import com.accenture.service.dto.MotoRequestDto;
import com.accenture.service.dto.MotoResponseDto;

import java.util.List;

public interface MotoService {

    MotoResponseDto ajouterMoto(MotoRequestDto motoRequestDto);
    List<MotoResponseDto> afficherMotos();
}
