package com.polarbookshop.catalog_service.domain;

import java.util.Optional;

/**
 * Definisce le operazioni di persistenza per l'entità {@link Book}.
 * * <p>Questa interfaccia funge da astrazione per il livello di accesso ai dati,
 * permettendo alla logica di business di interagire con il catalogo senza
 * dipendere da una specifica tecnologia di database.</p>
 */
public interface BookRepository {

    /**
     * Recupera tutti i libri presenti nel catalogo.
     * @return un insieme iterabile di {@link Book}.
     */
    Iterable<Book> findAll();

    /**
     * Cerca un libro tramite il suo ISBN.
     * @param isbn il codice ISBN del libro.
     * @return un {@link Optional} contenente il libro se trovato, altrimenti vuoto.
     */
    Optional<Book> findByIsbn(String isbn);

    /**
     * Verifica se esiste già un libro con il dato ISBN nel catalogo.
     * @param isbn il codice ISBN da verificare.
     * @return {@code true} se esiste, {@code false} altrimenti.
     */
    boolean existsByIsbn(String isbn);

    /**
     * Salva un nuovo libro o aggiorna un libro esistente nel catalogo.
     * @param book l'istanza del {@link Book} da salvare.
     * @return il libro salvato.
     */
    Book save(Book book);

    /**
     * Rimuove un libro dal catalogo tramite il suo ISBN.
     * @param isbn il codice ISBN del libro da eliminare.
     */
    void deleteByIsbn(String isbn);
}