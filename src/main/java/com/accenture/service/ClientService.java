package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;

import java.util.List;

public interface ClientService {
    ClientResponseDto inscrireClient(ClientRequestDto clientRequestDto) throws ClientException;
    List<ClientResponseDto> trouverTous() throws ClientException;
    ClientResponseDto trouverClient(String email, String motDePasse) throws ClientException;
//    ClientResponseDto modifierClient(String email, String motDePasse, ClientRequestDto clientRequestDto) throws ClientException;  //PUT pour modifier tous les champs
    ClientResponseDto modifierClientPartiellement(String email, String motDePasse, ClientRequestDto clientRequestDto) throws ClientException;   //PATCH modifier seulement certains champs
    void supprimerClient(String email, String motDePasse) throws ClientException;
}
