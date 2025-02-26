package com.accenture.service;

import com.accenture.exception.AdminException;
import com.accenture.exception.ClientException;
import com.accenture.repository.entity.Administrateur;
import com.accenture.repository.entity.Client;
import com.accenture.repository.ClientDao;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.ClientMapper;
import com.accenture.utils.RegexConstants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import static com.accenture.model.Role.CLIENT;

@Service
public class ClientServiceImpl implements ClientService {

    public static final String AU_MOINS_18_ANS_POUR_VOUS_INSCRIRE = "Vous devez avoir au moins 18 ans pour vous inscrire.";
    private static final String EMAIL_INCONNU = "Cet email n'est pas enregistré chez VoitureLoc.";
    private final ClientDao clientDao; // final car on ne touche plus au clientDao
    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;

    public ClientServiceImpl(ClientDao clientDao, ClientMapper clientMapper, PasswordEncoder passwordEncoder) {
        this.clientDao = clientDao;
        this.clientMapper = clientMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /*********************************************
     METHODES PUBLIQUES
     *********************************************/

    @Override
    public List<ClientResponseDto> trouverTous() {
        return clientDao.findAll().stream()
                .map(client -> clientMapper.toClientResponseDto(client))
                .toList();
    }

    /**
     *
     * @param clientRequestDto
     * @return
     * @throws ClientException
     */
    @Override
    public ClientResponseDto inscrireClient(ClientRequestDto clientRequestDto)  throws ClientException {

        if(clientRequestDto == null)
            throw new ClientException("le paramètre est obligatoire");

        verifierClient(clientRequestDto);

        Client client = clientMapper.toClient(clientRequestDto);
        client.setRole(CLIENT);
        String passwordChiffre = passwordEncoder.encode((client.getMotDePasse()));
        client.setMotDePasse(passwordChiffre);

        return clientMapper.toClientResponseDto(clientDao.save(client));
    }

    @Override
    public void supprimerClient(String email, String motDePasse) throws EntityNotFoundException {
        // Vérification des informations d'identification

        Client client = clientDao.findByEmail(email)
                .orElseThrow(() -> new ClientException("Ce compte ne correspond pas"));

        clientDao.delete(client);
    }

    @Override
    public ClientResponseDto modifierClient(String email, String motDePasse, ClientRequestDto clientRequestDto) throws ClientException, EntityNotFoundException {

        Client clientExistant = clientDao.findByEmail(email)
                .orElseThrow(() -> new ClientException("Ce compte ne correspond pas"));

        verifierClient(clientRequestDto);

        // Crée un nouvel objet client avec les données envoyées dans la requête
        Client clientMisAJour = clientMapper.toClient(clientRequestDto);

        // On conserve l'ID, l'email et la date d'inscription du client existant
        clientMisAJour.setId(clientExistant.getId());
        clientMisAJour.setEmail(clientExistant.getEmail());
        clientMisAJour.setDateInscription(clientExistant.getDateInscription());

        Client clientEnreg = clientDao.save(clientExistant);

        return clientMapper.toClientResponseDto(clientEnreg);
    }

    @Override
    public ClientResponseDto modifierClientPartiellement(String email, String motDePasse, ClientRequestDto clientRequestDto) throws ClientException, EntityNotFoundException {

//        Optional<Client> optClient = clientDao.findByEmail(email);
//
//        if (optClient.isEmpty())
//            throw new EntityNotFoundException(EMAIL_INCONNU);$
//        Client clientExistant = optClient.get();
        Client clientExistant = clientDao.findByEmail(email)
                .orElseThrow(() -> new ClientException("Ce compte ne correspond pas"));

        Client nouveau = clientMapper.toClient(clientRequestDto);

        remplacer(nouveau, clientExistant);
        Client clientEnreg = clientDao.save(clientExistant);
        return clientMapper.toClientResponseDto(clientEnreg);

    }
    /*********************************************
     METHODES PRIVEES
     *********************************************/

    private static void remplacer(Client client, Client clientExistant) { // si ce qui m'est fourni n'est pas null, je remplace l'existant par le nouveau de la méthode PATCH
        if (client.getNom() != null)
            clientExistant.setNom(client.getNom());
        if (client.getPrenom() != null)
            clientExistant.setPrenom(client.getPrenom());
        if(client.getAdresse() != null)
            clientExistant.setAdresse(client.getAdresse());
        if(client.getDateNaissance() != null)
            clientExistant.setDateNaissance(client.getDateNaissance());
        if(client.getEmail() != null)
            clientExistant.setEmail(client.getEmail());
        if(client.getMotDePasse() != null)
            clientExistant.setMotDePasse(client.getMotDePasse());
        if(client.getPermis() != null)
            clientExistant.setPermis(client.getPermis());
    }

    //       Vérification de la majorité
    private static boolean verifierMajorite(LocalDate dateNaissance) {
        return dateNaissance != null &&
                (dateNaissance.plusYears(18).isBefore(LocalDate.now()) || dateNaissance.plusYears(18).isEqual(LocalDate.now()));
    }

        private void verifierClient(ClientRequestDto clientRequestDto) throws ClientException {

            if (clientRequestDto == null)
                throw new ClientException("Merci de compléter les informations.");

            if (clientRequestDto.prenom() == null || clientRequestDto.prenom().isBlank()) // blank pour les Strings, empty la chaine est 0 il n'y a rien même après un trim
                throw new ClientException("Merci d'indiquer votre prénom.");

            if (clientRequestDto.nom() == null || clientRequestDto.nom().isBlank())
                throw new ClientException("Merci d'indiquer votre nom.");

            if (clientRequestDto.adresse() == null)
                throw new ClientException("Merci d'indiquer votre adresse.");

            if (clientRequestDto.adresse().rue() == null || clientRequestDto.adresse().rue().isBlank())
                throw new ClientException("Merci d'indiquer votre rue.");

            if (clientRequestDto.adresse().codePostal() == null || clientRequestDto.adresse().codePostal().isBlank())
                throw new ClientException("Merci d'indiquer votre code postal.");

            if (clientRequestDto.adresse().ville() == null || clientRequestDto.adresse().ville().isBlank())
                throw new ClientException("Merci d'indiquer votre ville.");

            if (clientRequestDto.email() == null || clientRequestDto.email().isBlank())
                throw new ClientException("Merci d'indiquer votre email.");
            // Vérification du format email avec une regex
            if (!clientRequestDto.email().matches(RegexConstants.EMAIL_REGEX))
                throw new ClientException("Format de l'email invalide.");
            if (clientDao.existsByEmail(clientRequestDto.email()))
                throw new ClientException("Cet email est déjà utilisé !");

            if (clientRequestDto.motDePasse() == null || clientRequestDto.motDePasse().isBlank())
                throw new ClientException("Merci d'ajouter un mot de passe.");
            // Vérification du format email avec une regex
            if (!clientRequestDto.motDePasse().matches(RegexConstants.MOTDEPASSE_REGEX))
                throw new ClientException("Format du mot de passe invalide.");

            if (clientRequestDto.dateNaissance() == null || !verifierMajorite(clientRequestDto.dateNaissance()))
                throw new ClientException(AU_MOINS_18_ANS_POUR_VOUS_INSCRIRE);

        }
}
