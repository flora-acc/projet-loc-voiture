package com.accenture.service;

import com.accenture.exception.AdminException;
import com.accenture.exception.ClientException;
import com.accenture.repository.entity.Administrateur;
import com.accenture.repository.entity.Adresse;
import com.accenture.repository.entity.Client;
import com.accenture.repository.dao.ClientDao;
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

    /**
     * Récupère tous les clients de la base de données et les convertit en DTO.
     *
     * @return une liste d'objets ClientResponseDto représentant tous les clients
     */
    @Override
    public List<ClientResponseDto> trouverTous() {
        return clientDao.findAll().stream()
                .map(client -> clientMapper.toClientResponseDto(client))
                .toList();
    }

    /**
     * Récupère un client en fonction de son email et de son mot de passe
     *
     * @param email      Email du client à rechercher.
     * @param motDePasse Mot de passe pour vérifier l'identité du client.
     * @return Un objet ClientResponseDto représentant le client trouvé.
     * @throws ClientException Si le client n'existe pas ou si les identifiants sont incorrects.
     */
    @Override
    public ClientResponseDto trouverClient(String email, String motDePasse) throws ClientException {
        Client client = clientDao.findByEmail(email)
                .orElseThrow(() -> new ClientException("Ce compte ne correspond pas"));

        if (!client.getMotDePasse().equals(motDePasse)) {
            throw new ClientException("Identifiants incorrects");
        }
        return clientMapper.toClientResponseDto(client);
    }

    /**
     * Inscrit un nouveau client dans le système
     *
     * @param clientRequestDto Objet de transfert contenant les données du client à inscrire.
     * @return Un objet ClientResponseDto représentant le client inscrit.
     * @throws ClientException Si les données fournies sont invalides ou si le paramètre est null.
     */
    @Override
    public ClientResponseDto inscrireClient(ClientRequestDto clientRequestDto) throws ClientException {

        if (clientRequestDto == null)
            throw new ClientException("le paramètre est obligatoire");

        verifierConstantesClient(clientRequestDto);
        verifierClient(clientRequestDto);

        Client client = clientMapper.toClient(clientRequestDto);
        client.setRole(CLIENT);
        String passwordChiffre = passwordEncoder.encode((client.getMotDePasse()));
        client.setMotDePasse(passwordChiffre);

        return clientMapper.toClientResponseDto(clientDao.save(client));
    }

    /**
     * Supprime un client en fonction de son email et de son mot de passe
     *
     * @param email      Email du client à supprimer.
     * @param motDePasse Mot de passe pour vérifier l'identité du client
     * @throws ClientException Si les informations d'identification sont incorrectes et si le client n'existe pas dans la base de données.
     */
    @Override
    public void supprimerClient(String email, String motDePasse) throws ClientException {
        // Vérification des informations d'identification
        Client client = clientDao.findByEmail(email)
                .orElseThrow(() -> new ClientException("Ce compte ne correspond pas"));

        if (passwordEncoder.matches(motDePasse, client.getMotDePasse())) {
            clientDao.delete(client);
        } else throw new ClientException("Identifiants incorrects");
    }

//    @Override
//    public ClientResponseDto modifierClient(String email, String motDePasse, ClientRequestDto clientRequestDto) throws ClientException, EntityNotFoundException {
//
//        Client clientExistant = clientDao.findByEmail(email)
//                .orElseThrow(() -> new ClientException("Ce compte ne correspond pas"));
//
//        verifierClient(clientRequestDto);
//
//        // Crée un nouvel objet client avec les données envoyées dans la requête
//        Client clientMisAJour = clientMapper.toClient(clientRequestDto);
//
//        // On conserve l'ID, l'email et la date d'inscription du client existant
//        clientMisAJour.setId(clientExistant.getId());
//        clientMisAJour.setEmail(clientExistant.getEmail());
//        clientMisAJour.setDateInscription(clientExistant.getDateInscription());
//
//        Client clientEnreg = clientDao.save(clientExistant);
//
//        return clientMapper.toClientResponseDto(clientEnreg);
//    }

    /**
     * Met à jour partiellement une entité Client existante
     *
     * @param email            L'email du client pour l'identifier
     * @param motDePasse       Le mot de passe pour vérifier l'identité du client.
     * @param clientRequestDto Objet de transfert contenant les nouvelles données à mettre à jour.
     * @return Un objet ClientResponseDto représentant le client mis à jour
     * @throws ClientException Si le compte client n'existe pas ou si les identifiants sont incorrects.
     */
    @Override
    public ClientResponseDto modifierClientPartiellement(String email, String motDePasse, ClientRequestDto clientRequestDto) throws ClientException {

        Client clientExistant = clientDao.findByEmail(email)
                .orElseThrow(() -> new ClientException("Ce compte ne correspond pas"));

        if (!passwordEncoder.matches(motDePasse, clientExistant.getMotDePasse())) {
            throw new ClientException("Identifiants incorrects");
        }

        remplacer(clientRequestDto, clientExistant);
        Client clientMisAJour = clientDao.save(clientExistant);
        return clientMapper.toClientResponseDto(clientMisAJour);
    }

    /*********************************************
     METHODES PRIVEES
     *********************************************/

    private static void remplacer(ClientRequestDto client, Client clientExistant) { // si ce qui m'est fourni n'est pas null, je remplace l'existant par le nouveau de la méthode PATCH
        if (client.nom() != null)
            clientExistant.setNom(client.nom());
        if (client.prenom() != null)
            clientExistant.setPrenom(client.prenom());
        if (client.adresse() != null) {
            clientExistant.getAdresse().setRue(client.adresse().rue());
            clientExistant.getAdresse().setCodePostal(client.adresse().codePostal());
            clientExistant.getAdresse().setVille((client.adresse().ville()));
        }
        if (client.dateNaissance() != null)
            clientExistant.setDateNaissance(client.dateNaissance());
        if (client.email() != null)
            clientExistant.setEmail(client.email());
        if (client.motDePasse() != null)
            clientExistant.setMotDePasse(client.motDePasse());
        if (client.permis() != null)
            clientExistant.setPermis(client.permis());
    }

    //       Vérification de la majorité
    private static boolean verifierMajorite(LocalDate dateNaissance) {
        return dateNaissance != null &&
                (dateNaissance.plusYears(18).isBefore(LocalDate.now()) || dateNaissance.plusYears(18).isEqual(LocalDate.now()));
    }

    private void verifierConstantesClient(ClientRequestDto clientRequestDto) throws ClientException {
        // Vérification du format email avec une regex
        if (!clientRequestDto.email().matches(RegexConstants.EMAIL_REGEX))
            throw new ClientException("Format de l'email invalide.");
        // Vérification du format email avec une regex
        if (!clientRequestDto.motDePasse().matches(RegexConstants.MOTDEPASSE_REGEX))
            throw new ClientException("Format du mot de passe invalide.");
    }

    private void verifierChamp(String champ, String messageErreur) throws ClientException {
        if (champ == null || champ.isBlank()) {
            throw new ClientException(messageErreur);

        }
    }

    private void verifierClient(ClientRequestDto clientRequestDto) throws ClientException {
        if (clientRequestDto == null) {
            throw new ClientException("Merci de compléter les informations.");
        }

        verifierChamp(clientRequestDto.prenom(), "Merci d'indiquer votre prénom.");
        verifierChamp(clientRequestDto.nom(), "Merci d'indiquer votre nom.");
        verifierChamp(clientRequestDto.email(), "Merci d'indiquer votre email.");
        verifierChamp(clientRequestDto.motDePasse(), "Merci d'ajouter un mot de passe.");

        if (clientDao.existsByEmail(clientRequestDto.email())) {
            throw new ClientException("Cet email est déjà utilisé !");
        }

        // Valider l'adresse
        if (clientRequestDto.adresse() == null) {
            throw new ClientException("Merci d'indiquer votre adresse.");
        }
        verifierChamp(clientRequestDto.adresse().rue(), "Merci d'indiquer votre rue.");
        verifierChamp(clientRequestDto.adresse().codePostal(), "Merci d'indiquer votre code postal.");
        verifierChamp(clientRequestDto.adresse().ville(), "Merci d'indiquer votre ville.");

        // Valider la date de naissance
        if (clientRequestDto.dateNaissance() == null || !verifierMajorite(clientRequestDto.dateNaissance())) {
            throw new ClientException(AU_MOINS_18_ANS_POUR_VOUS_INSCRIRE);
        }
    }

}