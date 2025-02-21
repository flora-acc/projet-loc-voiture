package com.accenture.service;

import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;

import java.util.List;

public interface ClientService {

    ClientResponseDto inscrireClient(ClientRequestDto clientRequestDto);

    List<ClientResponseDto> trouverTous();
}
