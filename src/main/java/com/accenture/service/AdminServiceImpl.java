package com.accenture.service;


import com.accenture.exception.AdminException;
import com.accenture.exception.ClientException;
import com.accenture.repository.dao.AdminDao;
import com.accenture.repository.entity.Administrateur;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.mapper.AdminMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.accenture.model.Role.ADMIN;

@Service
public class AdminServiceImpl implements AdminService {


    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    private final AdminDao adminDao; // final car on ne touche plus au adminDao
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminDao adminDao, AdminMapper adminMapper, PasswordEncoder passwordEncoder) {
        this.adminDao = adminDao;
        this.adminMapper = adminMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /*********************************************
     METHODES PUBLIQUES
     *********************************************/

    /**
     * Récupère toutes les entités administrateur dans la base de données et les convertit en DTO.
     * @return Une liste d'objets AdminResponseDto.
     */
    @Override
    public List<AdminResponseDto> trouverTous() {
        return adminDao.findAll().stream()
                .map(admin -> adminMapper.toAdminResponseDto(admin))
                .toList();
    }

    /**
     * Crée une nouvelle entité Administrateur.
     * @param adminRequestDto
     * @return Un objet AdminResponseDto représentant le nouvel administrateur créé.
     * @throws AdminException Si adminRequestDto est null.
     */
    @Override
    public AdminResponseDto creerAdmin(AdminRequestDto adminRequestDto) throws AdminException {

        if(adminRequestDto == null)
            throw new AdminException("le paramètre est obligatoire");

        if (adminDao.existsByEmail(adminRequestDto.email())) {
            throw new AdminException("Email déjà utilisé !");
        }

        Administrateur admin = adminMapper.toAdministrateur(adminRequestDto);
        admin.setRole(ADMIN);
        String passwordChiffre = passwordEncoder.encode((admin.getMotDePasse()));
        admin.setMotDePasse(passwordChiffre);

        return adminMapper.toAdminResponseDto(adminDao.save(admin));
    }

    /**
     * Supprime une entité Administrateur en fonction de l'email et du mot de passe
     * @param email
     * @param motDePasse
     * @throws AdminException
     */
    @Override
    public void supprimerAdmin(String email, String motDePasse) throws AdminException {
        // Vérification des informations d'identification
        Administrateur admin = adminDao.findByEmail(email)
                .orElseThrow(() -> new AdminException("Ce compte ne correspond pas"));

        long nombreAdmins = adminDao.count();
        if (nombreAdmins <= 1) {
            throw new AdminException("Il est nécessaire d'avoir au moins un compte administrateur.");
        }

        if (!passwordEncoder.matches(motDePasse, admin.getMotDePasse())) {
            throw new ClientException("Identifiants incorrects");
        }

        adminDao.delete(admin);
    }

    /**
     * Met à jour partiellement une entité Administrateur existante.
     * @param email
     * @param motDePasse
     * @param adminRequestDto = Objet de transfert contenant les données à mettre à jour
     * @return un objet AdminResponseDto représentant l'administrateur mis à jour
     * @throws AdminException
     */
    @Override
    public AdminResponseDto modifierAdminPartiellement(String email, String motDePasse, AdminRequestDto adminRequestDto) throws AdminException {

        Administrateur adminExistant = adminDao.findByEmail(email)
                .orElseThrow(() -> new AdminException("Ce compte ne correspond pas"));

        Administrateur nouveau = adminMapper.toAdministrateur(adminRequestDto);

        remplacer(nouveau, adminExistant);
        Administrateur adminEnreg = adminDao.save(adminExistant);
        return adminMapper.toAdminResponseDto(adminEnreg);

    }

    /*********************************************
     METHODES PRIVEES
     *********************************************/

    private static void remplacer(Administrateur admin, Administrateur adminExistant) { // si ce qui m'est fourni n'est pas null, je remplace l'existant par le nouveau de la méthode PATCH
        if (admin.getNom() != null)
            adminExistant.setNom(admin.getNom());
        if (admin.getPrenom() != null)
            adminExistant.setPrenom(admin.getPrenom());
        if(admin.getEmail() != null)
            adminExistant.setEmail(admin.getEmail());
        if(admin.getMotDePasse() != null)
            adminExistant.setMotDePasse(admin.getMotDePasse());
    }

}
