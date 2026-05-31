package com.polarbookshop.catalog_service;

import com.polarbookshop.catalog_service.domain.Book;
import com.polarbookshop.catalog_service.domain.BookRepository;
import com.polarbookshop.catalog_service.domain.BookService;
import com.polarbookshop.catalog_service.web.BookController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@WebMvcTest(BookController.class)
class CatalogServiceApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockitoBean
	private BookService bookService;

	@MockitoBean
	private BookRepository bookRepository;

	@Test
	void whenPostRequestThenBookCreated() {
		var expectedBook = new Book("12312322132", "Title", "Author", 9.90);

		webTestClient
				.post()
				.uri("/books")
				.bodyValue(expectedBook)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Book.class).value(actualBook -> {
					assertThat(actualBook).isNotNull();
					assertThat(actualBook.isbn())
							.isEqualTo(expectedBook.isbn());
				});
	}
}
