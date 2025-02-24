package com.accenture.service;

import com.accenture.exception.AdminException;
import com.accenture.exception.ClientException;
import com.accenture.model.Permis;
import com.accenture.repository.AdminDao;
import com.accenture.repository.ClientDao;
import com.accenture.service.dto.AdresseRequestDto;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.mapper.AdminMapper;
import com.accenture.service.mapper.ClientMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    ClientDao daoMock;
    @Mock
    ClientMapper clientMapper;

    @InjectMocks
    ClientServiceImpl service;

    @DisplayName(" Test de la méthode inscrireClient : si inscrireClient(null), exception levée")
    @Test
    void testInscrireClient(){
        assertThrows(AdminException.class, ()-> service.inscrireClient(null));
    }

    @DisplayName(" Test de la méthode inscrireClient : si inscrireClient(ClientRequestDto avec nom null), exception levée")
    @Test
    void testInscrireClientSansNom(){
        ClientRequestDto dto = new ClientRequestDto(null,"test",new AdresseRequestDto("test","test","test"),"test@test.fr","test",LocalDate.of(1992,7,22), List.of(Permis.B));
        assertThrows(ClientException.class, ()-> service.inscrireClient(dto));
    }

    @DisplayName(" Test de la méthode inscrireClient : si inscrireClient(ClientRequestDto avec nom blank), exception levée")
    @Test
    void testInscrireClientNomBlank(){
        ClientRequestDto dto = new ClientRequestDto("\t","test",new AdresseRequestDto("test","test","test"),"test@test.fr","test",LocalDate.of(1992,7,22), List.of(Permis.B));
        assertThrows(ClientException.class, ()-> service.inscrireClient(dto));
    }

    @DisplayName(" Test de la méthode inscrireClient : si inscrireClient(ClientRequestDto avec prenom null), exception levée")
    @Test
    void testInscrireClientSansPrenom(){
        ClientRequestDto dto = new ClientRequestDto("test",null,new AdresseRequestDto("test","test","test"),"test@test.fr","test",LocalDate.of(1992,7,22), List.of(Permis.B));
        assertThrows(ClientException.class, ()-> service.inscrireClient(dto));
    }

    @DisplayName(" Test de la méthode inscrireClient : si inscrireClient(ClientRequestDto avec prenom blank), exception levée")
    @Test
    void testInscrireClientPrenomBlank(){
        ClientRequestDto dto = new ClientRequestDto("test","\t",new AdresseRequestDto("test","test","test"),"test@test.fr","test",LocalDate.of(1992,7,22), List.of(Permis.B));
        assertThrows(ClientException.class, ()-> service.inscrireClient(dto));
    }

    @DisplayName(" Test de la méthode inscrireClient : si inscrireClient(ClientRequestDto avec adresse.rue null), exception levée")
    @Test
    void testInscrireClientRueNull(){
        ClientRequestDto dto = new ClientRequestDto("test","test",new AdresseRequestDto(null,"test","test"),"test@test.fr","test",LocalDate.of(1992,7,22), List.of(Permis.B));
        assertThrows(ClientException.class, ()-> service.inscrireClient(dto));
    }

    @DisplayName(" Test de la méthode inscrireClient : si inscrireClient(ClientRequestDto avec adresse.rue blank), exception levée")
    @Test
    void testInscrireClientRueBlank(){
        ClientRequestDto dto = new ClientRequestDto("test","test",new AdresseRequestDto("\t","test","test"),"test@test.fr","test",LocalDate.of(1992,7,22), List.of(Permis.B));
        assertThrows(ClientException.class, ()-> service.inscrireClient(dto));
    }

    @DisplayName(" Test de la méthode inscrireClient : si inscrireClient(ClientRequestDto avec adresse.codepostal null), exception levée")
    @Test
    void testInscrireClientCodePostalNull(){
        ClientRequestDto dto = new ClientRequestDto("test","test",new AdresseRequestDto("test",null,"test"),"test@test.fr","test",LocalDate.of(1992,7,22), List.of(Permis.B));
        assertThrows(ClientException.class, ()-> service.inscrireClient(dto));
    }

    @DisplayName(" Test de la méthode inscrireClient : si inscrireClient(ClientRequestDto avec adresse.codepostal blank), exception levée")
    @Test
    void testInscrireClientCodePostalBlank(){
        ClientRequestDto dto = new ClientRequestDto("test","test",new AdresseRequestDto("test","\t","test"),"test@test.fr","test",LocalDate.of(1992,7,22), List.of(Permis.B));
        assertThrows(ClientException.class, ()-> service.inscrireClient(dto));
    }

    @DisplayName(" Test de la méthode inscrireClient : si inscrireClient(ClientRequestDto avec adresse.ville null), exception levée")
    @Test
    void testInscrireClientVilleNull(){
        ClientRequestDto dto = new ClientRequestDto("test","test",new AdresseRequestDto("test","test",null),"test@test.fr","test",LocalDate.of(1992,7,22), List.of(Permis.B));
        assertThrows(ClientException.class, ()-> service.inscrireClient(dto));
    }

    @DisplayName(" Test de la méthode inscrireClient : si inscrireClient(ClientRequestDto avec adresse.ville blank), exception levée")
    @Test
    void testInscrireClientVilleBlank(){
        ClientRequestDto dto = new ClientRequestDto("test","test",new AdresseRequestDto("test","test","\t"),"test@test.fr","test",LocalDate.of(1992,7,22), List.of(Permis.B));
        assertThrows(ClientException.class, ()-> service.inscrireClient(dto));
    }

    @DisplayName(" Test de la méthode inscrireClient : si inscrireClient(ClientRequestDto avec email null), exception levée")
    @Test
    void testInscrireClientEmailNull(){
        ClientRequestDto dto = new ClientRequestDto("test","test",new AdresseRequestDto("test","test","test"),null,"test",LocalDate.of(1992,7,22), List.of(Permis.B));
        assertThrows(ClientException.class, ()-> service.inscrireClient(dto));
    }

    @DisplayName(" Test de la méthode inscrireClient : si inscrireClient(ClientRequestDto avec email blank), exception levée")
    @Test
    void testInscrireClientEmailBlank(){
        ClientRequestDto dto = new ClientRequestDto("test","test",new AdresseRequestDto("test","test","test"),"\t","test",LocalDate.of(1992,7,22), List.of(Permis.B));
        assertThrows(ClientException.class, ()-> service.inscrireClient(dto));
    }

    @DisplayName(" Test de la méthode inscrireClient : si inscrireClient(ClientRequestDto avec email invalide format), exception levée")
    @Test
    void testInscrireClientEmailInvalide(){
        ClientRequestDto dto = new ClientRequestDto("test","test",new AdresseRequestDto("test","test","test"),"test.fr","test",LocalDate.of(1992,7,22), List.of(Permis.B));
        assertThrows(ClientException.class, ()-> service.inscrireClient(dto));
    }

    @DisplayName(" Test de la méthode inscrireClient : si inscrireClient(ClientRequestDto avec motDePasse null), exception levée")
    @Test
    void testInscrireClientMotDePasseNull(){
        ClientRequestDto dto = new ClientRequestDto("test","test",new AdresseRequestDto("test","test","test"),"test@test.fr",null,LocalDate.of(1992,7,22), List.of(Permis.B));
        assertThrows(ClientException.class, ()-> service.inscrireClient(dto));
    }

    @DisplayName(" Test de la méthode inscrireClient : si inscrireClient(ClientRequestDto avec motDePasse blank), exception levée")
    @Test
    void testInscrireClientMotDePasseBlank(){
        ClientRequestDto dto = new ClientRequestDto("test","test",new AdresseRequestDto("test","test","test"),"test@test.fr","\t",LocalDate.of(1992,7,22), List.of(Permis.B));
        assertThrows(ClientException.class, ()-> service.inscrireClient(dto));
    }

    @DisplayName(" Test de la méthode inscrireClient : si inscrireClient(ClientRequestDto avec mot de passe invalide format), exception levée")
    @Test
    void testInscrireClientMotDePasseInvalide(){
        ClientRequestDto dto = new ClientRequestDto("test","test",new AdresseRequestDto("test","test","test"),"test.fr","test",LocalDate.of(1992,7,22), List.of(Permis.B));
        assertThrows(ClientException.class, ()-> service.inscrireClient(dto));
    }

    @DisplayName(" Test de la méthode inscrireClient : si inscrireClient(ClientRequestDto avec dateNaissance null), exception levée")
    @Test
    void testInscrireClientDateNaissanceNull(){
        ClientRequestDto dto = new ClientRequestDto("test","test",new AdresseRequestDto("test","test","test"),"test@test.fr","Ceciestuntest@1",null, List.of(Permis.B));
        ClientException clientException = assertThrows(ClientException.class, ()-> service.inscrireClient(dto));
        assertEquals(ClientServiceImpl.AU_MOINS_18_ANS_POUR_VOUS_INSCRIRE,clientException.getMessage());
    }

    @DisplayName(" Test de la méthode inscrireClient : si inscrireClient(ClientRequestDto avec dateNaissance invalide), exception levée")
    @Test
    void testInscrireClientDateNaissanceInvalide(){
        ClientRequestDto dto = new ClientRequestDto("test","test",new AdresseRequestDto("test","test","test"),"test@test.fr","Ceciestuntest@1",LocalDate.of(2024,7,22), List.of(Permis.B));
        assertThrows(ClientException.class, ()-> service.inscrireClient(dto));
    }

}
