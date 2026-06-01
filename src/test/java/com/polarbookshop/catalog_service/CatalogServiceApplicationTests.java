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

	@MockitoBean
	private BookService bookService;

	@BeforeEach
	void setUp() {

		this.webTestClient = WebTestClient.bindToServer()
				.baseUrl("http://localhost:" + port)
				.build();
	}
	@Test
	void whenPostRequestThenBookCreated() {
		var isbn = "1234567890123";
		var expectedBook = new Book(isbn, "Title", "Author", 9.90);

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

}