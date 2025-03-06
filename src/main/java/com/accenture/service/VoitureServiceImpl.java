package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.exception.VehiculeException;
import com.accenture.repository.entity.Voiture;
import com.accenture.repository.dao.VoitureDao;
import com.accenture.service.dto.MotoRequestDto;
import com.accenture.service.dto.VoitureRequestDto;
import com.accenture.service.dto.VoitureResponseDto;
import com.accenture.service.mapper.VoitureMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoitureServiceImpl implements VoitureService{

    private final VoitureDao voitureDao;
    private final VoitureMapper voitureMapper;

    public VoitureServiceImpl(VoitureDao voitureDao, VoitureMapper voitureMapper) {
        this.voitureDao = voitureDao;
        this.voitureMapper = voitureMapper;
    }

    /*********************************************
     METHODES PUBLIQUES
     *********************************************/

    /**
     * Ajoute une nouvelle voiture dans la base données
     * @param voitureRequestDto Objet contenant les informations de la voiture à ajouter
     * @return un objet VoitureResponseDto qui représente la voiture à ajouter
     */
    @Override
    public VoitureResponseDto ajouterVoiture(VoitureRequestDto voitureRequestDto) {

        verifierVoiture(voitureRequestDto);
        Voiture voiture = voitureMapper.toVoiture(voitureRequestDto);

        Voiture voitureEnreg = voitureDao.save(voiture);
        return voitureMapper.toVoitureResponseDto(voitureEnreg);
    }

    /**
     * Récupère la liste de toutes les voitures dans la base de données
     * @return une liste d'objets VoitureResponseDto représentant toutes les voitures
     */
    @Override
    public List<VoitureResponseDto> afficherVoitures() {
        List<Voiture> listeV = voitureDao.findAll();
        return listeV.stream()
                .map(voiture -> voitureMapper.toVoitureResponseDto(voiture))
                .toList();
    }

    /**
     * Trouve une voiture dans la base de données grâces à son identifiant
     * @param id Identifiant de la voiture à trouver
     * @return Un objet VoitureResponseDto représentant la voiture trouvée
     * @throws VehiculeException si aucune voiture n'est trouvée
     */
    @Override
    public VoitureResponseDto trouverVoiture(int id) throws VehiculeException {
        Voiture voiture = voitureDao.findById(id)
                .orElseThrow(() -> new VehiculeException("Cet id ne correspond à aucune voiture."));

        return voitureMapper.toVoitureResponseDto(voiture);
    }

    /**
     * Filtre les voitures en fonction de leur état (retirée du parc ou active)
     * @param retireParc filtrer les voitures retirées du parc ou non
     * @param actif filtrer les voitures actives ou non
     * @return une liste de VoitureResponseDto correspondant aux voitures filtrées
     */
    @Override
    public List<VoitureResponseDto> filtrerVoitures(Boolean retireParc,Boolean actif) {
        List<Voiture> voitures = voitureDao.findAll();

        if (retireParc != null) { // Vérifie que retireParc est bien précisé
            voitures = voitures.stream()
                    .filter(v -> v.getRetireParc() == retireParc) // compare si l'état de la voiture (true/false) correspond à celui demandé par l'utilisateur.
                    .collect(Collectors.toList());
        }
        // Filtrer les voitures actives (uniquement sur la liste déjà filtrée par retireParc)
        if (actif != null) {
            voitures = voitures.stream()
                    .filter(v -> v.getActif() == actif)
                    .collect(Collectors.toList());
        }
        // Conversion en DTO
        return voitures.stream().map(voitureMapper::toVoitureResponseDto).collect(Collectors.toList());
    }

    /**
     * Supprime une voiture de la base de données en fonction de son identifiant
     * @param id Identifiant de la voiture à supprimer
     * @throws VehiculeException si aucune voiture n'est trouvée pour cet identifiant
     */
    @Override
    public void supprimerVoiture(int id) throws VehiculeException {
        Voiture voiture = voitureDao.findById(id)
                .orElseThrow(() -> new VehiculeException("Cet id ne correspond pas."));

        if (voiture.getActif() != null && voiture.getActif()) {
            throw new VehiculeException("Impossible de supprimer une voiture en location.");
        }

        voitureDao.delete(voiture);
    }

    /**
     * Met à jour partiellement une voiture existante dans la base de données
     * @param id Identifiant de la voiture à mettre à jour
     * @param voitureRequestDto Objet contenant les informations à mettre à jour
     * @return Un objet VoitureResponseDto représentant la voiture mise à jour
     * @throws VehiculeException Si aucune voiture n'est trouvée pour cet id
     */
    @Override
    public VoitureResponseDto modifierVoiturePartiellement(int id, VoitureRequestDto voitureRequestDto) throws VehiculeException {

        Voiture voitureExistante = voitureDao.findById(id)
                .orElseThrow(() -> new VehiculeException("Ce véhicule n'est pas identifié"));

        remplacer(voitureRequestDto, voitureExistante);
        Voiture voitureMisAJour = voitureDao.save(voitureExistante);
        return voitureMapper.toVoitureResponseDto(voitureMisAJour);
    }

    /*********************************************
     METHODES PRIVEES
     *********************************************/

    // Vérification pour les String
    private void verifierChamp(String champ, String messageErreur) throws VehiculeException {
        if (champ == null || champ.isBlank()) {
            throw new VehiculeException(messageErreur);
        }
    }

    // Vérification pour les Enums, Integer et autres objets
    private void verifierChamp(Object champ, String messageErreur) throws VehiculeException {
        if (champ == null) {
            throw new VehiculeException(messageErreur);
        }

        if (champ instanceof Integer valeur) {
            if (valeur < 0) {
                throw new VehiculeException("La valeur ne peut pas être négative.");
            }
        }
    }

    private void verifierVoiture(VoitureRequestDto voitureRequestDto) throws VehiculeException {
        if (voitureRequestDto == null) {
            throw new VehiculeException("Merci de compléter les informations.");
        }

        verifierChamp(voitureRequestDto.marque(), "Merci d'indiquer la marque.");
        verifierChamp(voitureRequestDto.modele(), "Merci d'indiquer le modele.");
        verifierChamp(voitureRequestDto.couleur(), "Merci d'indiquer la couleur du vehicule.");
        verifierChamp(voitureRequestDto.nbPlaces(), "Merci d'ajouter le nombre de places.");
        verifierChamp(voitureRequestDto.carburant(), "Merci d'ajouter le carburant.");
        verifierChamp(voitureRequestDto.nbPortes(), "Merci d'ajouter le nombre de portes.");
        verifierChamp(voitureRequestDto.transmission(), "Merci d'ajouter le type de transmission: AUTO ou MANUELLE");
        verifierChamp(voitureRequestDto.climatisation(), "Merci d'ajouter la climatisation.");
        verifierChamp(voitureRequestDto.nbBagages(), "Merci d'ajouter le nombre de bagages possible.");
        verifierChamp(voitureRequestDto.type(), "Merci d'ajouter le type.");
        verifierChamp(voitureRequestDto.tarifBase(), "Merci d'ajouter le tarif de base.");
        verifierChamp(voitureRequestDto.kilometrage(), "Merci d'ajouter le kilometrage.");
        verifierChamp(voitureRequestDto.retireParc(), "Merci d'indiquer si la voiture est retirée du parc.");
        verifierChamp(voitureRequestDto.actif(), "Merci d'indiquer si la moto est en location.");
    }

    private static void remplacer(VoitureRequestDto voiture, Voiture voitureExistante) {
        if (estValide(voiture.marque()))
            voitureExistante.setMarque(voiture.marque());
        if (estValide(voiture.modele()))
            voitureExistante.setModele(voiture.modele());
        if (estValide(voiture.couleur()))
            voitureExistante.setCouleur(voiture.couleur());
        if (voiture.nbPlaces() != null)
            voitureExistante.setNbPlaces(voiture.nbPlaces());
        if (voiture.carburant() != null)
            voitureExistante.setCarburant(voiture.carburant());
        if (voiture.nbPortes() != null)
            voitureExistante.setNbPortes(voiture.nbPortes());
        if (voiture.transmission() != null)
            voitureExistante.setTransmission(voiture.transmission());
        if (voiture.climatisation() != null)
            voitureExistante.setClimatisation(voiture.climatisation());
        if (voiture.nbBagages() != null)
            voitureExistante.setNbBagages(voiture.nbBagages());
        if (voiture.type() != null)
            voitureExistante.setType(voiture.type());
        if (voiture.tarifBase() != null)
            voitureExistante.setTarifBase(voiture.tarifBase());
        if (voiture.kilometrage() != null)
            voitureExistante.setKilometrage(voiture.kilometrage());
        if (voiture.actif() != null)
            voitureExistante.setActif(voiture.actif());
        if (voiture.retireParc() != null)
            voitureExistante.setRetireParc(voiture.retireParc());
    }

    // Vérifie que la valeur est non nulle, non vide et n'est pas un placeholder
    private static boolean estValide(String valeur) {
        return valeur != null && !valeur.trim().isEmpty() && !"string".equalsIgnoreCase(valeur.trim());
    }
}
