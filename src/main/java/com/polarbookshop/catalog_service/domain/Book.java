package com.polarbookshop.catalog_service.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

/**
 * Rappresenta un libro all'interno del catalogo.
 * Utilizziamo un 'record' Java perché è un oggetto immutabile che trasporta dati (Data Transfer Object).
 *
 * <p>Validazioni applicate:</p>
 * <ul>
 *   <li><b>ISBN:</b> Deve essere una stringa di 10 o 13 cifre.</li>
 *   <li><b>Titolo/Autore:</b> Non possono essere vuoti o contenere solo spazi.</li>
 *   <li><b>Prezzo:</b> Obbligatorio e deve essere un valore positivo.</li>
 * </ul>
 *
 * <p>Esempio d'uso:</p>
 * <pre>
 * Book book = new Book("1234567890", "Il Signore degli Anelli", "J.R.R. Tolkien", 19.99);
 * </pre>
 */
public record Book(

        @Id
        Long id,
        /**
         * Codice ISBN univoco del libro.
         * Deve rispettare il formato standard di 10 o 13 cifre.
         */
        @NotBlank(message = "The book ISBN must be defined.")
        @Pattern(
                regexp = "^([0-9]{10}|[0-9]{13})$",
                message = "The ISBN format must be valid."
        )
        String isbn,

        /**
         * Titolo del libro. Non può essere nullo o una stringa vuota.
         */
        @NotBlank(message = "The book title must be defined.")
        String title,

        /**
         * Autore del libro. Non può essere nullo o una stringa vuota.
         */
        @NotBlank(message = "The book author must be defined.")
        String author,

        /**
         * Prezzo del libro in valuta. Deve essere un numero positivo > 0.
         */
        @NotNull(message = "The book price must be defined.")
        @Positive(
                message = "The book price must be greater than zero."
        )
        Double price,

        @Version
        int version

) {
        public static Book of (
                String isbn, String title, String author, Double price
        ) {
                return new Book(
                        null, isbn, title, author, price, 0
                );
        }

}