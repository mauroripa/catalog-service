package com.polarbookshop.catalog_service.domain;

/**
 * Eccezione lanciata quando un libro richiesto non viene trovato nel catalogo.
 * * <p>Viene utilizzata tipicamente nelle operazioni di lettura, aggiornamento o cancellazione
 * quando l'ISBN fornito non corrisponde ad alcun record esistente.</p>
 * * <p>Esempio di utilizzo:</p>
 * <pre>
 * repository.findByIsbn(isbn)
 * .orElseThrow(() -> new BookNotFoundException(isbn));
 * </pre>
 */
public class BookNotFoundException extends RuntimeException {

    /**
     * Costruisce una nuova eccezione con un messaggio descrittivo.
     *
     * @param isbn L'ISBN del libro che non è stato possibile trovare.
     */
    public BookNotFoundException(String isbn) {
        super("The book with ISBN " + isbn + " was not found.");
    }
}