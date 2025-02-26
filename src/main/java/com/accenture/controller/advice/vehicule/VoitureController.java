package com.accenture.controller.advice.vehicule;

import com.accenture.service.dtoVehicule.VoitureRequestDto;
import com.accenture.service.dtoVehicule.VoitureResponseDto;
import com.accenture.service.vehicule.VoitureService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/voiture")
public class VoitureController {

    private VoitureService voitureService;

    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    @PostMapping("/choix")
    public ResponseEntity<VoitureResponseDto> creation(@Valid @RequestBody VoitureRequestDto voitureRequestDto) {
        return ResponseEntity.ok(voitureService.choisirVoiture(voitureRequestDto));
    }
}
