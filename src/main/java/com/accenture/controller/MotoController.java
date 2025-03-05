package com.accenture.controller;

import com.accenture.repository.entity.Moto;
import com.accenture.service.MotoService;
import com.accenture.service.dto.MotoRequestDto;
import com.accenture.service.dto.MotoResponseDto;
import com.accenture.service.dto.VoitureResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motos")
@Tag(name = "Location de motos", description = "Interface de gestion de location des motos de l'application")
public class MotoController {

    private MotoService motoService;

    private static final Logger logger = LoggerFactory.getLogger(MotoController.class);

    public MotoController(MotoService motoService) {
        this.motoService = motoService;
    }

    @Operation(summary = "Afficher toutes les motos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des motos a été trouvée",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Moto.class)) }),
            @ApiResponse(responseCode = "404", description = "La liste des motos n'a pas pu être récupérée.",
                    content = @Content) })
    @GetMapping
    List<MotoResponseDto> trouverToutes(){

        return motoService.afficherMotos();
    }

    @Operation(summary = "Ajouter une moto dans la liste des motos en location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'ajout de la moto a été effectué avec succès.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Moto.class)) }),
            @ApiResponse(responseCode = "404", description = "L'ajout de la moto a échoué.",
                    content = @Content) })
    @PostMapping
    public MotoResponseDto creerMoto(@Valid
                                           @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                   description = "Saisir les informations de la nouvelle moto", required = true,
                                                   content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = Moto.class),
                                                           examples = @ExampleObject(value = """
                                                                       { "marque": "Kawasaki", 
                                                                       "modele": "Ninja ZX-6R", 
                                                                       "couleur": "Vert", 
                                                                       "cylindree": "636cc", 
                                                                       "poids": 195, 
                                                                       "puissanceKw": 103, 
                                                                       "hauteurSelle": "830mm", 
                                                                       "transmission": "Manuelle", 
                                                                       "type": "Sportive", 
                                                                       "tarifBase": 130, 
                                                                       "kilometrage": 8000, 
                                                                       "retireParc": false, 
                                                                       "actif": true }
                                                                       """)))
                                           @RequestBody MotoRequestDto motoRequestDto) {
        return motoService.ajouterMoto(motoRequestDto);
    }

}
