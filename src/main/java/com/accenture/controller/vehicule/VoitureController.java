package com.accenture.controller.vehicule;

import com.accenture.service.dtoVehicule.VoitureRequestDto;
import com.accenture.service.dtoVehicule.VoitureResponseDto;
import com.accenture.service.vehicule.VoitureService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class VoitureController {

    private VoitureService voitureService;

    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    @GetMapping
    List<VoitureResponseDto> tous(){
        return voitureService.listeVoiture();
    }

    @GetMapping("/filtre")
    List<VoitureResponseDto> filtrer(
            @RequestParam(required = false) boolean retireParc, //@RequestParam(required = false) rend le paramètre optionnel
            @RequestParam(required = false) boolean actif
    ){
        return voitureService.filtrerVoitures(retireParc,actif);
    }

    @PostMapping("/voiture")
    public ResponseEntity<VoitureResponseDto> creation(@Valid @RequestBody VoitureRequestDto voitureRequestDto) {
        return ResponseEntity.ok(voitureService.ajouterVoiture(voitureRequestDto));
    }
}
