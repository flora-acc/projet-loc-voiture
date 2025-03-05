package com.accenture.service;

import com.accenture.exception.VehiculeException;
import com.accenture.repository.entity.Voiture;
import com.accenture.repository.dao.VoitureDao;
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
     * @param dto Objet contenant les informations de la voiture à ajouter
     * @return un objet VoitureResponseDto qui représente la voiture à ajouter
     */
    @Override
    public VoitureResponseDto ajouterVoiture(VoitureRequestDto dto) {
        Voiture voiture = voitureMapper.toVoiture(dto);

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
                .orElseThrow(() -> new VehiculeException("Ce compte ne correspond pas"));

        remplacer(voitureRequestDto, voitureExistante);
        Voiture voitureMisAJour = voitureDao.save(voitureExistante);
        return voitureMapper.toVoitureResponseDto(voitureMisAJour);
    }

    /*********************************************
     METHODES PRIVEES
     *********************************************/

    private static void remplacer(VoitureRequestDto voiture, Voiture voitureExistant) { // si ce qui m'est fourni n'est pas null, je remplace l'existant par le nouveau de la méthode PATCH
        if (voiture.marque() != null)
            voitureExistant.setMarque(voiture.marque());
        if (voiture.modele() != null)
            voitureExistant.setModele(voiture.modele());
        if(voiture.couleur() != null)
            voitureExistant.setModele(voiture.couleur());
        if(voiture.nbPlaces() != null)
            voitureExistant.setNbPlaces(voiture.nbPlaces());
        if(voiture.carburant() != null)
            voitureExistant.setCarburant(voiture.carburant());
        if(voiture.nbPortes() != null)
            voitureExistant.setNbPortes(voiture.nbPortes());
        if(voiture.transmission() != null)
            voitureExistant.setTransmission(voiture.transmission());
        if(voiture.climatisation() != null)
            voitureExistant.setClimatisation(voiture.climatisation());
        if(voiture.nbBagages() != null)
            voitureExistant.setNbBagages(voiture.nbBagages());
        if(voiture.type() != null)
            voitureExistant.setType(voiture.type());
        if(voiture.tarifBase() != null)
            voitureExistant.setTarifBase(voiture.tarifBase());
        if(voiture.kilometrage() != null)
            voitureExistant.setKilometrage(voiture.kilometrage());
        if(voiture.actif() != null)
            voitureExistant.setActif(voiture.actif());
        if(voiture.retireParc() != null)
            voitureExistant.setRetireParc(voiture.retireParc());
    }
}
