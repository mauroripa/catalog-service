package com.polarbookshop.catalog_service.persistence;

import com.polarbookshop.catalog_service.domain.Book;
import com.polarbookshop.catalog_service.domain.BookRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementazione in memoria di {@link BookRepository}.
 * <p>Utilizza una {@link ConcurrentHashMap} per memorizzare i libri, garantendo
 * thread-safety durante le operazioni di lettura e scrittura simultanee.</p>
 * <p>Questa classe viene utilizzata principalmente per scopi di testing o
 * prototipazione rapida, non essendo persistente oltre il ciclo di vita
 * dell'applicazione.</p>
 */
@Repository
public class InMemoryBookRepository implements BookRepository {

    /**
     * Struttura dati per la memorizzazione dei libri.
     * La chiave è l'ISBN (String), il valore è l'entità {@link Book}.
     */
    private static final Map<String, Book> books =
            new ConcurrentHashMap<>();

    @Override
    public Iterable<Book> findAll() {
        return books.values();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return existsByIsbn(isbn) ? Optional.of(books.get(isbn)) :
                Optional.empty();
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return books.get(isbn) != null;
    }

    @Override
    public Book save(Book book) {
        books.put(book.isbn(), book);
        return book;
    }

    @Override
    public void deleteByIsbn(String isbn) {
        books.remove(isbn);
    }
}