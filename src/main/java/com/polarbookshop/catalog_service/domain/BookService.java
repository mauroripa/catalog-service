package com.polarbookshop.catalog_service.domain;

import org.springframework.stereotype.Service;

/**
 * Servizio di dominio che coordina la logica di business per la gestione dei libri.
 * <p>Questa classe agisce come intermediario tra il controller (che riceve le richieste)
 * e il repository (che gestisce i dati).</p>
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    /**
     * Costruttore per l'iniezione delle dipendenze del repository.
     * @param bookRepository l'implementazione del repository da utilizzare.
     */
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    /**
     * Recupera l'elenco completo dei libri nel catalogo.
     * @return un insieme iterabile di {@link Book}.
     */
    public Iterable<Book> viewBookList() {
        return bookRepository.findAll();
    }

    /**
     * Recupera i dettagli di un libro specifico.
     * @param isbn l'ISBN del libro da cercare.
     * @return il {@link Book} corrispondente.
     * @throws BookNotFoundException se il libro con l'ISBN specificato non esiste.
     */
    public Book viewBookDetails(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    /**
     * Aggiunge un nuovo libro al catalogo.
     * @param book il {@link Book} da aggiungere.
     * @return il libro salvato.
     * @throws BookAlreadyExistsException se un libro con lo stesso ISBN esiste già.
     */
    public Book addBookToCatalog(Book book) {
        if (bookRepository.existsByIsbn(book.isbn())) {
            throw new BookAlreadyExistsException(book.isbn());
        }
        return bookRepository.save(book);
    }

    /**
     * Rimuove un libro dal catalogo.
     * @param isbn l'ISBN del libro da eliminare.
     */
    public void removeBookFromCatalog(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

    /**
     * Aggiorna i dettagli di un libro esistente o lo aggiunge se non trovato.
     * @param isbn l'ISBN del libro da aggiornare.
     * @param book il {@link Book} con i nuovi dettagli.
     * @return il libro aggiornato o creato.
     */
    public Book editBookDetails(String isbn, Book book) {
        return bookRepository.findByIsbn(isbn)
                .map(existingBook -> {
                    var bookToUpdate = new Book(
                            existingBook.isbn(),
                            book.title(),
                            book.author(),
                            book.price());
                    return bookRepository.save(bookToUpdate);
                })
                .orElseGet(() -> addBookToCatalog(book));
    }
}