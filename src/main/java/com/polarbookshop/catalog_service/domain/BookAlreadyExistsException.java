package com.polarbookshop.catalog_service.domain;

/**
 * Eccezione personalizzata lanciata quando si tenta di registrare un libro
 * con un ISBN già presente nel catalogo.
 * * <p>Questa classe estende {@link RuntimeException}, rendendola un'eccezione
 * non controllata (unchecked), ideale per le violazioni di business logic.</p>
 * * <p>Esempio di utilizzo:</p>
 * <pre>
 * if (repository.existsByIsbn(isbn)) {
 * throw new BookAlreadyExistsException(isbn);
 * }
 * </pre>
 */
public class BookAlreadyExistsException extends RuntimeException {

    /**
     * Costruisce una nuova eccezione con un messaggio descrittivo.
     *
     * @param isbn L'ISBN del libro che causa il conflitto.
     */
    public BookAlreadyExistsException(String isbn) {
        super("A book with ISBN " + isbn + " already exists.");
    }
}