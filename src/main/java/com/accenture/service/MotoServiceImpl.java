package com.accenture.service;


import com.accenture.exception.ClientException;
import com.accenture.exception.VehiculeException;
import com.accenture.repository.dao.MotoDao;
import com.accenture.repository.entity.Moto;
import com.accenture.repository.entity.Voiture;
import com.accenture.service.dto.*;
import com.accenture.service.mapper.MotoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MotoServiceImpl implements MotoService {

    private final MotoDao motoDao;
    private final MotoMapper motoMapper;

    public MotoServiceImpl(MotoDao motoDao, MotoMapper motoMapper) {
        this.motoDao = motoDao;
        this.motoMapper = motoMapper;
    }

    /*********************************************
     METHODES PUBLIQUES
     *********************************************/


    @Override
    public MotoResponseDto ajouterMoto(MotoRequestDto motoRequestDto) throws VehiculeException {

        verifierMoto(motoRequestDto);
        Moto moto = motoMapper.toMoto(motoRequestDto);

        Moto motoEnreg = motoDao.save(moto);
        return motoMapper.toMotoResponseDto(motoEnreg);
    }

    @Override
    public List<MotoResponseDto> afficherMotos() { // ne renvoie pas d'exception car méthode findAll
        List<Moto> listeM = motoDao.findAll();
        return listeM.stream()
                .map(moto -> motoMapper.toMotoResponseDto(moto))
                .toList();
    }

    @Override
    public MotoResponseDto trouverMoto(int id) throws VehiculeException {
        Moto moto = motoDao.findById(id)
                .orElseThrow(() -> new VehiculeException("Cet id ne correspond à aucune moto."));

        return motoMapper.toMotoResponseDto(moto);
    }

    @Override
    public List<MotoResponseDto> filtrerMotos(Boolean retireParc,Boolean actif) {
        List<Moto> motos = motoDao.findAll();

        if (retireParc != null) { // Vérifie que retireParc est bien précisé
            motos = motos.stream()
                    .filter(v -> v.getRetireParc() == retireParc) // compare si l'état de la moto (true/false) correspond à celui demandé par l'utilisateur.
                    .collect(Collectors.toList());
        }
        // Filtrer les motos actives (uniquement sur la liste déjà filtrée par retireParc)
        if (actif != null) {
            motos = motos.stream()
                    .filter(v -> v.getActif() == actif)
                    .collect(Collectors.toList());
        }
        // Conversion en DTO
        return motos.stream()
                .map(motoMapper::toMotoResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void supprimerMoto(int id) throws VehiculeException {
        Moto moto = motoDao.findById(id)
                .orElseThrow(() -> new VehiculeException("Cet id ne correspond pas."));

        if (moto.getActif() != null && moto.getActif()) {
            throw new VehiculeException("Impossible de supprimer une moto en location.");
        }

        motoDao.delete(moto);
    }

    @Override
    public MotoResponseDto modifierMotoPartiellement(int id, MotoRequestDto motoRequestDto) throws VehiculeException {

        Moto motoExistante = motoDao.findById(id)
                .orElseThrow(() -> new VehiculeException("Ce véhicule n'est pas identifié"));

        remplacer(motoRequestDto, motoExistante);
        Moto motoMiseAJour = motoDao.save(motoExistante);
        return motoMapper.toMotoResponseDto(motoMiseAJour);
    }

    /*********************************************
     METHODES PRIVEES
     *********************************************/

    private static void remplacer(MotoRequestDto moto, Moto motoExistante) { // si ce qui m'est fourni n'est pas null, je remplace l'existant par le nouveau de la méthode PATCH
        if (moto.marque() != null)
            motoExistante.setMarque(moto.marque());
        if (moto.modele() != null)
            motoExistante.setModele(moto.modele());
        if(moto.couleur() != null)
            motoExistante.setCouleur(moto.couleur());
        if(moto.cylindree() != null)
            motoExistante.setCylindree(moto.cylindree());
        if(moto.poids() != null)
            motoExistante.setPoids(moto.poids());
        if(moto.puissanceKw() != null)
            motoExistante.setPuissanceKw(moto.puissanceKw());
        if(moto.transmission() != null)
            motoExistante.setTransmission(moto.transmission());
        if(moto.hauteurSelle() != null)
            motoExistante.setHauteurSelle(moto.hauteurSelle());
        if(moto.transmission() != null)
            motoExistante.setTransmission(moto.transmission());
        if(moto.typeMoto() != null)
            motoExistante.setTypeMoto(moto.typeMoto());
        if(moto.tarifBase() != null)
            motoExistante.setTarifBase(moto.tarifBase());
        if(moto.kilometrage() != null)
            motoExistante.setKilometrage(moto.kilometrage());
        if(moto.actif() != null)
            motoExistante.setActif(moto.actif());
        if(moto.retireParc() != null)
            motoExistante.setRetireParc(moto.retireParc());
    }

    // Vérification pour les String
    private void verifierChamp(String champ, String messageErreur) throws VehiculeException {
        if (champ == null || champ.isBlank()) {
            throw new VehiculeException(messageErreur);
        }
    }

    // Vérification pour les Enums et autres objets
    private void verifierChamp(Object champ, String messageErreur) throws VehiculeException {
        if (champ == null) {
            throw new VehiculeException(messageErreur);
        }
    }

    private void verifierMoto(MotoRequestDto motoRequestDto) throws VehiculeException {
        if (motoRequestDto == null) {
            throw new VehiculeException("Merci de compléter les informations.");
        }

        verifierChamp(motoRequestDto.marque(), "Merci d'indiquer la marque.");
        verifierChamp(motoRequestDto.modele(), "Merci d'indiquer le modele.");
        verifierChamp(motoRequestDto.couleur(), "Merci d'indiquer la couleur du vehicule.");
        verifierChamp(motoRequestDto.cylindree(), "Merci d'ajouter la cylindree.");
        verifierChamp(motoRequestDto.poids(), "Merci d'ajouter le poids.");
        verifierChamp(motoRequestDto.puissanceKw(), "Merci d'ajouter la puissance.");
        verifierChamp(motoRequestDto.hauteurSelle(), "Merci d'ajouter la hauteur de selle.");
        verifierChamp(motoRequestDto.transmission(), "Merci d'ajouter la transmission.");
        verifierChamp(motoRequestDto.typeMoto(), "Merci d'ajouter le type.");
        verifierChamp(motoRequestDto.tarifBase(), "Merci d'ajouter le tarif de base.");
        verifierChamp(motoRequestDto.kilometrage(), "Merci d'ajouter le kilometrage.");
        verifierChamp(motoRequestDto.retireParc(), "Merci d'indiquer si la moto est retirée du parc.");
        verifierChamp(motoRequestDto.actif(), "Merci d'indiquer si la moto est en location.");
    }
}
