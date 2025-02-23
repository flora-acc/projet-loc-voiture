package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.entity.Client;
import com.accenture.repository.ClientDao;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.accenture.model.Role.CLIENT;

@Service
public class ClientServiceImpl implements ClientService {

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

        verifierClient(clientRequestDto);

        Client client = clientMapper.toClient(clientRequestDto);
        client.setRole(CLIENT);
        String passwordChiffre = passwordEncoder.encode((client.getMotDePasse()));
        client.setMotDePasse(passwordChiffre);

        return clientMapper.toClientResponseDto(clientDao.save(client));
    }

    @Override
    public void supprimer(int id) throws EntityNotFoundException{
        if(clientDao.existsById(id))
            clientDao.deleteById(id);
        else
            throw new ClientException("L'id ne correspond pas");
    }

    /*********************************************
     METHODES PRIVEES
     *********************************************/

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

            if (clientRequestDto.dateNaissance() == null || !verifierMajorite(clientRequestDto.dateNaissance()))
                throw new ClientException("Vous devez avoir au moins 18 ans pour vous inscrire.");

            if (clientRequestDto.email() == null || clientRequestDto.email().isBlank())
                throw new ClientException("Merci d'indiquer votre email.");

            if (clientDao.existsByEmail(clientRequestDto.email()))
                throw new ClientException("Cet email est déjà utilisé !");
        }
}
