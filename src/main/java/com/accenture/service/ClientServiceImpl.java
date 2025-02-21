package com.accenture.service;

import com.accenture.repository.entity.Client;
import com.accenture.repository.ClientDao;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.ClientMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao; // final car on ne touche plus au clientDao
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientDao clientDao, ClientMapper clientMapper) {
        this.clientDao = clientDao;
        this.clientMapper = clientMapper;
    }

    /*********************************************
     METHODES PUBLIQUES
     *********************************************/

    @Override
    public List<ClientResponseDto> trouverTous() {
        return clientDao.findAll().stream()
                .map(client -> clientMapper.toClientResponseDto(client))
                .toList();
    }

    @Override
    public ClientResponseDto inscrireClient(ClientRequestDto clientRequestDto) {
        if (clientDao.existsByEmail(clientRequestDto.email())) {
            throw new IllegalArgumentException("Email déjà utilisé !");
        }

        Client client = clientMapper.toClient(clientRequestDto);

        return clientMapper.toClientResponseDto(clientDao.save(client));
    }

    //   Vérification de la majorité (18 ans)
    // private boolean verifierMajorite(LocalDate dateNaissance) {
    //        return dateNaissance.plusYears(18).isBefore(LocalDate.now()) || dateNaissance.plusYears(18).isEqual(LocalDate.now());
    //    }
}
