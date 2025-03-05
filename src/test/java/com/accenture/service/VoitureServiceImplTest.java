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
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.dto.VoitureResponseDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


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

    @DisplayName(" Test de la méthode ajouterVoiture : si ajouterVoiture(null), exception levée")
    @Test
    void testAjouterVoiture(){
        VehiculeException exception = assertThrows(VehiculeException.class, () -> service.ajouterVoiture(null));
        assertEquals("Informations à compléter", exception.getMessage());
    }

    @DisplayName(" Test supprimerVoiture() : si la voiture existe, mais actif=false, une exception est levée.")
    @Test
    void testSupprimerVoiture(){
        Voiture voiture = new Voiture();
        voiture.setId(1);
        voiture.setActif(true);
        Mockito.when(daoMock.findById(1)).thenReturn(Optional.of(voiture));

        VehiculeException exception = assertThrows(VehiculeException.class, () -> {
            service.supprimerVoiture(voiture.getId());
        });

        assertEquals("Impossible de supprimer une voiture en location.", exception.getMessage());
    }
}
