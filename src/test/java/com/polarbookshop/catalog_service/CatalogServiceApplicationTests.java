package com.polarbookshop.catalog_service;

import com.polarbookshop.catalog_service.domain.Book;
import com.polarbookshop.catalog_service.domain.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = {
		org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class,
		org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class
}) // <--- Questa riga spegne il tentativo di connessione al database
class CatalogServiceApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockitoBean
	private BookService bookService;
	@Test
	void whenPostRequestThenBookCreated() {
		var expectedBook = Book.of(	 "1234567890123", "Title", "Author", 9.90);
		given(bookService.addBookToCatalog(expectedBook)).willReturn(expectedBook);
		webTestClient
				.post()
				.uri("/books")
				.bodyValue(expectedBook)
				.exchange()
				.expectStatus().isCreated() // Prova a leggerlo come stringa grezzo
				.expectBody(Book.class)
				.value(actualBook -> {
					assertThat(actualBook).isNotNull();
					assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
				});
	}

	// Applica lo stesso principio di 'given' anche agli altri metodi (Get, Put, Delete)
	// in modo che il mock sappia sempre cosa restituire.
}