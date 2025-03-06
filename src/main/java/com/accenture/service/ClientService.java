package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;

import java.util.List;

public interface ClientService {

    /**
     * Enregistre un nouveau client dans la base de données
     * @param clientRequestDto Les informations du client à inscrire
     * @return Le client inscrit sous forme de ClientResponseDto.
     * @throws ClientException Si l'inscription échoue
     */
    ClientResponseDto inscrireClient(ClientRequestDto clientRequestDto) throws ClientException;

    /**
     * Récupère la liste de tous les clients enregistrés
     * @return Une liste de ClientResponseDto représentant tous les clients
     * @throws ClientException Si une erreur survient à la récupération des clients
     */
    List<ClientResponseDto> trouverTous() throws ClientException;
    /**
     * Trouve un client à partir de son email et de son mot de passe
     * @param email L'email du client
     * @param motDePasse Le mot de passe du client
     * @return Le client correspondant sous forme de ClientResponseDto
     * @throws ClientException Si l'authentification échoue
     */
    ClientResponseDto trouverClient(String email, String motDePasse) throws ClientException;

    /**
     * Modifie partiellement les informations d'un client existant
     * @param email L'email du client à modifier
     * @param motDePasse Le mot de passe du client pour vérification
     * @param clientRequestDto Les nouvelles valeurs à mettre à jour (les champs non fournis restent inchangés)
     * @return Le client mis à jour sous forme de ClientResponseDto
     * @throws ClientException Si le client n'existe pas ou si l'authentification échoue.
     */
    ClientResponseDto modifierClientPartiellement(String email, String motDePasse, ClientRequestDto clientRequestDto) throws ClientException;   //PATCH modifier seulement certains champs

    /**
     * Supprime un client à partir de son email et de son mot de passe
     * @param email L'email du client à supprimer
     * @param motDePasse Le mot de passe du client pour vérification
     * @throws ClientException Si le client n'existe pas ou si la suppression échoue
     */
    void supprimerClient(String email, String motDePasse) throws ClientException;
}
