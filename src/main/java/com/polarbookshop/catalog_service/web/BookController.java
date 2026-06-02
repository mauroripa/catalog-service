package com.polarbookshop.catalog_service.web;

import com.polarbookshop.catalog_service.domain.Book;
import com.polarbookshop.catalog_service.domain.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST per la gestione delle operazioni sul catalogo libri.
 * <p>Espone gli endpoint HTTP necessari per interagire con i dati dei libri
 * (lista, ricerca, creazione, eliminazione, aggiornamento).</p>
 */
@RestController
@RequestMapping("books")
public class BookController {

    private final BookService bookService;

    /**
     * Inietta il servizio di dominio per la gestione della logica applicativa.
     * @param bookService il servizio di business.
     */
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    /**
     * Recupera l'elenco di tutti i libri nel catalogo.
     * @return un'iterabile di oggetti {@link Book}.
     */
    @GetMapping
    public Iterable<Book> get() {
        return bookService.viewBookList();
    }

    /**
     * Recupera i dettagli di un libro tramite il suo ISBN.
     * @param isbn l'ISBN del libro.
     * @return il {@link Book} trovato.
     */
    @GetMapping("{isbn}")
    public Book getByIsbn(@PathVariable String isbn) {
        return bookService.viewBookDetails(isbn);
    }

    /**
     * Crea un nuovo libro nel catalogo.
     * @param book i dettagli del libro da creare, validati tramite {@link Valid}.
     * @return il libro creato.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book post(@Valid @RequestBody Book book) {
        return bookService.addBookToCatalog(book);
    }

    /**
     * Elimina un libro dal catalogo tramite ISBN.
     * @param isbn l'ISBN del libro da rimuovere.
     */
    @DeleteMapping("{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String isbn) {
        bookService.removeBookFromCatalog(isbn);
    }

    /**
     * Aggiorna i dettagli di un libro esistente o lo crea se non presente.
     * @param isbn l'ISBN del libro.
     * @param book i nuovi dettagli.
     * @return il libro aggiornato o creato.
     */
    @PutMapping("{isbn}")
    public Book put(@PathVariable String isbn, @Valid @RequestBody Book book) {
        return bookService.editBookDetails(isbn, book);
    }
}