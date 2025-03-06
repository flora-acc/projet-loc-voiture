package com.accenture.controller;


import com.accenture.repository.entity.Moto;
import com.accenture.service.MotoService;
import com.accenture.service.dto.MotoRequestDto;
import com.accenture.service.dto.MotoResponseDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motos")
@Tag(name = "Motos", description = "Gestion des motos")
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

    @Operation(summary = "Afficher une moto par id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "La moto a été ajoutée.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Moto.class)) }),
            @ApiResponse(responseCode = "400", description = "La moto n'a pas pu être récupérée.") })
    @GetMapping("/{id}")
    public ResponseEntity<MotoResponseDto> trouverUneMoto(int id) {

        MotoResponseDto moto = motoService.trouverMoto(id);
        return ResponseEntity.ok(moto);
    }

    @Operation(summary = "Afficher les motos par filtre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste filtrée des motos a été trouvée.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Moto.class)) }),
            @ApiResponse(responseCode = "404", description = "La liste filtrée des motos n'a pas pu être récupérée.",
                    content = @Content) })
    @GetMapping("/filtre")
    List<MotoResponseDto> filtrer(
            @RequestParam(required = false) boolean retireParc, //@RequestParam(required = false) rend le paramètre optionnel
            @RequestParam(required = false) boolean actif
    ){
        return motoService.filtrerMotos(retireParc,actif);
    }

    @Operation(summary = "Supprimer une moto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "La moto a été supprimée avec succès",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Moto.class)) }),
            @ApiResponse(responseCode = "400", description = "La suppression est impossible") })
    @DeleteMapping
    ResponseEntity<Void> suppr(int id){
        motoService.supprimerMoto(id);
        logger.debug("La suppression de la moto {} a bien été effectuée.", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // comportement correct, 204 No content = la ressource n'existe plus
    }

    @Operation(summary = "Modifier partiellement une moto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Les informations de la moto ont été modifiées avec succès",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Moto.class)) }),
            @ApiResponse(responseCode = "400", description = "La modification est impossible") })
    @PatchMapping("/{id}")
    ResponseEntity<MotoResponseDto> modifierMotoPartiellement(@PathVariable int id, @RequestBody MotoRequestDto motoRequestDto) {
        MotoResponseDto reponse = motoService.modifierMotoPartiellement(id, motoRequestDto);
        return ResponseEntity.ok(reponse);
    }

}
