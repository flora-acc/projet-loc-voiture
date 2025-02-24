package com.accenture.service;

import com.accenture.exception.AdminException;
import com.accenture.repository.AdminDao;
import com.accenture.service.mapper.AdminMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock
    AdminDao daoMock;
    @Mock
    AdminMapper adminMapper;

    @InjectMocks
    AdminServiceImpl service;

    @DisplayName(" Test de la méthode creerAdmin : si ajouter(null), exception levée")
    @Test
    void testCreerAdmin(){
        assertThrows(AdminException.class, ()-> service.creerAdmin(null));
    }

//    @DisplayName(" Test de la méthode creerAdmin : si creerAdmin(AdminRequestDto avec libelle null), exception levée")
//    @Test
//    void testAjouterSansLibelle(){
//        TacheRequestDto dto = new TacheRequestDto(null, LocalDate.now(), true, Priorite.FAIBLE);
//        assertThrows(TacheException.class, ()-> service.ajouter(dto));
//    }

}
