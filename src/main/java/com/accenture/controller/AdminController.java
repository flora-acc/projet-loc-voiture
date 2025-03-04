package com.accenture.controller;

import com.accenture.repository.entity.Administrateur;
import com.accenture.service.AdminService;
import com.accenture.service.AdminServiceImpl;
import com.accenture.service.dto.AdminRequestDto;
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
@RequestMapping("/admin")
@Tag(name = "Gestion des administrateurs", description = "Interface de gestion des administrateurs de l'application")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private AdminService adminService;

    public AdminController(AdminService adminService) {

        this.adminService = adminService;
    }

    @Operation(summary = "Afficher tous les administrateurs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'administrateur a été trouvé",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Administrateur.class)) }),
            @ApiResponse(responseCode = "400", description = "L'id de l'administrateur est invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Administrateur introuvable",
                    content = @Content) })
    @GetMapping
    List<AdminResponseDto> afficherAdmins() {
        logger.info("Requête reçue pour récupérer la liste des administrateurs.");
        List<AdminResponseDto> admins = adminService.trouverTous();

        logger.debug("Nombre d'administrateurs trouvés : {}", admins.size());
        return admins;
    }

    @Operation(summary = "Creer un nouvel administrateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'administrateur a été créé avec succès",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Administrateur.class)) }),
            @ApiResponse(responseCode = "400", description = "Les informations saisies sont invalides") })
    @PostMapping
    public ResponseEntity<AdminResponseDto> creerAdmin(@Valid
                                                           @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                   description = "Saisir les informations du nouvel administrateur", required = true,
                                                                   content = @Content(mediaType = "application/json",
                                                                           schema = @Schema(implementation = Administrateur.class),
                                                                           examples = @ExampleObject(value = "{ \"nom\": \"Durand\", \"prenom\": \"Michel\", \"email\": \"email@email.fr\", \"motDePasse\": \"Motdepasse@$\", \"role\": \"ADMIN\" }")))
                                                           @RequestBody AdminRequestDto adminRequestDto) {
        logger.info("Requête reçue pour créer un administrateur avec l'email : {}", adminRequestDto.email());
        return ResponseEntity.ok(adminService.creerAdmin(adminRequestDto));
    }

    @Operation(summary = "Supprimer un administrateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'administrateur a été supprimé avec succès",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Administrateur.class)) }),
            @ApiResponse(responseCode = "400", description = "La suppression est impossible") })
    @DeleteMapping
    ResponseEntity<Void> supprimerAdmin(@RequestParam String email, @RequestParam String motDePasse) {

        adminService.supprimerAdmin(email, motDePasse);
        logger.debug("La suppression de l'administrateur {} a bien été effectuée.", email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // comportement correct, 204 No content = la ressource n'existe plus
    }

    @Operation(summary = "Modifier partiellement un administrateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'administrateur a été modifié avec succès",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Administrateur.class)) }),
            @ApiResponse(responseCode = "400", description = "La modification est impossible") })
    @PatchMapping
    ResponseEntity<AdminResponseDto> modifierAdminPartiellement(@RequestParam String email, @RequestParam String motDePasse, @RequestBody AdminRequestDto adminRequestDto) {
        AdminResponseDto reponse = adminService.modifierAdminPartiellement(email, motDePasse, adminRequestDto);
        return ResponseEntity.ok(reponse);
    }

}
