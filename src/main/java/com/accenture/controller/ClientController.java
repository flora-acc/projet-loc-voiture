package com.accenture.controller;

import com.accenture.repository.entity.Administrateur;
import com.accenture.repository.entity.Client;
import com.accenture.service.ClientService;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
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
@RequestMapping("/client")
@Tag(name = "Gestion des clients", description = "Interface de gestion des clients de l'application")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private ClientService clientService;

    public ClientController(ClientService clientService) {

        this.clientService = clientService;
    }

    @Operation(summary = "Afficher tous les clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des clients a été trouvée",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class)) }),
            @ApiResponse(responseCode = "404", description = "La liste des clients n'a pas pu être récupérée.",
                    content = @Content) })
    @GetMapping
    List<ClientResponseDto> afficherClients() {
        logger.info("Requête reçue pour récupérer la liste des clients.");
        List<ClientResponseDto> clients = clientService.trouverTous();

        logger.debug("Nombre de clients trouvés : {}", clients.size());
        return clients;
    }

    @Operation(summary = "Récupérer ses informations en tant que client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Les informations ont été récupérées.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class)) }),
            @ApiResponse(responseCode = "400", description = "Les informations saisies sont invalides") })
    @GetMapping("/moncompte")
    public ResponseEntity<ClientResponseDto> trouverUnClient(
            @RequestParam String email,
            @RequestParam String motDePasse) {

        ClientResponseDto client = clientService.trouverClient(email, motDePasse);
        return ResponseEntity.ok(client);
    }

    @Operation(summary = "S'inscrire en tant que nouveau client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'inscription client s'est faite avec succès",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class)) }),
            @ApiResponse(responseCode = "400", description = "Les informations saisies sont invalides") })
    @PostMapping
    public ResponseEntity<ClientResponseDto> inscription(@Valid
                                                             @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                     description = "Saisir les informations du nouveau client", required = true,
                                                                     content = @Content(mediaType = "application/json",
                                                                             schema = @Schema(implementation = Client.class),
//                                                                             examples = @ExampleObject(value = "{ \"nom\": \"Durand\", \"prenom\": \"Michel\", \"email\": \"email@email.fr\", \"motDePasse\": \"Motdepasse@$\", \"rue\": \"1 rue des eglantines\", \"codePostal\": \"56000\", \"ville\": \"Vannes\", \"dateNaissance\": \"1992-07-26\" }")))
                                                                             examples = @ExampleObject(value = """
                                                                                     {
                                                                                       "nom": "Durand",
                                                                                       "prenom": "Michel",
                                                                                       "email": "exemple@email.fr",
                                                                                       "motDePasse": "Exemple",
                                                                                       "adresse": {
                                                                                         "rue": "1 rue de la paix",
                                                                                         "codePostal": "75001",
                                                                                         "ville": "Paris"
                                                                                       },
                                                                                       "dateNaissance": "1999-03-04",
                                                                                       "permis": [
                                                                                         "B"
                                                                                       ]
                                                                                     }
                                                                                     """)))
                                                             @RequestBody ClientRequestDto clientRequestDto) {
        logger.info("Requête reçue pour inscrire un client avec l'email : {}", clientRequestDto.email());
        return ResponseEntity.ok(clientService.inscrireClient(clientRequestDto));
    }

    @Operation(summary = "Supprimer un client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Le client a été supprimé avec succès",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class)) }),
            @ApiResponse(responseCode = "400", description = "La suppression est impossible") })
    @DeleteMapping
    ResponseEntity<Void> suppr(@RequestParam String email, @RequestParam String motDePasse){
        clientService.supprimerClient(email, motDePasse);
        logger.debug("La suppression du client {} a bien été effectuée.", email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // comportement correct, 204 No content = la ressource n'existe plus
    }


    @Operation(summary = "Modifier partiellement un client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Le client a été modifié avec succès",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Administrateur.class)) }),
            @ApiResponse(responseCode = "400", description = "La modification est impossible") })
    @PatchMapping
    ResponseEntity<ClientResponseDto> modifierClientPartiellement(@RequestParam String email, @RequestParam String motDePasse, @RequestBody ClientRequestDto clientRequestDto) {
        ClientResponseDto reponse = clientService.modifierClientPartiellement(email, motDePasse, clientRequestDto);
        return ResponseEntity.ok(reponse);
    }

}
