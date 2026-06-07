package com.polarbookshop.catalog_service;

import com.polarbookshop.catalog_service.domain.Book;
import com.polarbookshop.catalog_service.domain.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CatalogServiceApplicationTests {

	private WebTestClient webTestClient;

	@LocalServerPort
	private int port;

	@MockitoBean // Sostituisce il vecchio @MockBean per Spring Boot 3.4+
	private BookService bookService;

	@BeforeEach
	void setUp() {
		this.webTestClient = WebTestClient.bindToServer()
				.baseUrl("http://localhost:" + port)
				.build();
	}

	@Test
	void whenPostRequestThenBookCreated() {
		var expectedBook = Book.of("1234567890123", "Title", "Author", 9.90);

		// Configuriamo il mock per restituire il libro quando chiamato
		given(bookService.addBookToCatalog(expectedBook)).willReturn(expectedBook);

		webTestClient
				.post()
				.uri("/books")
				.bodyValue(expectedBook)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Book.class).value(actualBook -> {
					assertThat(actualBook).isNotNull();
					assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
				});
	}

	// Applica lo stesso principio di 'given' anche agli altri metodi (Get, Put, Delete)
	// in modo che il mock sappia sempre cosa restituire.
}