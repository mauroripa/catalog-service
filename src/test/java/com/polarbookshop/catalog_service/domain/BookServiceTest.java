package com.polarbookshop.catalog_service.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

/**
 * Test unitari per {@link BookService}.
 * <p>Verifica la logica di business del servizio isolando le dipendenze
 * esterne (il repository) tramite l'utilizzo di Mockito.</p>
 */
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    /**
     * Verifica che venga lanciata un'eccezione {@link BookAlreadyExistsException}
     * quando si tenta di aggiungere un libro con un ISBN già esistente.
     */
    @Test
    void whenBookToCreateAlreadyExistsThenThrows() {
        var bookIsbn = "1234561232";
        var bookToCreate = Book.of(bookIsbn, "Title", "Author", 9.90);
        when(bookRepository.existsByIsbn(bookIsbn)).thenReturn(true);

        assertThatThrownBy(() -> bookService.addBookToCatalog(bookToCreate))
                .isInstanceOf(BookAlreadyExistsException.class)
                .hasMessage("A book with ISBN " + bookIsbn + " already exists.");
    }

    /**
     * Verifica che venga lanciata un'eccezione {@link BookNotFoundException}
     * quando si cerca un libro tramite ISBN che non esiste nel repository.
     */
    @Test
    void whenBookToReadDoesNotExistThenThrows() {
        var bookIsbn = "1234561232";
        when(bookRepository.findByIsbn(bookIsbn)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.viewBookDetails(bookIsbn))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("The book with ISBN " + bookIsbn + " was not found.");
    }
}