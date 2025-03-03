package com.accenture.controller.advice;

import com.accenture.controller.AdminController;
import com.accenture.exception.ClientException;
import com.accenture.model.ErreurMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ApplicationControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationControllerAdvice.class);

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ErreurMessage> gestionTacheException(ClientException ex){

        logger.debug("Détails pour l'analyse du problème :", ex);

        ErreurMessage er = new ErreurMessage(LocalDateTime.now(), "Erreur fonctionnelle", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErreurMessage> entityNotFoundException(EntityNotFoundException ex){

        logger.debug("Détails pour l'analyse du problème :", ex);

        ErreurMessage er = new ErreurMessage(LocalDateTime.now(), "Mauvaise requete", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErreurMessage> problemeValidation(MethodArgumentNotValidException ex){
        String message = ex.getBindingResult().getAllErrors()
                .stream()
                .map(objectError -> objectError.getDefaultMessage()) // je transforme mon objet error en chaine de caractères
                .collect(Collectors.joining(","));

        logger.debug("Erreurs détaillées de validation : {}", ex.getBindingResult().getAllErrors());

        ErreurMessage er = new ErreurMessage(LocalDateTime.now(),"validation erreur",message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }
}
