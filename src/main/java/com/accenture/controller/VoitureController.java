package com.accenture.controller;

import com.accenture.repository.entity.Administrateur;
import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.Voiture;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.dto.VoitureRequestDto;
import com.accenture.service.dto.VoitureResponseDto;
import com.accenture.service.VoitureService;
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
@RequestMapping("/voitures")
@Tag(name = "Location de voitures", description = "Interface de gestion de location des voitures de l'application")
public class VoitureController {

    private VoitureService voitureService;

    private static final Logger logger = LoggerFactory.getLogger(VoitureController.class);

    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    @Operation(summary = "Afficher toutes les voitures")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des voitures a été trouvée",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Voiture.class)) }),
            @ApiResponse(responseCode = "404", description = "La liste des voitures n'a pas pu être récupérée.",
                    content = @Content) })
    @GetMapping
    List<VoitureResponseDto> trouverToutes(){

        return voitureService.afficherVoitures();
    }

    @Operation(summary = "Afficher une voiture par id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "La voiture a été ajoutée.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class)) }),
            @ApiResponse(responseCode = "400", description = "La voiture n'a pas pu être récupérée.") })
    @GetMapping("/{id}")
    public ResponseEntity<VoitureResponseDto> trouverUneVoiture(int id) {

        VoitureResponseDto voiture = voitureService.trouverVoiture(id);
        return ResponseEntity.ok(voiture);
    }

    @Operation(summary = "Afficher les voitures par filtre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste filtrée des voitures a été trouvée.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Voiture.class)) }),
            @ApiResponse(responseCode = "404", description = "La liste filtrée des voitures n'a pas pu être récupérée.",
                    content = @Content) })
    @GetMapping("/filtre")
    List<VoitureResponseDto> filtrer(
            @RequestParam(required = false) boolean retireParc, //@RequestParam(required = false) rend le paramètre optionnel
            @RequestParam(required = false) boolean actif
    ){
        return voitureService.filtrerVoitures(retireParc,actif);
    }

    @Operation(summary = "Ajouter une voiture dans la liste des voitures en location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'ajout de la voiture a été effectué avec succès.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Voiture.class)) }),
            @ApiResponse(responseCode = "404", description = "L'ajout de la voiture a échoué.",
                    content = @Content) })
    @PostMapping
    public VoitureResponseDto creerVoiture(@Valid
                                               @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                       description = "Saisir les informations du nouveau client", required = true,
                                                       content = @Content(mediaType = "application/json",
                                                               schema = @Schema(implementation = Client.class),
                                                               examples = @ExampleObject(value = """
                                                                       { "marque": "Peugeot", 
                                                                       "modele": "208", 
                                                                       "couleur": "Rouge", 
                                                                       "nbPlaces": 5, 
                                                                       "carburant": "DIESEL", 
                                                                       "nbPortes": 5,
                                                                       "transmission": "MANUELLE", 
                                                                       "climatisation": true, 
                                                                       "nbBagages": 2,
                                                                       "type": "CITADINE",
                                                                       "retireParc": false,
                                                                         "actif": true
                                                                       }
                                                                       """)))
                                               @RequestBody VoitureRequestDto voitureRequestDto) {
        return voitureService.ajouterVoiture(voitureRequestDto);
    }

    @Operation(summary = "Supprimer une voiture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "La voiture a été supprimée avec succès",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Voiture.class)) }),
            @ApiResponse(responseCode = "400", description = "La suppression est impossible") })
    @DeleteMapping
    ResponseEntity<Void> suppr(int id){
        voitureService.supprimerVoiture(id);
        logger.debug("La suppression de la voiture {} a bien été effectuée.", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // comportement correct, 204 No content = la ressource n'existe plus
    }

    @Operation(summary = "Modifier partiellement une voiture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Les informations de la voiture ont été modifiées avec succès",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Voiture.class)) }),
            @ApiResponse(responseCode = "400", description = "La modification est impossible") })
    @PatchMapping
    ResponseEntity<VoitureResponseDto> modifierVoiturePartiellement(int id, @RequestBody VoitureRequestDto voitureRequestDto) {
        VoitureResponseDto reponse = voitureService.modifierVoiturePartiellement(id, voitureRequestDto);
        return ResponseEntity.ok(reponse);
    }
}
