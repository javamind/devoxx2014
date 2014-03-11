package com.ninjamind.conference.controller;

import com.ninjamind.conference.utils.LoggerFactory;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Handler d'exception se chargeant des exceptions pouvant être levées
 * @author EHRET_G
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
    /**
     * Logger commun à la publication
     */
    private static final Logger LOG = LoggerFactory.make();

    /**
     * Traitement des erreurs de persistence
     * @param e
     */
    @ExceptionHandler(JpaSystemException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(JpaSystemException e){
        LOG.error("Persistence error", e);
    }
}
