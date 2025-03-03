package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;

import java.util.List;

public interface ClientService {

    ClientResponseDto inscrireClient(ClientRequestDto clientRequestDto);
    List<ClientResponseDto> trouverTous();
//    ClientResponseDto modifierClient(String email, String motDePasse, ClientRequestDto clientRequestDto) throws ClientException;  //PUT pour modifier tous les champs
    ClientResponseDto modifierClientPartiellement(String email, String motDePasse, ClientRequestDto clientRequestDto) throws ClientException;   //PATCH modifier seulement certains champs
    void supprimerClient(String email, String motDePasse) throws ClientException;
}
