package com.polarbookshop.catalog_service.web;

import com.polarbookshop.catalog_service.domain.BookAlreadyExistsException;
import com.polarbookshop.catalog_service.domain.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Gestore globale delle eccezioni per il catalogo libri.
 * <p>Intercetta le eccezioni lanciate dal {@link BookService} o dai validatori
 * e le trasforma in risposte HTTP appropriate per il client REST.</p>
 */
@RestControllerAdvice
public class BookControllerAdvice {

    /**
     * Gestisce {@link BookNotFoundException} restituendo un HTTP 404 (Not Found).
     */
    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String bookNotFoundHandler(BookNotFoundException ex) {
        return ex.getMessage();
    }

    /**
     * Gestisce {@link BookAlreadyExistsException} restituendo un HTTP 422 (Unprocessable Entity).
     */
    @ExceptionHandler(BookAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String bookAlreadyExistsHandler(BookAlreadyExistsException ex) {
        return ex.getMessage();
    }

    /**
     * Gestisce gli errori di validazione del bean restituendo un HTTP 400 (Bad Request).
     * Mappa ogni campo non valido con il relativo messaggio di errore.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions (
            MethodArgumentNotValidException ex
    ) {
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}