package com.polarbookshop.catalog_service.domain;

import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test unitari per la validazione dell'entità {@link Book}.
 * <p>Verifica che le regole di validazione (Bean Validation) siano applicate
 * correttamente ai campi del record {@link Book}.</p>
 */
public class BookValidationTests {

    private static Validator validator;

    /**
     * Inizializza il validator prima di eseguire i test.
     */
    @BeforeAll
    static void setup(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Verifica che la validazione abbia successo quando tutti i campi
     * rispettano i vincoli definiti.
     */
    @Test
    void whenAllFieldsCorrectThenValidationSucceeds() {
        var book =
                Book.of("1234567890","Title", "Author", 9.90);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isEmpty();
    }

    /**
     * Verifica che la validazione fallisca quando l'ISBN non rispetta
     * il formato numerico previsto.
     */
    @Test
    void whenIsbnDefinedButIncorrectThenValidationFails() {
        var book =
                Book.of("a234567890", "Title", "Author", 9.90);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The ISBN format must be valid.");
    }
}