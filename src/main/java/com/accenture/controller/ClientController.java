package com.accenture.controller;

import com.accenture.repository.entity.Client;
import com.accenture.service.ClientService;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    List<ClientResponseDto> listeClients() {
        return clientService.trouverTous();
    }

    @PostMapping("/inscription")
    public ResponseEntity<ClientResponseDto> inscription(@Valid @RequestBody ClientRequestDto clientRequestDto) {
        return ResponseEntity.ok(clientService.inscrireClient(clientRequestDto));
    }

}
