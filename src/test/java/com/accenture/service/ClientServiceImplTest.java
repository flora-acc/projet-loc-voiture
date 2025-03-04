package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.model.Permis;
import com.accenture.repository.dao.ClientDao;
import com.accenture.repository.entity.Adresse;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.AdresseRequestDto;
import com.accenture.service.dto.AdresseResponseDto;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.ClientMapper;
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
class ClientServiceImplTest {

    @Mock
    ClientDao daoMock;
    @Mock
    ClientMapper mapperMock;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    ClientServiceImpl service;



    @DisplayName("""
            Test de la méthode trouverTous qui doit renvoyer une liste de ClientResponseDto
            correspondant aux clients existant en base
            """)
    @Test
    void testTrouverTous(){
        Client clientLucie = creerClientLucie();
        Client clientFlorian = creerClientFlorian();
        List<Client> clients = List.of(clientLucie, clientFlorian);
        ClientResponseDto clientResponseDtoLucie = creerClientResponseDtoLucie();
        ClientResponseDto clientResponseDtoFlorian = creerClientResponseDtoFlorian();
        List<ClientResponseDto> dtos = List.of(clientResponseDtoLucie, clientResponseDtoFlorian);

        Mockito.when(daoMock.findAll()).thenReturn(clients);
        Mockito.when(mapperMock.toClientResponseDto(clientLucie)).thenReturn((clientResponseDtoLucie));
        Mockito.when(mapperMock.toClientResponseDto(clientFlorian)).thenReturn((clientResponseDtoFlorian));

        assertEquals(dtos, service.trouverTous());

    }

    private static Client creerClientLucie() {
        Client c = new Client();
        c.setId(1);
        c.setNom("Limoges");
        c.setPrenom("Lucie");
        c.setEmail("emailLu@email.fr");
        c.setMotDePasse("Ceciestuntest@1");
        // Création et remplissage de l'adresse
        Adresse adresse = new Adresse();
        adresse.setRue("12 Rue de la Paix");
        adresse.setCodePostal("75001");
        adresse.setVille("Paris");
        // Assigner l'adresse au client
        c.setAdresse(adresse);
        c.setDateNaissance(LocalDate.of(1999,4,2));
        c.setPermis(List.of(Permis.A));
        return c;
    }

    private static Client creerClientFlorian() {
        Client c = new Client();
        c.setId(1);
        c.setNom("Bon");
        c.setPrenom("Florian");
        c.setEmail("emailFlo@email.fr");
        c.setMotDePasse("Ceciestuntest@1");
        // Création et remplissage de l'adresse
        Adresse adresse = new Adresse();
        adresse.setRue("10 Rue de la Paix");
        adresse.setCodePostal("75001");
        adresse.setVille("Paris");
        // Assigner l'adresse au client
        c.setAdresse(adresse);
        c.setDateNaissance(LocalDate.of(1992,4,2));
        c.setPermis(List.of(Permis.A));
        return c;
    }

    private static ClientResponseDto creerClientResponseDtoLucie() {
        return new ClientResponseDto(
                1,"Limoges","Lucie",new Adresse(1,"12 Rue de la Paix","75001","Paris"),"emailLu@email.fr",LocalDate.of(1999,4,2),List.of(Permis.A)
        );
    }

    private static ClientResponseDto creerClientResponseDtoFlorian() {
        return new ClientResponseDto(
                1,"Bon","Florian",new Adresse(1,"10 Rue de la Paix","75001","Paris"),"emailFlo@email.fr",LocalDate.of(1992,4,2),List.of(Permis.A)
        );
    }

    @DisplayName(" Test de la méthode inscrireClient : si inscrireClient(null), exception levée")
    @Test
    void testInscrireClient(){
        assertThrows(ClientException.class, ()-> service.inscrireClient(null));
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

    @DisplayName(" Test de la méthode inscrireClient : si inscrireClient(ClientRequestDto OK), alors save() est appelée et un ClientResponseDto renvoyé")
    @Test
    void testInscrireOK(){
        ClientRequestDto requestDto = new ClientRequestDto("Bon","Florian",new AdresseRequestDto("Rue de la Paix","75001","Paris"),"emailFlo@email.fr","Ceciestuntest@1",LocalDate.of(1992,4,2),List.of(Permis.A));
        Client clientAvantEnreg = creerClientFlorian();
        clientAvantEnreg.setId(0);

        Client clientApresEnreg = creerClientFlorian();
        ClientResponseDto responseDto = creerClientResponseDtoFlorian();

        // Mock du passwordEncoder pour simuler l'encodage du mot de passe
        Mockito.when(passwordEncoder.encode("Ceciestuntest@1")).thenReturn("encodedPassword");

        // Mocks
        Mockito.when(mapperMock.toClient(requestDto)).thenReturn(clientAvantEnreg);
        Mockito.when(daoMock.save(clientAvantEnreg)).thenReturn(clientApresEnreg);
        Mockito.when(mapperMock.toClientResponseDto(clientApresEnreg)).thenReturn(responseDto);

        // Test
        ClientResponseDto resultat = service.inscrireClient(requestDto);

        // Vérifications
        assertSame(responseDto, resultat);
        Mockito.verify(daoMock, Mockito.times(1)).save(clientAvantEnreg);
        assertEquals("encodedPassword", clientAvantEnreg.getMotDePasse()); // Vérifier si le mot de passe a bien été encodé
    }

    @DisplayName(" Test supprimerClient() : si l'email est inexistant dans la base de données, l'exception est levée")
    @Test
    void testSupprimerClientEmailIntrouvable(){
        // Given
        String email = "emailTest";
        Mockito.when(daoMock.findByEmail(email)).thenReturn(Optional.empty());

        // When

        // Then
        assertThrows(ClientException.class, ()-> service.supprimerClient(email, ""));
    }

    @DisplayName(" Test supprimerClient() : si le client existe, mais le mot de passe ne correspond pas, une exception est levée.")
    @Test
    void testSupprimerClientMotDePasseIncorrect(){
        Client client = new Client();
        client.setMotDePasse("password123");
        Mockito.when(daoMock.findByEmail("email")).thenReturn(Optional.of(client));

        assertThrows(ClientException.class, () -> {
            service.supprimerClient("email", "wrongPassword");
        });
    }

    @DisplayName(" Test supprimerClient() : si le client existe et que le mot de passe est correct, le client est supprimé")
    @Test
    void testSupprimerClientOk(){
        String motDePasseFourni = "password123";
        Client client = new Client();
        client.setMotDePasse("password123");
        Mockito.when(daoMock.findByEmail("email")).thenReturn(Optional.of(client));
        Mockito.when(passwordEncoder.matches(motDePasseFourni, client.getMotDePasse())).thenReturn(true);

        service.supprimerClient("email", motDePasseFourni);

        Mockito.verify(daoMock, Mockito.times(1)).delete(client);
    }

    @DisplayName(" Test modifierClientPartiellement() : si l'email est inexistant dans la base de données, l'exception est levée")
    @Test
    void testModifierClientPartiellementEmailIntrouvable(){
        ClientRequestDto dto = new ClientRequestDto("test","test",new AdresseRequestDto("test","test","test"),"test@test.fr","Ceciestuntest@1",LocalDate.of(1992,7,22), List.of(Permis.B));
        String email = "emailTest"; // email d'identification, celui utilisé pour retrouver un client existant dans la base de données
        Mockito.when(daoMock.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(ClientException.class, ()-> service.modifierClientPartiellement(email, "test",dto));
    }

    @DisplayName("Test modifierClientPartiellement() : la modification est enregistrée en base de données")
    @Test
    void testModifierClientPartiellementOk() {
        // Given
        Client clientExistant = creerClientFlorian();
        String email = clientExistant.getEmail();
        String motDePasse = clientExistant.getMotDePasse();

        ClientRequestDto clientRequestDto = new ClientRequestDto("NouveauNom", clientExistant.getPrenom(),
                new AdresseRequestDto(clientExistant.getAdresse().getRue(),
                        clientExistant.getAdresse().getCodePostal(),
                        clientExistant.getAdresse().getVille()),
                clientExistant.getEmail(), clientExistant.getMotDePasse(),
                clientExistant.getDateNaissance(), clientExistant.getPermis());

        Client clientMisAJour = creerClientFlorian();
        clientMisAJour.setNom("NouveauNom");

        ClientResponseDto responseDto = new ClientResponseDto(1, "NouveauNom", clientMisAJour.getPrenom(),
                new Adresse(1, clientMisAJour.getAdresse().getRue(),
                        clientMisAJour.getAdresse().getCodePostal(),
                        clientMisAJour.getAdresse().getVille()),
                clientMisAJour.getEmail(),
                clientMisAJour.getDateNaissance(), clientMisAJour.getPermis());

        Mockito.when(daoMock.findByEmail(email)).thenReturn(Optional.of(clientExistant));
        Mockito.when(daoMock.save(Mockito.any(Client.class))).thenReturn(clientExistant);
        Mockito.when(mapperMock.toClientResponseDto(clientMisAJour)).thenReturn(responseDto);

        // When
        ClientResponseDto response = service.modifierClientPartiellement(email, motDePasse, clientRequestDto);

        // Then
        assertNotNull(response);
        assertEquals("NouveauNom", response.nom());
        Mockito.verify(daoMock, Mockito.times(1)).save(clientExistant);
    }

}
