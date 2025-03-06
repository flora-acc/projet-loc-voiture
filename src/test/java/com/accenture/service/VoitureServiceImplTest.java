package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.exception.VehiculeException;
import com.accenture.model.Carburant;
import com.accenture.model.Permis;
import com.accenture.model.Type;
import com.accenture.repository.dao.VoitureDao;
import com.accenture.repository.entity.Adresse;
import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.Voiture;
import com.accenture.service.dto.*;
import com.accenture.service.mapper.VoitureMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class VoitureServiceImplTest {

    @Mock
    VoitureDao daoMock;
    @Mock
    VoitureMapper mapperMock;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    VoitureServiceImpl service;


    @DisplayName("""
            Test de la méthode afficherVoitures qui doit renvoyer une liste de VoitureResponseDto
            correspondant aux voitures existant en base
            """)
    @Test
    void testAfficherVoitures() {
        Voiture voitureRenault = creerVoitureRenault();
        Voiture voitureBMW = creerVoitureBMW();
        List<Voiture> voitures = List.of(voitureRenault, voitureBMW);
        VoitureResponseDto voitureResponseDtoRenault = creerVoitureResponseDtoRenault();
        VoitureResponseDto voitureResponseDtoBMW = creerVoitureResponseDtoBMW();
        List<VoitureResponseDto> dtos = List.of(voitureResponseDtoRenault, voitureResponseDtoBMW);

        Mockito.when(daoMock.findAll()).thenReturn(voitures);
        Mockito.when(mapperMock.toVoitureResponseDto(voitureRenault)).thenReturn((voitureResponseDtoRenault));
        Mockito.when(mapperMock.toVoitureResponseDto(voitureBMW)).thenReturn((voitureResponseDtoBMW));

        assertEquals(dtos, service.afficherVoitures());

    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(null), exception levée")
    @Test
    void testAjouterVoiture(){
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(null));
        assertEquals("Merci de compléter les informations.", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec marque null), exception levée")
    @Test
    void testAjouterVoitureMarqueNull() {
        VoitureRequestDto dto = new VoitureRequestDto(null, "test", "test",2,Carburant.HYBRIDE, 4,"test",true,5, Type.CITADINE,100,3000,false,true);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("Merci d'indiquer la marque.", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec marque blank), exception levée")
    @Test
    void testAjouterVoitureMarqueBlank() {
        VoitureRequestDto dto = new VoitureRequestDto("\t", "test", "test",2,Carburant.HYBRIDE, 4,"test",true,5, Type.CITADINE,100,3000,false,true);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("Merci d'indiquer la marque.", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec modele null), exception levée")
    @Test
    void testAjouterVoitureModeleNull() {
        VoitureRequestDto dto = new VoitureRequestDto("test", null, "test",2,Carburant.HYBRIDE, 4,"test",true,5, Type.CITADINE,100,3000,false,true);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("Merci d'indiquer le modele.", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec modele blank), exception levée")
    @Test
    void testAjouterVoitureModeleBlank() {
        VoitureRequestDto dto = new VoitureRequestDto("test", "\t", "test",2,Carburant.HYBRIDE, 4,"test",true,5, Type.CITADINE,100,3000,false,true);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("Merci d'indiquer le modele.", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec couleur null), exception levée")
    @Test
    void testAjouterVoitureCouleurNull() {
        VoitureRequestDto dto = new VoitureRequestDto("test", "test", null,2,Carburant.HYBRIDE, 4,"test",true,5, Type.CITADINE,100,3000,false,true);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("Merci d'indiquer la couleur du vehicule.", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec couleur blank), exception levée")
    @Test
    void testAjouterVoitureCouleurBlank() {
        VoitureRequestDto dto = new VoitureRequestDto("test", "test", "\t",2,Carburant.HYBRIDE, 4,"test",true,5, Type.CITADINE,100,3000,false,true);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("Merci d'indiquer la couleur du vehicule.", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec nbPlaces null), exception levée")
    @Test
    void testAjouterVoitureNbPlacesNull() {
        VoitureRequestDto dto = new VoitureRequestDto("test", "test", "test",null,Carburant.HYBRIDE, 4,"test",true,5, Type.CITADINE,100,3000,false,true);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("Merci d'ajouter le nombre de places.", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec nbPlaces invalide), exception levée")
    @Test
    void testAjouterVoitureNbPlacesInvalide() {
        VoitureRequestDto dto = new VoitureRequestDto("test", "test", "test",-2,Carburant.HYBRIDE, 4,"test",true,5, Type.CITADINE,100,3000,false,true);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("La valeur ne peut pas être négative.", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec carburant null), exception levée")
    @Test
    void testAjouterVoitureCarburantNull() {
        VoitureRequestDto dto = new VoitureRequestDto("test", "test", "test",2,null, 4,"test",true,5, Type.CITADINE,100,3000,false,true);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("Merci d'ajouter le carburant.", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec nbPortes null), exception levée")
    @Test
    void testAjouterVoitureNbPortesNull() {
        VoitureRequestDto dto = new VoitureRequestDto("test", "test", "test",2,Carburant.HYBRIDE, null,"test",true,5, Type.CITADINE,100,3000,false,true);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("Merci d'ajouter le nombre de portes.", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec nbPortes invalide), exception levée")
    @Test
    void testAjouterVoitureNbPortesInvalide() {
        VoitureRequestDto dto = new VoitureRequestDto("test", "test", "test",2,Carburant.HYBRIDE, -4,"test",true,5, Type.CITADINE,100,3000,false,true);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("La valeur ne peut pas être négative.", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec transmission null), exception levée")
    @Test
    void testAjouterVoitureTransmissionNull() {
        VoitureRequestDto dto = new VoitureRequestDto("test", "test", "test",2,Carburant.HYBRIDE, 4,null,true,5, Type.CITADINE,100,3000,false,true);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("Merci d'ajouter le type de transmission: AUTO ou MANUELLE", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec transmission blank), exception levée")
    @Test
    void testAjouterVoitureTransmissionBlank() {
        VoitureRequestDto dto = new VoitureRequestDto("test", "test", "test",2,Carburant.HYBRIDE, 4,"\t",true,5, Type.CITADINE,100,3000,false,true);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("Merci d'ajouter le type de transmission: AUTO ou MANUELLE", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec climatisation null), exception levée")
    @Test
    void testAjouterVoitureClimatisationNull() {
        VoitureRequestDto dto = new VoitureRequestDto("test", "test", "test",2,Carburant.HYBRIDE, 4,"test",null,5, Type.CITADINE,100,3000,false,true);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("Merci d'ajouter la climatisation.", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec nbBagages null), exception levée")
    @Test
    void testAjouterVoitureNbBagagesNull() {
        VoitureRequestDto dto = new VoitureRequestDto("test", "test", "test",2,Carburant.HYBRIDE, 4,"test",true,null, Type.CITADINE,100,3000,false,true);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("Merci d'ajouter le nombre de bagages possible.", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec nbBagages invalide), exception levée")
    @Test
    void testAjouterVoitureNbBagagesInvalide() {
        VoitureRequestDto dto = new VoitureRequestDto("test", "test", "test",2,Carburant.HYBRIDE, 4,"test",true,-5, Type.CITADINE,100,3000,false,true);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("La valeur ne peut pas être négative.", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec type null), exception levée")
    @Test
    void testAjouterVoitureTypeNull() {
        VoitureRequestDto dto = new VoitureRequestDto("test", "test", "test",2,Carburant.HYBRIDE, 4,"test",true,5, null,100,3000,false,true);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("Merci d'ajouter le type.", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec tarifBase null), exception levée")
    @Test
    void testAjouterVoitureTarifBaseNull() {
        VoitureRequestDto dto = new VoitureRequestDto("test", "test", "test",2,Carburant.HYBRIDE, 4,"test",true,5, Type.CITADINE,null,3000,false,true);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("Merci d'ajouter le tarif de base.", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec tarifBase invalide), exception levée")
    @Test
    void testAjouterVoitureTarifBaseInvalide() {
        VoitureRequestDto dto = new VoitureRequestDto("test", "test", "test",2,Carburant.HYBRIDE, 4,"test",true,5, Type.CITADINE,-100,3000,false,true);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("La valeur ne peut pas être négative.", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec le kilometrage null), exception levée")
    @Test
    void testAjouterVoitureKilometrageNull() {
        VoitureRequestDto dto = new VoitureRequestDto("test", "test", "test",2,Carburant.HYBRIDE, 4,"test",true,5, Type.CITADINE,100,null,false,true);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("Merci d'ajouter le kilometrage.", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec le kilometrage invalide), exception levée")
    @Test
    void testAjouterVoitureKilometrageInvalide() {
        VoitureRequestDto dto = new VoitureRequestDto("test", "test", "test",2,Carburant.HYBRIDE, 4,"test",true,5, Type.CITADINE,100,-3000,false,true);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("La valeur ne peut pas être négative.", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec retire du parc null), exception levée")
    @Test
    void testAjouterVoitureRetireParcNull() {
        VoitureRequestDto dto = new VoitureRequestDto("test", "test", "test",2,Carburant.HYBRIDE, 4,"test",true,5, Type.CITADINE,100,3000,null,true);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("Merci d'indiquer si la voiture est retirée du parc.", exception.getMessage());
    }

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(VoitureRequestDto avec actif null), exception levée")
    @Test
    void testAjouterVoitureActifNull() {
        VoitureRequestDto dto = new VoitureRequestDto("test", "test", "test",2,Carburant.HYBRIDE, 4,"test",true,5, Type.CITADINE,100,3000,false,null);
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(dto));
        assertEquals("Merci d'indiquer si la moto est en location.", exception.getMessage());
    }

    @DisplayName(" Test supprimerVoiture() : si l'id est inexistant dans la base de données, l'exception est levée")
    @Test
    void testSupprimerVoitureIdIntrouvable(){
        int id = 45;
        Mockito.when(daoMock.findById(id)).thenReturn(Optional.empty());

        assertThrows(VehiculeException.class, ()-> service.supprimerVoiture(id));
    }

    @DisplayName(" Test supprimerVoiture() : si la voiture existe, et que actif=true donc en location, une exception est levée.")
    @Test
    void testSupprimerVoitureActifFalse(){
        Voiture voiture = new Voiture();
        voiture.setId(1);
        voiture.setActif(true);
        Mockito.when(daoMock.findById(1)).thenReturn(Optional.of(voiture));

        VehiculeException exception = assertThrows(VehiculeException.class, () -> {
            service.supprimerVoiture(voiture.getId());
        });

        assertEquals("Impossible de supprimer une voiture en location.", exception.getMessage());
    }

        @DisplayName(" Test supprimerVoiture() : si l'id existe en base de données', le client est supprimé")
    @Test
    void testSupprimerVoitureOk(){
        int idFourni = 45;
        Voiture voiture = new Voiture();
        voiture.setId(45);
        Mockito.when(daoMock.findById(idFourni)).thenReturn(Optional.of(voiture));

        service.supprimerVoiture(idFourni);

        Mockito.verify(daoMock, Mockito.times(1)).delete(voiture);
    }

    @DisplayName(" Test modifierVoiturePartiellement() : si l'id est inexistant dans la base de données, l'exception est levée")
    @Test
    void testModifierVoiturePartiellementIdIntrouvable(){
        int id = 2;
        VoitureRequestDto dto = new VoitureRequestDto(null, "test", "test",2,Carburant.HYBRIDE, 4,"test",true,5, Type.CITADINE,100,3000,false,true);
        Mockito.when(daoMock.findById(id)).thenReturn(Optional.empty());

        assertThrows(VehiculeException.class, ()-> service.modifierVoiturePartiellement(id, dto));
    }

//    @DisplayName("Test modifierVoiturePartiellement() : la modification est enregistrée en base de données")
//    @Test
//    void testModifierVoiturePartiellementOk() {
//        // Given
//        Voiture voitureExistante = creerVoitureBMW();
//        int idFourni = 45;
//        voitureExistante.setId(45);
//
//        VoitureRequestDto voitureRequestDto = new VoitureRequestDto(
//                "Nouvelle marque",
//                "test", "test",
//                2,
//                Carburant.HYBRIDE,
//                4,
//                "test",
//                true,
//                5,
//                Type.CITADINE,
//                100,
//                3000,
//                false,
//                true
//        );
//
//        Voiture voitureMiseAJour = creerVoitureBMW();
//        voitureMiseAJour.setMarque("Nouvelle Marque");
//
//        VoitureResponseDto responseDto = new VoitureResponseDto(
//                1,
//                "NouveauNom",
//                voitureMiseAJour.getModele(),
//                voitureMiseAJour.getNbPlaces(),
//                voitureMiseAJour.getCarburant(),
//                voitureMiseAJour.getNbPortes(),
//                voitureMiseAJour.getTransmission(),
//                voitureMiseAJour.getClimatisation(),
//                voitureMiseAJour.getNbBagages(),
//                voitureMiseAJour.getType(),
//                voitureMiseAJour.getTarifBase(),
//                voitureMiseAJour.getKilometrage(),
//                voitureMiseAJour.getRetireParc(),
//                voitureMiseAJour.getActif()
//        )
//
//        Mockito.when(daoMock.findById(idFourni)).thenReturn(Optional.of(voitureExistante));
//        Mockito.when(daoMock.save(Mockito.any(Voiture.class))).thenReturn(voitureExistante);
//        Mockito.when(mapperMock.toVoitureResponseDto(voitureMiseAJour)).thenReturn(responseDto);
//
//        // When
//        ClientResponseDto response = service.modifierVoiturePartiellement(email, motDePasse, voitureRequestDto);
//
//        // Then
//        assertNotNull(response);
//        assertEquals("NouveauNom", response.nom());
//        Mockito.verify(daoMock, Mockito.times(1)).save(voitureExistante);
//    }


    /*********************************************
     METHODES PRIVEES
     *********************************************/

    private static Voiture creerVoitureRenault() {
        Voiture v = new Voiture();
        v.setId(1);
        v.setMarque("Renault");
        v.setModele("Clio");
        v.setCouleur("Bleu");
        v.setCarburant(Carburant.ESSENCE);
        v.setNbPortes(5);
        v.setTransmission("AUTOMATIQUE");
        v.setClimatisation(true);
        v.setNbBagages(2);
        v.setType(Type.CITADINE);
        v.setTarifBase(55);
        v.setKilometrage(12000);
        v.setRetireParc(false);
        v.setActif(true);
        return v;
    }

    private static Voiture creerVoitureBMW() {
        Voiture v = new Voiture();
        v.setId(2);
        v.setMarque("BMW");
        v.setModele("X5");
        v.setCouleur("Noir");
        v.setCarburant(Carburant.ESSENCE);
        v.setNbPortes(5);
        v.setTransmission("AUTOMATIQUE");
        v.setClimatisation(true);
        v.setNbBagages(4);
        v.setType(Type.SUV);
        v.setTarifBase(100);
        v.setKilometrage(20000);
        v.setRetireParc(false);
        v.setActif(true);
        return v;
    }

    private static VoitureResponseDto creerVoitureResponseDtoRenault() {
        return new VoitureResponseDto(
                1,
                "Renault",
                "Clio","Bleu",
                5,Carburant.ESSENCE,
                5,
                "AUTOMATIQUE",
                true,
                2,
                Type.CITADINE,
                55,
                12000,
                false,
                true
        );
    }

    private static VoitureResponseDto creerVoitureResponseDtoBMW() {
        return new VoitureResponseDto(
                2,
                "BMW",
                "X5",
                "Noir",
                5,
                Carburant.ESSENCE,
                5,
                "AUTOMATIQUE",
                true,
                4,
                Type.SUV,
                100,
                20000,
                false,
                true
        );
    }
}
