package com.accenture.service;

import com.accenture.exception.VehiculeException;
import com.accenture.service.dto.MotoRequestDto;
import com.accenture.service.dto.MotoResponseDto;

import java.util.List;

public interface MotoService {

    /**
     * Ajoute une nouvelle moto à la base de données.
     * @param motoRequestDto Les informations de la moto à ajouter.
     * @return La moto ajoutée sous forme de MotoResponseDto.
     */
    MotoResponseDto ajouterMoto(MotoRequestDto motoRequestDto);
    /**
     * Récupère la liste de toutes les motos enregistrées en BDD
     * @return Une liste de MotoResponseDto représentant toutes les motos.
     */
    List<MotoResponseDto> afficherMotos();
    /**
     * Recherche une moto par son identifiant
     * @param id L'identifiant unique de la moto.
     * @return La moto correspondante sous forme de MotoResponseDto
     * @throws VehiculeException Si aucune moto avec l'identifiant donné n'est trouvée.
     */
    MotoResponseDto trouverMoto(int id) throws VehiculeException;
    /**
     * Filtre les motos selon leur statut actif et/ou retrait du parc
     * @param retireParc Indique si la moto est retirée du parc (true) ou non (false).
     * @param actif Indique si la moto est active (true) ou non (false).
     * @return Une liste de MotoResponseDto correspondant aux critères de filtrage
     */
    List<MotoResponseDto> filtrerMotos(Boolean retireParc, Boolean actif);
    /**
     * Supprime une moto en fonction de son identifiant
     * @param id L'identifiant unique de la moto à supprimer.
     * @throws VehiculeException Si la moto à supprimer n'existe pas ou ne peut pas être supprimée.
     */
    void supprimerMoto(int id) throws VehiculeException;
    /**
     * Modifie partiellement les informations d'une moto existante
     * @param id L'identifiant de la moto à modifier.
     * @param motoRequestDto Les nouvelles valeurs à mettre à jour (les champs non fournis restent inchangés).
     * @return La moto mise à jour sous forme de MotoResponseDto.
     * @throws VehiculeException Si la moto avec l'identifiant n'existe pas.
     */
    MotoResponseDto modifierMotoPartiellement(int id, MotoRequestDto motoRequestDto) throws VehiculeException;
}
