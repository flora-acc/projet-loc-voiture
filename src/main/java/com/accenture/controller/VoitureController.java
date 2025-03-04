package com.accenture.controller;

import com.accenture.service.dto.VoitureRequestDto;
import com.accenture.service.dto.VoitureResponseDto;
import com.accenture.service.VoitureService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
@Tag(name = "Location de voitures", description = "Interface de gestion de location des voitures de l'application")
public class VoitureController {

    private VoitureService voitureService;

    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    @GetMapping
    List<VoitureResponseDto> tous(){
        return voitureService.afficherVoitures();
    }

    @GetMapping("/filtre")
    List<VoitureResponseDto> filtrer(
            @RequestParam(required = false) boolean retireParc, //@RequestParam(required = false) rend le paramètre optionnel
            @RequestParam(required = false) boolean actif
    ){
        return voitureService.filtrerVoitures(retireParc,actif);
    }

    @PostMapping
    public VoitureResponseDto creerVoiture(@Valid @RequestBody VoitureRequestDto voitureRequestDto) {
        return voitureService.ajouterVoiture(voitureRequestDto);
    }
}
